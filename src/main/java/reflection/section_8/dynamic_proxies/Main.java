package reflection.section_8.dynamic_proxies;

import reflection.section_8.dynamic_proxies.external.DatabaseReader;
import reflection.section_8.dynamic_proxies.external.HttpClient;
import reflection.section_8.dynamic_proxies.external.impl.DatabaseReaderImpl;
import reflection.section_8.dynamic_proxies.external.impl.HttpClientImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
    HttpClient httpClient = createProxy(new HttpClientImpl());
    DatabaseReader databaseReader = createProxy(new DatabaseReaderImpl());

    useHttpClient(httpClient);
    useDatabaseReader(databaseReader);

    List<String> listOfGreetings = createProxy(new ArrayList<String>());

    listOfGreetings.add("Hello");
    listOfGreetings.add("good morning");
    listOfGreetings.remove("hello");
    }

    public static void useHttpClient(HttpClient httpClient){
        httpClient.initialize();
        String response = httpClient.sendRequest("some request");

        System.out.println(String.format("Http response is : %s", response));
    }

    public static void useDatabaseReader(DatabaseReader databaseReader) throws InterruptedException {
        int rowsInGameTable = databaseReader.countRowsInTable("GamesTable");
        System.out.println(String.format("There are %s rows in GamesTable", rowsInGameTable));

        String[] data = databaseReader.readRow("SELECT * FROM GamesTable");

        System.out.println(String.format("Received %s", String.join(" , ", data)));
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Object originalObject){
        Class<?>[] interfaces = originalObject.getClass().getInterfaces();

        TimeMeasuringProxyHandler timeMeasuringProxyHandler = new TimeMeasuringProxyHandler(originalObject);

        return (T) Proxy.newProxyInstance(originalObject.getClass().getClassLoader(),interfaces,timeMeasuringProxyHandler);
    }

    public static class TimeMeasuringProxyHandler implements InvocationHandler{

        private final Object originalObject;

        public TimeMeasuringProxyHandler(Object originalObject) {
            this.originalObject = originalObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result;

            System.out.println(String.format("Measuring proxy - before executing method : %s()", method.getName()));

            long startTime = System.currentTimeMillis();

            try {
                result = method.invoke(originalObject, args);
            }catch (InvocationTargetException e){
                throw e.getTargetException();
            }
            long endTime = System.currentTimeMillis();

            System.out.println(String.format("Measuring proxy - Execution of %s() took %dms", method.getName(), endTime-startTime));

            return result;
        }
    }
}
