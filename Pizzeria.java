package Pizza;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Pizzeria extends Thread implements Runnable {

    private ArrayList <Street> arrStreet = new ArrayList<Street>();
    private ArrayList <Road> arrRoad = new ArrayList<Road>();;
    private ArrayList <String> arrMenu = new ArrayList<String>();
    private ArrayList <Car> carArr = new ArrayList<Car>();
    private Street base;    //adress pizerria

    Pizzeria(String fileName)  throws RuntimeException {
        try {
            //reading and parsing file
            int dataBlock=1;
            FileReader myFile = new FileReader(fileName);
            BufferedReader buff = new BufferedReader(myFile);

            String line = buff.readLine();
            while (line!=null) {
                if (line.indexOf("-")<0){
                    switch(dataBlock) {
                        case 1:     //streets
                            String aWords[] = line.split(",");
                            arrStreet.add(new Street(aWords[0],Integer.parseInt(aWords[1]),Integer.parseInt(aWords[2])));
                            break;
                        case 2:     //roads from street to street
                            String aWords2[] = line.split(" ");
                            arrRoad.add(new Road(arrStreet,Integer.parseInt(aWords2[0]),Integer.parseInt(aWords2[1])));
                            break;
                        case 3:     //who is pizzeria
                            base = arrStreet.get(Integer.parseInt(line)-1);
                            break;
                        case 4:     //how many cars
                            for(int i=0; i<Integer.parseInt(line); i++) {
                                 carArr.add(new Car("Машина №"+(i+1)));
                            }
                            break;
                        case 5:     //menu
                            arrMenu.add(line);
                            break;
                        default:
                            break;
                    }
                }else {
                    dataBlock++;
                }
                line = buff.readLine();
            }
            findAllDistance();  //get distance from addresses to pizzeria
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void printAll(){
        for (Street str : arrStreet){
            System.out.println(str.getName()+","+str.getX()+","+str.getY()+", дист="+str.getDistanceToPizza());
        }
        System.out.println("------------");
        for (Road road : arrRoad){
            System.out.println(road.getPoint1().getName()+"<=>"+road.getPoint2().getName()+"="+road.getDistance());
        }
        System.out.println("------------");
        System.out.println(base.getName());
        System.out.println("------------");
        System.out.println(carArr.size());
        System.out.println("------------");
        for (String str : arrMenu){
            System.out.println(str);
        }
    }

    private double findAllDistance(){   //Deykstra
        double dist=0;                  //recounted to the incoming data - it seems to be correct
        base.setDistanceToPizza(0);     //pizzeria
        int roadCount = 0;
        while(roadCount<arrRoad.size()){    //until we cross all distances
            for(Road road : arrRoad) {
                int fl=0;
                if (road.getPoint1().getDistanceToPizza() != Double.POSITIVE_INFINITY) { //check one top
                    double min = Math.min(road.getDistance()+road.getPoint1().getDistanceToPizza(),road.getPoint2().getDistanceToPizza());
                    road.getPoint2().setDistanceToPizza(min);
                    fl++;
                }
                if (road.getPoint2().getDistanceToPizza() != Double.POSITIVE_INFINITY) { //check other top
                    double min = Math.min(road.getDistance()+road.getPoint2().getDistanceToPizza(),road.getPoint1().getDistanceToPizza());
                    road.getPoint1().setDistanceToPizza(min);
                    fl++;
                }
                if(fl>0){
                    roadCount++;
                }
            }
        }
        return dist;
    }

    @Override
    public void run() {

        Scanner in = new Scanner(System.in);
        String mn = "", bufStr = "";
        Street adr = null;

        System.out.println("----------------------------- ПИЦЕРИЯ ------------------------------");
        System.out.println("-------------------------- П Е Т Р О В И Ч -------------------------");

        while (true) {

            while (mn.length() == 0) {
                System.out.println("------------- Наше меню (для выхода напишите 'Выход'): -------------");
                System.out.print("----- ");
                for (String menu : arrMenu) {
                    System.out.print(menu + ((arrMenu.indexOf(menu)==(arrMenu.size()-1)) ? " ----" : " / "));
                }
                System.out.println();
                System.out.println();
                System.out.print("Выберите блюдо : ");
                bufStr = in.next();
                if (bufStr.indexOf("Выход") >= 0) {
                    System.exit(1);
                }
                for (String menu : arrMenu) {
                    if (menu.indexOf(bufStr) >= 0) {
                        mn = menu;
                    }
                }
            }

            while (adr == null) {
                System.out.print("Введите адрес : ");
                bufStr = in.next();
                if (bufStr.indexOf("Выход") >= 0) {
                    System.exit(1);
                }
                for (Street street : arrStreet) {
                    if (street.getName().indexOf(bufStr) >= 0) {
                        adr = street;
                    }
                }
            }

            System.out.println();
            for (Car car : carArr) {
                if ((car.getStreet() == null) && (adr != null)) {
                    car.setStreetMenu(adr, mn);
                    adr = null;
                    mn = "";
                }
                if (car.getStreet() != null){
                    System.out.println(car);
                }
            }

            if (adr!=null){         //if no one take the order
                System.out.print("Нет свободных машин. Будете ждать? Да/Нет: " );
                bufStr = in.next();
                if (bufStr.indexOf("Нет") >= 0) {
                    adr=null;
                    mn="";
                } else {
                    continue;
                }
            }
            System.out.println();
        }
    }
}