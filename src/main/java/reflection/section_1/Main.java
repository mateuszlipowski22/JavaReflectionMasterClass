package reflection.section_1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        Class<String> stringClass = String.class;

        Map<String, Integer> hashMap = new HashMap<String,Integer>();
        Class<?> hashMapClass = hashMap.getClass();

        Class<?> squareClass = Class.forName("reflection.section_1.Main$Square");

        var circleObject = new Drawable() {
            @Override
            public int getNumberOfCorners() {
                return 0;
            }
        };

        printClassInfo(stringClass, hashMapClass, squareClass);
        printClassInfo(Collection.class, boolean.class, int[][].class, Color.class, circleObject.getClass());
    }

    private static void printClassInfo(Class<?> ... clases){
        for(Class<?> clazz : clases){
            System.out.println(String.format("class name: %s, class package : %s",
                    clazz.getSimpleName(),clazz.getPackageName()));

            Class<?>[] implementedInterfaces = clazz.getInterfaces();

            for(Class<?> implementedInterface : implementedInterfaces){
                System.out.println(String.format("class %s implements : %s"
                        ,clazz.getSimpleName(),implementedInterface.getSimpleName()));
            }

            System.out.println("Is array : "+clazz.isArray());
            System.out.println("Is primitive : "+clazz.isPrimitive());
            System.out.println("Is enum : "+clazz.isEnum());
            System.out.println("Is interface : "+clazz.isInterface());
            System.out.println("Is anonymous : "+clazz.isAnonymousClass());

            System.out.println();
            System.out.println();
        }
    }

    private static class Square implements Drawable{

        @Override
        public int getNumberOfCorners() {
            return 4;
        }
    }

    private static interface Drawable{
        int getNumberOfCorners();
    }

    private enum Color{
        RED,
        BLUE,
        GREEN
    }
}
