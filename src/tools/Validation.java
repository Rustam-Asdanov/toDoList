package tools;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javax.swing.*;

public class Validation {
    private Validation(){}

    public static void checkField(TextField...textFields){
        for(TextField tf : textFields){
            tf.textProperty().addListener( (observable, oldValue, newValue) -> {
                if(newValue.length()<6) tf.setStyle("-fx-border-color: orange;");
                if(!Character.isAlphabetic(newValue.charAt(0))) tf.setStyle("-fx-border-color: red;");
            });
        }
    }

    public static boolean isEmptyField(TextField...textFields){
        for(TextField tf : textFields){
            if(tf.getText().isEmpty()) {
                JOptionPane optionPane = new JOptionPane("Заполните все поля.", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Внимание!");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                return true;
            }
        }
        return false;
    }

    public static boolean isEmptyDate(DatePicker...datePickers){
        for(DatePicker tf : datePickers){
            if(tf.getValue() == null) {
                JOptionPane optionPane = new JOptionPane("Заполните все поля.", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Внимание!");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                return true;
            }
        }
        return false;
    }

    public static void successOperation(){
        JOptionPane.showOptionDialog(
                null, "Действе завершено удачно","Уведомление",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                null, new Object[]{}, null);
    }

    public static void clearField(TextField...textFields){
        for(TextField tx : textFields) tx.setText("");
    }
}
