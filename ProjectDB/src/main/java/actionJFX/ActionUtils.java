package actionJFX;

import elementsJFX.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Date;
import java.util.List;

public class ActionUtils {


    static MyAlert notCorrectDate = new MyAlert(Alert.AlertType.ERROR,"Некорректно введена дата! (dd.mm.yyyy)");
    static MyAlert numberFormatForDate = new MyAlert(Alert.AlertType.ERROR,"Дата должна содержать только цифры");
    static MyAlert numberFormatForId = new MyAlert(Alert.AlertType.ERROR,"ID должен содержать только цифры");
    static MyAlert failureInfo = new MyAlert(Alert.AlertType.ERROR,"Поле не заполнено!");

    public static String checkDate(String birthday) {
        String[] dateSplit = birthday.split("\\.");
        if (dateSplit.length != 3) {
            notCorrectDate.showAndWait();
            return null;
        }

        Date date = new Date();
        try {
            date.setDate(Integer.parseInt(dateSplit[0]));
            date.setMonth(Integer.parseInt(dateSplit[1])-1);
            date.setYear(Integer.parseInt(dateSplit[2]));
            if ((date.getDate()<10)||(date.getMonth()<9)) {
                if ((date.getDate()<10)&&(date.getMonth()<9))
                    return "0" + date.getDate() + ".0" + (date.getMonth() + 1) + "." + date.getYear();
                if (date.getMonth() < 9)
                    return date.getDate() + ".0" + (date.getMonth() + 1) + "." + date.getYear();
                return "0" + date.getDate() + "." + (date.getMonth() + 1) + "." + date.getYear();
            }
            return date.getDate() + "." + (date.getMonth() + 1) + "." + date.getYear();
        }
        catch (NumberFormatException e) {
            numberFormatForDate.showAndWait();
            return null;
        }
    }

    public static void enterId(Window window, List<String> filter) {
        Stage stage = new Stage();
        stage.setTitle("Ввод id экземпляра");

        MyLabel lbWelcome = new MyLabel("Введите id экземпляра", 14, 56, 15);
        lbWelcome.lb.setPrefWidth(260);
        MyLabel lbId = new MyLabel("id", 12, 15, 60);
        MyTextField tfId = new MyTextField(50, 12, 35, 55);
        MyButton btnNext = new MyButton("Окей", 12, 140, 110, 55);
        btnNext.btn.setOnAction(event -> {
            if (!tfId.getText().isEmpty()) {
                if (!filter.isEmpty())
                    filter.clear();
                try {
                    Integer.parseInt(tfId.getText());
                    filter.add(tfId.getText());
                    stage.close();
                }
                catch (NumberFormatException e){
                    numberFormatForId.showAndWait();
                }
            }
            else
                failureInfo.showAndWait();
        });

        MyButton btnExit = new MyButton("Назад", 12, 235, 15, 90);
        btnExit.setOnActionExit(stage);

        Pane root = new Pane(lbWelcome.getLb(), lbId.getLb(), tfId.getTf(), btnNext.getBtn(), btnExit.getBtn());
        Scene scene = new Scene(root, 270, 130, Color.TRANSPARENT);
        MyStage stg = new MyStage(stage, scene, window);
        stg.showAndWait();
    }

}
