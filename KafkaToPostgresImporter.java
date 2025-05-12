import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.dataformat.avro.AvroDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.AvroDataFormatFactory;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.annotations.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

public class KafkaToPostgresImporter {
    public static void main(String[] args) throws Exception {
        // Create a Camel context
        CamelContext context = new DefaultCamelContext();

        // Create a Kafka component
        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers("localhost:9092");
        context.addComponent("kafka", kafka);

        // Create an Avro data format
        AvroDataFormat avro = new AvroDataFormat();
        avro.setSchemaFromClasspath("schema.avsc");

        // Create a PostgreSQL data source
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/mydatabase")
                .username("myuser")
                .password("mypassword")
                .build();

        // Create a Camel route
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("kafka:mytopic?brokers=localhost:9092&groupId=mygroup")
                        .unmarshal(avro)
                        .bean(MyRecordProcessor.class)
                        .filter().method(MyRecordFilter.class, "filter")
                        .to("jdbc:postgresql://localhost:5432/mydatabase?dataSource=#dataSource&sql=INSERT INTO mytable (id, name, value) VALUES (#id, #name, #value)");
            }
        });

        // Start the Camel context
        context.start();
    }
}
