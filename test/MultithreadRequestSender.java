package test;

public class MultithreadRequestSender {
    public static void main(String[] args) {
        int numberOfThreads = 10; // Number of threads to create
        System.out.println("Number of thread is " + numberOfThreads);

        // Create and start multiple threads
        for (int i = 1; i <= numberOfThreads; i++) {
            RequestSender requestSender = new RequestSender("Thread " + i);
            requestSender.start();
        }
    }
}
