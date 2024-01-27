package reflection.section_7.repeatable_annotations.annotations;

import java.lang.annotation.*;

public class Annotations {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ScanPackages {
        String[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ScheduleExecutorClass{
    }


    @Repeatable(ExecutionSchedules.class)
    @Target(ElementType.METHOD)
    public @interface ExecuteOnSchedule{
        int delaySeconds() default 0;
        int periodSeconds();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExecutionSchedules{
        ExecuteOnSchedule[] value();
    }
}
