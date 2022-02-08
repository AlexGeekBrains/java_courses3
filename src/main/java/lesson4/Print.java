package lesson4;


public class Print {
    private final Object mon = new Object();
    private volatile char print = 'A';

    public void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (print != 'A') {
                        mon.wait();
                    }
                    System.out.print("A");
                    print = 'B';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (print != 'B') {
                        mon.wait();
                    }
                    System.out.print("B");
                    print = 'C';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (print != 'C') {
                        mon.wait();
                    }
                    System.out.print("C");
                    print = 'A';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}