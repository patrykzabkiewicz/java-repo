public class MyRecordFilter {
    public boolean filter(MyRecord record) {
        return record.getValue() >= 0;
    }
}
