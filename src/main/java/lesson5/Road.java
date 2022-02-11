package lesson5;

public class Road extends Stage {
    private int roadCount;

    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        super.countStage++;
        roadCount = countStage;
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
