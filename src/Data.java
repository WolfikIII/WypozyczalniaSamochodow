import com.mysql.cj.log.Log;

import java.awt.image.RescaleOp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data extends Login {
    Scanner scanner = new Scanner(System.in);
    static String URL = "jdbc:mysql://localhost:3306/samochody";
    static String USER = "root";
    static String PASSWORD = "zaq1@WSXcde3";
    List<Car> carList = new ArrayList<>();

    public List<Car> importDataToList(){
        int numberOfCars=0;
        int lastId=0;
        long id=0;
        String mark;
        String model;
        int production_date;
        double price;
        String isAvailable;


        try{
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String lastIdQuery = "SELECT COUNT(*) FROM samochody.cars WHERE isAvailable = 'TAK'";
            ResultSet rs = statement.executeQuery(lastIdQuery);
            rs.next();
            numberOfCars = rs.getInt("Count(*)");
            //for(int i=1; i<=lastId; i++){
                Statement statement1 = connection.createStatement();
                String markQuery = "SELECT * FROM samochody.cars WHERE cars.isAvailable = 'TAK'" ;
                ResultSet rs2 = statement1.executeQuery(markQuery);
                while(rs2.next()){
                id++;
                mark = rs2.getString("mark");
                model = rs2.getString("model");
                production_date = rs2.getInt("production_year");
                price = rs2.getDouble("price");
                isAvailable = rs2.getString("isAvailable");
                Car car = new Car(id, mark, model, production_date, price, isAvailable);
                carList.add(car);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return carList;
    }
    public void displayCarList(){
        for (Car car:carList) {
            car.displayCar();
        }
    }

    public void addCarToDB(){
        String mark;
        String model;
        int production_year;
        double price;
        String isAvailable;

        System.out.println("Wpisz markę auta: ");
        mark = scanner.next();
        System.out.println("Podaj model auta: ");
        model = scanner.next();
        System.out.println("Podaj rok produckji auta: ");
        production_year = scanner.nextInt();
        System.out.println("Podaj cene za wynajem: ");
        price = scanner.nextDouble();
        isAvailable = "TAK";
        try{
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String insertCarIntoDB = "INSERT INTO samochody.cars (mark, model, production_year, price, isAvailable) VALUES ('" +mark+ "','"+model+ "','"+production_year+ "','"+price+"','"+isAvailable+ "')";
            statement.executeUpdate(insertCarIntoDB);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void carRenting(){
        displayCarList();
        System.out.println("Wpisz numer auta: ");
        int number = scanner.nextInt();
        int accNumber;
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Wybrałeś samochód: "); carList.get(number-1).displayCar();
            carList.remove(number-1);
            Statement statement2 = connection.createStatement();
            String updateDB = "UPDATE samochody.cars SET isAvailable = 'NIE' WHERE id_car = '"+number+"'";
            statement2.executeUpdate(updateDB);
            int countUsers;
            Statement statement3 = connection.createStatement();
            String countUsersQuery = "SELECT id_user FROM samochody.wypozyczalnia ORDER BY id_user DESC";
            ResultSet rs = statement3.executeQuery(countUsersQuery);
            rs.next();
            countUsers = rs.getInt("id_user");
            int table[] = new int[countUsers];
            Statement statement4 = connection.createStatement();
            String accountWithCars = "SELECT id_user FROM samochody.wypozyczalnia";
            ResultSet rs2 = statement4.executeQuery(accountWithCars);
                for(int i=0; i<table.length; i++){
                    rs2.next();
                    table[i] = rs2.getInt("id_user");
                }
                boolean flag = true;
            for(int i=0; i< table.length; i++) {
            if(table[i] == number)
                flag=false;
            }
               if(flag == false){
                   System.out.println("Nie możesz wypożyczyć drugiego auta!");
               }
               else {
                   Statement statement = connection.createStatement();
                   String query = "SELECT id_user FROM samochody.user WHERE login='"+login+"'";
                   ResultSet resultSet = statement.executeQuery(query);
                   resultSet.next();
                   accNumber = resultSet.getInt("id_user");
                   String insertToDB = "INSERT INTO samochody.wypozyczalnia (id_user, id_car, date) VALUES ('"+accNumber+"', '"+number+"', null)";
                   Statement statement1 = connection.createStatement();
                   statement1.executeUpdate(insertToDB);
               }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void carAdding() {
        System.out.println("Podaj marke:");
        String car_mark = scanner.next();
        System.out.println("Podaj model:");
        String car_model = scanner.next();
        System.out.println("Podaj rok produckji:");
        int car_year = scanner.nextInt();
        System.out.println("Podaj cene: ");
        double car_price = scanner.nextDouble();
        String car_availibility = "TAK";
        try {
            Connection connection1 = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection1.createStatement();
            String addingCar = "DELIMITER // CREATE PROCEDURE addNewCar (IN car_mark VARCHAR(25), IN car_model VARCHAR(25), IN car_year int, IN car_price double, IN car_availibility VARCHAR (3))" +
                    "BEGIN" +
                    "INSERT samochody.cars (mark, model, production_year, price, isAvailable) VALUES (car_mark, car_model, car_year, car_price, car_availibility);" +
                    "END //" +
                    "DELIMETER ;";
            statement.executeUpdate(addingCar);
            //Statement statement1 = connection1.createStatement();
            //String procedureQuery = "CALL addNewCar(null, '"+car_mark+"', '"+car_model+"', '"+car_year+"', '" +car_price+"', '" +car_availibility+"')";
           // statement1.executeUpdate(procedureQuery);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
