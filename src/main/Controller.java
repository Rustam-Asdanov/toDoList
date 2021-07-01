package main;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tools.MainProgramBase;
import tools.Validation;

import javax.swing.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField taskField;

    @FXML
    private TextField dayField;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private TextField searchField;

    @FXML
    private RadioButton allRadio;

    @FXML
    private RadioButton completeRadio;

    @FXML
    private RadioButton notPerformedRadio;

    @FXML
    private Button completeButton;

    @FXML
    private CheckBox allCheckBoxSelected;

    @FXML
    private TableView<TableClass> table;

    @FXML
    private TableColumn<TableClass, Integer> numberColumn;

    @FXML
    private TableColumn<TableClass, String> taskColumn;

    @FXML
    private TableColumn<TableClass, Integer> dayColumn;

    @FXML
    private TableColumn<TableClass, Date> dateColumn;

    @FXML
    private TableColumn<TableClass, String> categoryColumn;

    @FXML
    private TableColumn<TableClass, String> statusColumn;

    @FXML
    private Label countRowLabel;

    private static ObservableList<TableClass> taskList = FXCollections.observableArrayList();
    private static ObservableList<String> comboList = FXCollections.observableArrayList();
    private static FilteredList<TableClass> filteredList = null;

    @FXML
    void addCategory(ActionEvent event) {
        String category = "";
        while(true) {
            category = JOptionPane.showInputDialog("Enter name");
            if(!category.isEmpty()) break;
            JOptionPane.showOptionDialog(
                    null, "Нельзя оставить поле пустым","Уведомление",
                    JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                    null, new Object[]{}, null);
        }
        MainProgramBase.createCategory(category);
        comboList = MainProgramBase.getCategory();
        categoryBox.setItems(comboList);
    }

    @FXML
    void completeButtonClick(ActionEvent event) {
        if(taskList.isEmpty()) return;
        TableClass tc = table.getSelectionModel().getSelectedItem();
        if(tc.getStatus().equals("Выполнено")) MainProgramBase.setStatus(tc.getId(),"Не выполнено");
        else MainProgramBase.setStatus(tc.getId(),"Выполнено");
        if(allRadio.isSelected())tableData("Всё");
        else if(completeRadio.isSelected()) tableData("Выполнено");
        else tableData("Не выполнено");
    }

    @FXML
    void deleteCategory(ActionEvent event) {
        int info = areYouSure();
        if(info == JOptionPane.YES_NO_OPTION){
            String category = categoryBox.getSelectionModel().getSelectedItem();
            MainProgramBase.deleteCategory(category);
            MainProgramBase.deleteTask(0,category);
            Validation.successOperation();
            comboList = MainProgramBase.getCategory();
            categoryBox.setItems(comboList);
            tableData("Всё");
        }
    }

    @FXML
    void deleteClick(ActionEvent event) {
        if(table.getSelectionModel().getSelectedItem() == null && !allCheckBoxSelected.isSelected()) return;
        int info = areYouSure();
        if(info == JOptionPane.YES_NO_OPTION) {
            if (allCheckBoxSelected.isSelected()) {
                MainProgramBase.deleteTask(0, categoryBox.getSelectionModel().getSelectedItem());
                table.setItems(null);
            } else {
                MainProgramBase.deleteTask(table.getSelectionModel().getSelectedItem().getId(), "");
                if(allRadio.isSelected()) tableData("Всё");
                else if(completeRadio.isSelected()) tableData("Выполнено");
                else tableData("Не выполнено");
            }
            Validation.successOperation();
        }
    }

    @FXML
    void newTaskClick(ActionEvent event) {
        if(Validation.isEmptyField(taskField,dayField)) return;
        if(Validation.isEmptyDate(dataPicker)) return;
        if(comboList.isEmpty()) {
            JOptionPane optionPane = new JOptionPane("Выберите категорию.", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Внимание!");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return;
        }
        System.out.println(categoryBox.getSelectionModel().getSelectedItem());
        TableClass tc = new TableClass(
                0,0, taskField.getText(), Integer.parseInt(dayField.getText()),
                dataPicker.getValue(),
                categoryBox.getSelectionModel().getSelectedItem(), "Не выполнено"
        );
        MainProgramBase.createTask(tc);
        notPerformedRadio.setSelected(true);
        tableData("Не выполнено");
        completeButton.setText("Не выполнено");
    }

    @FXML
    void saveClick(ActionEvent event) {
        if(Validation.isEmptyField(taskField,dayField)) return;
        if(Validation.isEmptyDate(dataPicker)) return;
        TableClass tc = new TableClass(
                0,table.getSelectionModel().getSelectedItem().getId(), taskField.getText(),
                Integer.parseInt(dayField.getText()), dataPicker.getValue(),
                categoryBox.getSelectionModel().getSelectedItem(), "Не выполнено"
        );
        MainProgramBase.updateSelectedItem(tc);
        tableData("Всё");
    }

    @FXML
    void initialize() {
        //table configuration
        numberColumn.setCellValueFactory(new PropertyValueFactory<TableClass,Integer>("number"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<TableClass, String>("task"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<TableClass, Integer>("day"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TableClass, Date>("date"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<TableClass, String>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<TableClass, String>("status"));

        //combobox configuration
        ToggleGroup toggleGroup = new ToggleGroup();
        allRadio.setToggleGroup(toggleGroup);
        completeRadio.setToggleGroup(toggleGroup);
        notPerformedRadio.setToggleGroup(toggleGroup);
        comboList = MainProgramBase.getCategory();
        categoryBox.setItems(comboList);
        categoryBox.setPlaceholder(new Label("Выберите категорию."));

        tableData("Всё");
        allRadio.setSelected(true);
        if(!comboList.isEmpty()) categoryBox.getSelectionModel().selectFirst();
        elemenProperty();
        table.setPlaceholder(new Label("Таблица пустая"));
    }

    private static int areYouSure(){
        return JOptionPane.showConfirmDialog(null, "Вы уверены?",
                "Уведомление",JOptionPane.YES_NO_OPTION);
    }

    private void elemenProperty(){
        //search field
        filteredList = new FilteredList<>(taskList, p->true);
        searchField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(p->p.getTask().toLowerCase().startsWith(newValue.toLowerCase().trim()));
            table.setItems(filteredList);
            if(newValue.isEmpty()) table.setItems(taskList);
            countRowLabel.setText(""+filteredList.size());
        });

        taskField.textProperty().addListener( (observable, oldValue, newValue)->{
            if(newValue.length()>=100) taskField.setText(oldValue);
        } );

        dayField.textProperty().addListener((observable, oldValue, newValue)->{
           if(!newValue.matches("\\d*")) dayField.setText(newValue.replaceAll("[^\\d]",""));
        });

        //table
        allRadio.selectedProperty().addListener((e)->{
            tableData("Всё");
        });

        completeRadio.selectedProperty().addListener((e)->{
            tableData("Выполнено");
        });

        notPerformedRadio.selectedProperty().addListener((e)->{
            tableData("Не выполнено");
        });

        table.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            try{
                TableClass tc = table.getSelectionModel().getSelectedItem();
                taskField.setText(tc.getTask());
                dayField.setText("" + tc.getDay());
                dataPicker.setValue(tc.getDate());
                categoryBox.getSelectionModel().select(tc.getCategory());
                if(newValue.getStatus().equals("Выполнено")) completeButton.setText("Не выполнено");
                else completeButton.setText("Выполнено");
            } catch(NullPointerException nex){

            }
        });
    }

    private void tableData(String text){
        taskList = MainProgramBase.getTask(text);
        filteredList = new FilteredList<>(taskList, p->true);
        table.setItems(taskList);
        countRowLabel.setText(""+taskList.size());
    }
}
