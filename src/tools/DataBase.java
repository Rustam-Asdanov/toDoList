package tools;

import javafx.scene.control.Label;
import java.sql.*;

public class DataBase {
    private static Connection conn;
    private static Statement statement;
    private static int user_id;

    private DataBase(){}

    static{
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/toDoList",
                    "root","12345"
            );
            statement = conn.createStatement();
            statement.executeQuery("SET SQL_SAFE_UPDATES = 0;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addUser(String login, String password){
        try {
            statement.executeUpdate(
                    String.format("insert into user(login, passwords) values ('%s','%s');",login,password)
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean isUser(String login, String password){
        try {
            ResultSet resultSet = statement.executeQuery("select * from user;");
            while(resultSet.next()){
                if(resultSet.getString("login").equals(login) &&
                resultSet.getString("passwords").equals(password)
                ) {
                    user_id = resultSet.getInt("id");
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void deleteUser(Label label, String login){
        try{
            System.out.println(user_id);
            statement.executeUpdate(String.format("delete from task where t_owner = %d;", user_id));
            statement.executeUpdate(String.format("delete from category where c_owner = %d;", user_id));
            statement.executeUpdate(
                    String.format("delete from user where id = %d;", user_id)
            );
            label.setText("Пользователь удачно удалён.");
            statement.addBatch("SET @num := 0;");
            statement.addBatch("UPDATE user SET id = @num := (@num+1);");
            statement.addBatch("ALTER TABLE user AUTO_INCREMENT = 1;");
            statement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Statement getStatement(){
        return statement;
    }
    public static int getId(){
        return user_id;
    }
}
