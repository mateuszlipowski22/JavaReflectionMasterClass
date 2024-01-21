package reflection.section_5.methods.invokingMethods.logger;

import java.io.IOException;

public class FileLogger {
    public void sendRequest(String data) throws IOException {
        throw new IOException();
//        System.out.println(String.format("Data : %s was logged to the file system", data));
    }
}
