package Pizza;

class Street{
    private String name;
    private int x;
    private int y;
    private double distanceToPizza;

    Street(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
        this.distanceToPizza = Double.POSITIVE_INFINITY;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getName(){
        return name;
    }

    public void setDistanceToPizza(double distanceToPizza) {
        this.distanceToPizza = distanceToPizza;
    }

    public double getDistanceToPizza() {
        return  distanceToPizza;
    }
}
