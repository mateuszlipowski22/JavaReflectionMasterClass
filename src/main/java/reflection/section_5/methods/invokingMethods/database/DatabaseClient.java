package reflection.section_5.methods.invokingMethods.database;

public class DatabaseClient {
    public boolean storeData(String data){
        System.out.println(String.format("Data : %s was successfully stored in the database", data));
        return true;
    }
}
