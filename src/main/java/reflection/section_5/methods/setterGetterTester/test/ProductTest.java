package reflection.section_5.methods.setterGetterTester.test;

import reflection.section_5.methods.setterGetterTester.api.ClothingProduct;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ProductTest {
    public static void main(String[] args) {
        testGetters(ClothingProduct.class);
        testSetters(ClothingProduct.class);
    }

    public static void testSetters(Class<?> clazz) {
        List<Field> fields = getAllFields(clazz);

        for(Field field : fields){
            String setterName = "set" + capitalizeFirstLetter(field.getName());

            Method setterMethod = null;
            try {
                setterMethod = clazz.getMethod(setterName, field.getType());
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(String.format("Field : %s not found", setterName));
            }

            if (!setterMethod.getReturnType().equals(void.class)) {
                throw new IllegalStateException(String.format("Setter method : %s() has to return void",
                       setterName));
            }
        }
    }

    public static void testGetters(Class<?> clazz) {
        List<Field> fields = getAllFields(clazz);
        Map<String, Method> methodsMap = mapMethodNameToMethod(clazz);

        for (Field field : fields) {
            String getterName = "get" + capitalizeFirstLetter(field.getName());

            if (!methodsMap.containsKey((getterName))) {
                throw new IllegalStateException(String.format("Field : %s doesn't have a getter method", field.getName()));
            }
            Method getter = methodsMap.get(getterName);

            if (!getter.getReturnType().equals(field.getType())) {
                throw new IllegalStateException(String.format("Getter method : %s() has return type %s but expected %s",
                        getter.getName(),
                        getter.getReturnType(),
                        field.getType().getTypeName()));
            }

            if (getter.getParameterCount() > 0) {
                throw new IllegalStateException(String.format("Getter : %s has %d arguments",
                        getter.getName(), getter.getParameterCount()));
            }
        }
    }

    private static String capitalizeFirstLetter(String name) {
        return name.substring(0, 1).toUpperCase().concat(name.substring(1));
    }

    private static Map<String, Method> mapMethodNameToMethod(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        Map<String, Method> methodsMap = new HashMap<>();
        for (Method method : methods) {
            methodsMap.put(method.getName(), method);
        }
        return methodsMap;
    }

    private static List<Field> getAllFields(Class<?> clazz){
        if(clazz==null || clazz.equals(Object.class)){
            return Collections.emptyList();
        }

        Field[] currentClassFields = clazz.getDeclaredFields();

        List<Field> inheritedFields = getAllFields(clazz.getSuperclass());

        List<Field> allFields = new ArrayList<>();

        allFields.addAll(Arrays.asList(currentClassFields));
        allFields.addAll(inheritedFields);

        return allFields;
    }
}
