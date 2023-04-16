package test;

public class RequestSender  extends Thread {
    public RequestSender(String name) {
        super(name);
    }

    @Override
    public void run() {
        // Code to send the request
        System.out.println("Sending request from thread: " + getName());
        WsJaasTestClientMT wjtmt = new WsJaasTestClientMT(); 
        try {
            wjtmt.sendRequest(); 
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Request sent from thread:" + getName());
    }
  
}
