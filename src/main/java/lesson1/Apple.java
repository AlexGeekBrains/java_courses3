package lesson1;

public class Apple extends Fruit {
    private float weight;

    public Apple() {
        this.weight = 1.0f;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                '}';
    }
}
