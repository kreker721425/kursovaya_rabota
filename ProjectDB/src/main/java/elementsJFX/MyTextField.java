package elementsJFX;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class MyTextField {

    private TextField tf = new TextField();

    public MyTextField(int prefWidth, int sizeFont, int x, int y){

        tf.setPrefWidth(prefWidth);                           //Размер строки ввода
        tf.setFont(new Font("Arial", sizeFont));        //Настройка шрифта
        tf.setLayoutX(x);                                     //Положение по x
        tf.setLayoutY(y);                                     //Положение по y
    }

    public TextField getTf() {
        return tf;
    }

    public String getText() {
        return tf.getText();
    }

    public void setText(String str) {
        this.tf.setText(str);
    }
}
