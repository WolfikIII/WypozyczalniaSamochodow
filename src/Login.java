import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Login {
    static boolean flag = true;
    static String login;
    static String password;
    static String email;
    Scanner scan = new Scanner(System.in);
    static String URL = "jdbc:mysql://localhost:3306/samochody";
    static String USER = "root";
    static String PASSWORD = "zaq1@WSXcde3";

    public void signIn() {
        System.out.println("Login:");
        login = scan.next();
        System.out.println("Hasło:");
        password = scan.next();

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            String loginQuery = "SELECT * FROM samochody.user WHERE user.login = '" + login + "'and user.password = '" + password + "'";
            ResultSet rs = statement.executeQuery(loginQuery);
            if (rs.next()) {
                System.out.println("Zalogowano!");
            } else System.out.println("Zły login lub hasło!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signUp() {
        while(flag) {
            System.out.println("Wpisz login");
            login = scan.next();
            try {
                Connection connection1 = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection1.createStatement();
                String queryLogin = "SELECT * FROM samochody.user WHERE login = '" + login + "'";
                ResultSet rs1 = statement.executeQuery(queryLogin);
                if (rs1.next()) {
                    System.out.println("Podany login istnieje, spróbuj podać inny!");
                }
                else flag = false;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("Wpisz hasło");
        password = scan.next();
        System.out.println("Podaj email");
        email = scan.next();
        if (email.contains("@")) {
            System.out.println("Konto zostało utworzone!");
        } else {
            System.out.println("Nieprawidłowy adres email");
        }
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String registryQuery = "INSERT INTO samochody.user (login, password, email) VALUES ('" + login + "', '" + password + "', '" + email + "')";
            Statement create = connection.createStatement();
            create.executeUpdate(registryQuery);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        }
}

