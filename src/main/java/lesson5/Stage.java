package lesson5;

public abstract class Stage {
    protected static int countStage;
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);

    public int getCountStage() {
        return countStage;
    }
}
