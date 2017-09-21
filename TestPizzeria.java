package Pizza;

public class TestPizzeria {
    public static void main(String[] args) {
        String fileName = "d://java/Projects/Lection6/src/Pizza/PizzaData.txt";
        try {
            Pizzeria pizza = new Pizzeria(fileName);
            Thread myThready = new Thread(pizza);
            myThready.start();

        }catch (RuntimeException ex){
            System.out.println("Error reading/closing "+ fileName);
        }
    }
}
