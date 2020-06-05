package elementsJFX;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MyLabel {
    public Label lb = new Label();

    public MyLabel(String str, int sizeFont, int x, int y){

        lb.setText(str);                                                      //Установка текста
        lb.setFont(Font.font("Arial", FontWeight.BOLD, sizeFont));         //Настройка шрифта
        lb.setTextAlignment(TextAlignment.CENTER);                            //Выравнивание по центру
        lb.setWrapText(true);                                                 //Перенос текста на следующую строку
        lb.setLayoutX(x);                                                     //Положение по x
        lb.setLayoutY(y);                                                     //Положение по y
    }

    public Label getLb() {
        return lb;
    }

    public String getText() {
        return lb.getText();
    }
}
