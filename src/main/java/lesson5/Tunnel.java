package lesson5;

import java.util.concurrent.Semaphore;


public class Tunnel extends Stage {
    private int tunnelCount;
    private Semaphore semaphoreTunnel; // семафор для туннеля;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        super.countStage++;
        tunnelCount = countStage;
        this.semaphoreTunnel = new Semaphore(MainClass.CARS_COUNT / 2);
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                semaphoreTunnel.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                semaphoreTunnel.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
