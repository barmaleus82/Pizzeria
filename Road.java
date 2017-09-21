package Pizza;

import java.util.ArrayList;

class Road{
    private Street point1;
    private Street point2;
    private double distance;

    Road(ArrayList<Street> arr, int pos1, int pos2){
        this.point1 = arr.get(pos1-1);
        this.point2 = arr.get(pos2-1);
        this.distance = Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2));
    }

    public double getDistance(){
        return distance;
    }

    public Street getPoint1() {
        return point1;
    }

    public Street getPoint2() {
        return point2;
    }
}
