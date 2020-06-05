package elementsJFX;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MyStage {

    private final Stage stg;

    public MyStage(Stage stage, Scene scene){
        stg = stage;
        stg.setResizable(false);                    //Отключение растягивания окна
        stg.setScene(scene);                        //Подключение Scene к Stage
    }

    public MyStage(Stage stage, Scene scene, Window window){
        stg = stage;
        stg.setResizable(false);
        stg.setScene(scene);
        stg.initOwner(window);
        stg.initModality(Modality.WINDOW_MODAL);    //Привязка дочернего окна к родительскому, без возможности закрытия родительского
    }

    public void show() {
        stg.show();
    }

    public void showAndWait() {
        stg.showAndWait();
    }
}
