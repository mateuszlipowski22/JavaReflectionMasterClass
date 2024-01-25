package reflection.section_7.annotations.automatic_class_loading.app.database;

import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerClass;
import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerMethod;

@InitializerClass
public class DatabaseConnection {
    @InitializerMethod
    public void connectToDatabase1(){
        System.out.println("Connecting to database 1");
    }

    @InitializerMethod
    public void connectToDatabase2(){
        System.out.println("Connecting to database 2");
    }
}
