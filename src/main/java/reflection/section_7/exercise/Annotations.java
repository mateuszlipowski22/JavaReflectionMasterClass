package reflection.section_7.exercise;

import java.lang.annotation.*;

public class Annotations {
    /**
     * COMPLETE YOUR CODE HERE 
     */
    @Repeatable(PermissionsContainer.class)
    @Target(ElementType.TYPE)
    public @interface Permissions {
//        Role role();
//        OperationType[] allowed();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface PermissionsContainer {
        Permissions[] value();
    }

}