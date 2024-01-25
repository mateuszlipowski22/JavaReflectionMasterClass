package reflection.section_7.annotations.automatic_class_loading.app;

import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerClass;
import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerMethod;

@InitializerClass
public class AutoSaver {
    @InitializerMethod
    public void startAutoSavingThreads(){
        System.out.println("Start automatic data saving to disk");
    }
}
