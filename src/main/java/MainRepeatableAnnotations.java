import reflection.section_7.repeatable_annotations.annotations.Annotations.ExecuteOnSchedule;
import reflection.section_7.repeatable_annotations.annotations.Annotations.ScanPackages;
import reflection.section_7.repeatable_annotations.annotations.Annotations.ScheduleExecutorClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ScanPackages({
        "reflection.section_7.repeatable_annotations.loaders"
})
public class MainRepeatableAnnotations {
    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
        schedule();
    }

    public static void schedule() throws URISyntaxException, IOException, ClassNotFoundException {
        ScanPackages scanPackages = MainRepeatableAnnotations.class.getAnnotation(ScanPackages.class);
        if(scanPackages==null || scanPackages.value().length==0){
            return;
        }

        List<Class<?>> allClasses = getAllClasses(scanPackages.value());
        List<Method> scheduledExecutorMethods = getScheduledExecutorMethods(allClasses);

        for(Method method : scheduledExecutorMethods){
            scheduledExecutorMethods(method);
        }
    }

    private static void scheduledExecutorMethods(Method method) {
        ExecuteOnSchedule[] schedules = method.getAnnotationsByType(ExecuteOnSchedule.class);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        for(ExecuteOnSchedule schedule : schedules){
            scheduledExecutorService.scheduleAtFixedRate(
                    ()->runWhenSchedule(method),
                    schedule.delaySeconds(),
                    schedule.periodSeconds(),
                    TimeUnit.SECONDS
            );
        }
    }

    private static void runWhenSchedule(Method method) {
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println(String.format("Execution at %s", simpleDateFormat.format(currentDate)));
        try{
            method.invoke(null);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static List<Method> getScheduledExecutorMethods(List<Class<?>> classes){
        List<Method> scheduleMethods = new ArrayList<>();
        for(Class<?> clazz : classes){
            if(!clazz.isAnnotationPresent(ScheduleExecutorClass.class)){
                continue;
            }else {
                for(Method method : clazz.getDeclaredMethods()){
                    if(method.getAnnotationsByType(ExecuteOnSchedule.class).length!=0){
                        scheduleMethods.add(method);
                    }
                }
            }
        }
        return scheduleMethods;
    }

    private static List<Class<?>> getAllClasses(String ... packageNames) throws URISyntaxException, IOException, ClassNotFoundException {
        List<Class<?>> allClasses = new ArrayList<>();

        for(String packageName : packageNames){
            String packageRelativePath = packageName.replace('.','/');
            URI packageUri = Main.class.getResource(packageRelativePath).toURI();
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
