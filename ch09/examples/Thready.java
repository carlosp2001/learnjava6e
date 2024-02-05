package ch09.examples;

public class Thready {
    public static void main(String[] args) {
        // Round-robin between two threads
        new Thread(new ShowThread("Foo")).start();
        new Thread(new ShowThread("Bar")).start();
    }

    static class ShowThread implements Runnable {
        String message;

        ShowThread(String message) {
            this.message = message;
        }

        public void run() {
            while (true)
                System.out.println(message);
        }
    }
}
