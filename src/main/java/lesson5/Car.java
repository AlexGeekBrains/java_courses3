package lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {

    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private CountDownLatch start;
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch win;
    private static boolean isWin;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch start, CyclicBarrier cyclicBarrier, CountDownLatch win) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.start = start;
        this.cyclicBarrier = cyclicBarrier;
        this.win = win;
    }

    @Override
    public void run() {
        try {
            isWin = false;
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            start.countDown();
            cyclicBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        checkWin(this);

        win.countDown();

        /*  if (win.getCount() == MainClass.CARS_COUNT - 1) {// сначала метод проверки на победителя был такой
       System.out.println(this.name + " WIN");
       }*/
    }

    private static synchronized void checkWin(Car car) {
        if (!isWin) {
            System.out.println(car.getName() + " WIN");
            isWin = true;
        }
    }
}