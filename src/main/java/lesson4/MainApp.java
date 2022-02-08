package lesson4;

public class MainApp {
    public static void main(String[] args) {
        Print printABC= new Print();

        Thread threadA = new Thread(printABC::printA);
        Thread threadB = new Thread(printABC::printB);
        Thread threadC = new Thread(printABC::printC);

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
