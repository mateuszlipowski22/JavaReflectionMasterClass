package reflection.section_5.methods.test;

import reflection.section_5.methods.api.Product;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProductTest {
    public static void main(String[] args) {
        testGetters(Product.class);
    }

    public static void testGetters(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Method> methodsMap = mapMethodNameToMethod(clazz);

        for(Field field : fields){
            String getterName = "get" + capitalizeFirstLetter(field.getName());

            if(!methodsMap.containsKey((getterName))){
                throw  new IllegalStateException(String.format("Field : %s doesn't have a getter method",field.getName()));
            }
            Method getter = methodsMap.get(getterName);

            if(!getter.getReturnType().equals(field.getType())){
                throw  new IllegalStateException(String.format("Getter method : %s() has return type %s but expected %s",
                        getter.getName(),
                        getter.getReturnType(),
                        field.getType().getTypeName()));
            }

            if(getter.getParameterCount()>0){
                throw  new IllegalStateException(String.format("Getter : %s has %d arguments",
                        getter.getName(), getter.getParameterCount()));
            }
        }
    }

    private static String capitalizeFirstLetter(String name) {
        return name.substring(0,1).toUpperCase().concat(name.substring(1));
    }

    private static Map<String, Method> mapMethodNameToMethod(Class<?> clazz){
        Method[] methods = clazz.getMethods();
        Map<String, Method> methodsMap = new HashMap<>();
        for(Method method : methods){
            methodsMap.put(method.getName(),method);
        }
        return methodsMap;
    }
}
