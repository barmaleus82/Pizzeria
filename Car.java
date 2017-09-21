package Pizza;

class Car extends Thread implements Runnable {
    private String name;
    private Street street;      //address
    private String menu;        //menu
    private double distanceCur; //current position of car
    private double distanceFull;//distance from pizzeria to address and back

    Car(String name) {
        //System.out.println("car");
        this.name = name;
        new Thread(this).start();
    }

    public String toString(){
        if (street == null){
            return name+" свободна";
        } else {
            return name+". Адрес: " + street.getName() + "; расстояние = "+String.format("%.2f", street.getDistanceToPizza())+
                    " вернеться через: " + String.format("%.2f", (distanceFull-distanceCur) * 5) +"с";
        }
    }

    public void setStreetMenu(Street street, String menu){
        if(street.getDistanceToPizza()==0){ // if distance == 0 you are in pizzeria
            System.out.println("Вы и так в пицеррии");
        } else {
            this.street = street;
            this.menu = menu;
            this.distanceFull = street.getDistanceToPizza() * 2;
            this.distanceCur = 0;
        }
    }

    public Street getStreet(){
        return street;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(distanceFull>0){ //if we get order
                distanceCur++;  //one step in time
                if(distanceFull<=distanceCur){
                    street=null;
                    menu=null;
                    distanceCur=0;
                    distanceFull=0;
                }
            }
        }
    }
}