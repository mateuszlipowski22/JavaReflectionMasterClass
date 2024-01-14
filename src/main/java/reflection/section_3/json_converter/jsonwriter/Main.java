package reflection.section_3.json_converter.jsonwriter;

import reflection.section_3.json_converter.data.Address;
import reflection.section_3.json_converter.data.Company;
import reflection.section_3.json_converter.data.Person;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Address address = new Address("Main street",(short) 1);
        Person person = new Person("John", true,29, 100.555f, address,
                new Company("Udemy", "Las Vegas", new Address("Harrison street", (short) 8)));

        String json = objectToJson(person,0);

        System.out.println(json);
    }

    public static String objectToJson(Object instance, int indentSize) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("{");
        stringBuilder.append("\n");

        for (int i=0 ; i<fields.length;i++) {
            Field field = fields[i];
            field.setAccessible(true);

            if(field.isSynthetic()){
                continue;
            }
            stringBuilder.append(indent(indentSize+1));
            stringBuilder.append(formatStringValue(field.getName()));
            stringBuilder.append(":");
            if (field.getType().isPrimitive()){
                stringBuilder.append(formatPrimitiveValue(field,instance));
            }else if(field.getType().equals(String.class)){
                stringBuilder.append(formatStringValue(field.get(instance).toString()));
            }else {
                stringBuilder.append(objectToJson(field.get(instance),indentSize+1));
            }

            if( i !=fields.length-1){
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(indent(indentSize));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String indent(int indentSize){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i < indentSize;i++){
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }

    private static String formatPrimitiveValue(Field field, Object parentInstance) throws IllegalAccessException {
        if (field.getType().equals(boolean.class) ||
                field.getType().equals(int.class) ||
                field.getType().equals(long.class) ||
                field.getType().equals(short.class)) {
            return field.get(parentInstance).toString();
        } else if (field.getType().equals(double.class) ||
                field.getType().equals(float.class)) {
            return String.format("%.02f", field.get(parentInstance));
        }
        throw new RuntimeException(String.format("Type : %s is unsupported", field.getType().getName()));
    }

    private static String formatStringValue(String value) {
        return String.format("\"%s\"", value);
    }
}
