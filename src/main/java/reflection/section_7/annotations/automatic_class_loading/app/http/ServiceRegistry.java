package reflection.section_7.annotations.automatic_class_loading.app.http;

import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerClass;
import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerMethod;

@InitializerClass
public class ServiceRegistry {
    @InitializerMethod
    public void registerService(){
        System.out.println("Service successfully registered");
    }
}
