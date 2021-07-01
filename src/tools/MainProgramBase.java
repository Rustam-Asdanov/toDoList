package tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.TableClass;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class MainProgramBase {
    private static Statement statement = null;
    private static final int user_id;

    private MainProgramBase(){}

    static {
        statement = DataBase.getStatement();
        user_id = DataBase.getId();
    }

    //insert functions
    public static void createTask(TableClass task){
        String query = String.format("insert into task(t_task, t_day, t_date, t_category, t_status, t_owner)" +
                "values ('%s', '%d', '%tF', '%s', '%s', '%d');", task.getTask(), task.getDay(), task.getDate(),
                task.getCategory(), task.getStatus(), user_id);
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createCategory(String categoryName){
        String query = String.format("insert into category(c_name, c_owner) values" +
                "('%s','%d');",categoryName, user_id);
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //delete information
    public static void deleteTask(int id, String category){
        String query = "";
        if(!category.isEmpty()) {
            query = String.format("delete from task where t_category='%s' and t_owner='%d';",category,user_id);
        }
        else {
            query = String.format("delete from task where t_id='%d' and t_owner='%d';",id,user_id);
        }
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteCategory(String category){
        String query = String.format("delete from category where c_name='%s' AND c_owner='%s';",category,user_id);
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //get informations
    public static ObservableList<TableClass> getTask(String info){
        ObservableList<TableClass> list = FXCollections.observableArrayList();
        int count = 1;
        try {
            ResultSet resultSet = null;
            if(info.equals("Выполнено")) resultSet = statement.executeQuery(
                    "select * from task where t_status = 'Выполнено' and t_owner = '"+ user_id + "';");
            else if(info.equals("Не выполнено")) resultSet = statement.executeQuery(
                    "select * from task where t_status = 'Не выполнено' and t_owner = '" + user_id + "';");
            else resultSet = statement.executeQuery("select * from task where t_owner = '" + user_id + "';");
            while(resultSet.next()){
                list.add(new TableClass(
                        count++, resultSet.getInt("t_id"), resultSet.getString("t_task"),
                        resultSet.getByte("t_day"), LocalDate.parse(""+resultSet.getDate("t_date")),
                        resultSet.getString("t_category"), resultSet.getString("t_status")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static ObservableList<String> getCategory(){
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = statement.executeQuery("select * from category where c_owner = "+user_id+";");
            while(resultSet.next()){
                list.add(resultSet.getString("c_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    //update
    public static void setStatus(int id, String status){
        String query = String.format("update task set t_status = '%s' where t_id='%d' and t_owner='%d';" ,status, id, user_id);
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateSelectedItem(TableClass tc){
        String query = String.format("update task set t_task = '%s', t_day='%d', t_date='%tF', t_category='%s' " +
                "where t_id='%d' and t_owner='%d';",tc.getTask(), tc.getDay(), Date.valueOf(tc.getDate()),
                tc.getCategory(),tc.getId(),user_id);
        System.out.println(query);
        try {
            statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
