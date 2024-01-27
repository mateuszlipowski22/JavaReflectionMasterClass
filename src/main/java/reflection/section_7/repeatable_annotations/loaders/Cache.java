package reflection.section_7.repeatable_annotations.loaders;

import reflection.section_7.repeatable_annotations.annotations.Annotations.*;

@ScheduleExecutorClass
public class Cache {

    @ExecuteOnSchedule(periodSeconds = 5)
    @ExecuteOnSchedule(periodSeconds = 1, delaySeconds = 10)
    public static void reloadCache(){
        System.out.println("Reloading cache");
    }
}
