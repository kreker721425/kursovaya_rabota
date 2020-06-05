package elementsJFX;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MyButton {
    public Button btn = new Button();

    public MyButton(String str, int sizeFont, int prefWidth, int x, int y){

        btn.setText(str);                                     //Установка текста
        btn.setFont(new Font("Arial", sizeFont));          //Настройка шрифта
        btn.setPrefWidth(prefWidth);                          //Размер кнопки
        btn.setLayoutX(x);                                    //Положение по x
        btn.setLayoutY(y);                                    //Положение по y
    }

    public Button getBtn() {
        return btn;
    }

    public void setOnActionExit(Stage stage) {btn.setOnAction(event -> stage.close());}

    public void setDisable(boolean flag) {
        btn.setDisable(flag);
    }

    public void setWork(boolean flag){
        btn.setDisable(!flag);
        btn.setVisible(flag);
    }
}
