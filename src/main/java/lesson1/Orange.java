package lesson1;

public class Orange extends Fruit {


    public Orange() {
        super(1.0f);
    }



    @Override
    public String toString() {
        return "Orange{" +
                "weight=" + getWeight() +
                '}';
    }
}
