package reflection.section_7.annotations.automatic_class_loading;

import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerClass;
import reflection.section_7.annotations.automatic_class_loading.annotations.InitializerMethod;
import reflection.section_7.annotations.automatic_class_loading.annotations.ScanPackages;
import reflection.section_7.annotations.automatic_class_loading.app.AutoSaver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        initialize("reflection.section_7.annotations.automatic_class_loading.app");
    }

    private static List<Method> getAllInitializationMethods(Class<?> clazz){
        List<Method> initializingMethods = new ArrayList<>();
        for(Method method : clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(InitializerMethod.class)){
                initializingMethods.add(method);
            }
        }
        return initializingMethods;
    }

    public static void initialize(String ... packageName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, URISyntaxException, IOException, ClassNotFoundException {
        List<Class<?>> classes = getAllClasses(packageName);
        for(Class<?> clazz : classes){
            if(!clazz.isAnnotationPresent(InitializerClass.class)){
                continue;
            }

            List<Method> methods = getAllInitializationMethods(clazz);

            Object instance = clazz.getDeclaredConstructor().newInstance();

            for(Method method : methods){
                method.invoke(instance);
            }
        }
    }

    private static List<Class<?>> getAllClasses(String ... packageNames) throws URISyntaxException, IOException, ClassNotFoundException {
        List<Class<?>> allClasses = new ArrayList<>();

        for(String packageName : packageNames){
            String packageRelativePath = packageName.replace('.','/');
            System.out.println(packageRelativePath);
            URI packageUri = Main.class.getResource(packageRelativePath).toURI();
            System.out.println(packageUri);
            if(packageUri.getScheme().equals("file")){
                Path packageFullPath = Paths.get(packageUri);
                allClasses.addAll(getPackageClasses(packageFullPath,packageName));
            }else if (packageUri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(packageUri,Collections.emptyMap());
                Path packageFullPathJar = fileSystem.getPath(packageRelativePath);
                allClasses.addAll(getPackageClasses(packageFullPathJar,packageName));
                fileSystem.close();
            }
        }
        return allClasses;
    }

    private static List<Class<?>> getPackageClasses(Path packagePath, String packageName) throws IOException, ClassNotFoundException {

        if(!Files.exists(packagePath)){
            return Collections.emptyList();
        }

        List<Path> files = Files.list(packagePath)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        List<Class<?>> classes = new ArrayList<>();

        for(Path filePath : files){
            String fileName = filePath.getFileName().toString();
            System.out.println(fileName +" "+packageName);
            if(fileName.endsWith(".class")){
                String classFullName = packageName.isBlank() ?
                        fileName.replaceFirst("\\.class$", "")
                        : packageName + "." + fileName.replaceFirst("\\.class$", "");
                Class<?> clazz = Class.forName(classFullName);
                classes.add(clazz);
            }
        }
        return classes;
    }
}
