package reflection.section_5.methods.invokingMethods.udp;

public class UdpClient {
    public void sendAndForget(String requestPayload){
        System.out.println(String.format("Request : %s was send throuth UDP", requestPayload));
    }
}
