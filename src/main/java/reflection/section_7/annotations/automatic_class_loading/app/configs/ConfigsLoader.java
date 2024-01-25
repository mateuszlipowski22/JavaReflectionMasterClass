package reflection.section_7.annotations.automatic_class_loading.app.configs;

import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerClass;
import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerMethod;

@InitializerClass
public class ConfigsLoader {
    @InitializerMethod
    public void loadAllConfigs(){
        System.out.println("Loading all configuration files");
    }
}
