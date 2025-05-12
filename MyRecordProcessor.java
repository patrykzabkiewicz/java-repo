import org.apache.commons.beanutils.BeanUtils;

public class MyRecordProcessor {
    public MyRecord process(MyRecord record) {
        if (record.getValue() < 0) {
            // Filter out messages with negative values
            return null;
        } else {
            BeanUtils.copyProperties(record, record);
            for (String propertyName : BeanUtils.getPropertyNames(record)) {
                Object propertyValue = BeanUtils.getProperty(record, propertyName);
                if (propertyValue instanceof String) {
                    BeanUtils.setProperty(record, propertyName, ((String) propertyValue).toUpperCase());
                }
            }
            return record;
        }
    }
}
