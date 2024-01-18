package reflection.section_4.setting_field_value.configloader;

import reflection.section_4.setting_field_value.data.GameConfig;
import reflection.section_4.setting_field_value.data.UserInterface;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    private static final Path GAME_CONFIG_PATH = Path.of("src/main/resources/game-properties.cfg");
    private static final Path UI_CONFIG_PATH = Path.of("src/main/resources/user-interface.cfg");

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        GameConfig gameConfig = createConfigObject(GameConfig.class, GAME_CONFIG_PATH);
        System.out.println(gameConfig);
        UserInterface userConfig = createConfigObject(UserInterface.class, UI_CONFIG_PATH);
        System.out.println(userConfig);
    }

    public static <T> T createConfigObject(Class<T> clazz, Path theFile) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Scanner scanner = new Scanner(theFile);
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        T configInstance = (T) constructor.newInstance();

        while (scanner.hasNextLine()) {
            String configLine = scanner.nextLine();

            String[] nameValuePair = configLine.split("=");
            String propertyName = nameValuePair[0];
            String propertyValue = nameValuePair[1];

            Field field;
            try {
                field = clazz.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
                System.out.println(String.format("Property name : %s is unsupported", propertyName));
                continue;
            }
            field.setAccessible(true);
            Object parseValue;

            if (field.getType().isArray()) {
                parseValue = parseArray(field.getType().getComponentType(), propertyValue);
            }else {
                parseValue = parseValue(field.getType(), propertyValue);
            }
            field.set(configInstance, parseValue);
        }

        return configInstance;
    }

    private static Object parseArray(Class<?> arrayElementType, String value) {
        String[] elementValues = value.split(",");

        Object arrayObject = Array.newInstance(arrayElementType, elementValues.length);

        for (int i = 0; i < elementValues.length; i++) {
            Array.set(arrayObject, i, parseValue(arrayElementType, elementValues[i]));
        }
        return arrayObject;
    }

    private static Object parseValue(Class<?> type, String value) {
        if (type.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(short.class)) {
            return Short.parseShort(value);
        } else if (type.equals(long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(String.class)) {
            return value;
        }
        throw new RuntimeException(String.format("Type : %s unsupported", type.getTypeName()));
    }
}
