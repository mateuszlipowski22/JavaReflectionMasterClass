package reflection.section_7.annotations.automatic_class_loading.app.database;

import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerClass;
import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerMethod;

@InitializerClass
public class CacheLoader {
    @InitializerMethod
    public void loadCache(){
        System.out.println("Loading data from cache");
    }

    public void reloadCache(){
        System.out.println("Reload cache");
    }
}
