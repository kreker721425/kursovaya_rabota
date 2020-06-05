package elementsJFX;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class MyTableColumn {

    public TableColumn<Object, String> tableColumn;

    public MyTableColumn(String nameColumn, String classColumn, int width, boolean flag){
        if (flag) {
            tableColumn = new TableColumn<>(nameColumn);
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(classColumn));
            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            tableColumn.setMinWidth(width);
        }
        else {
            tableColumn = new TableColumn<>(nameColumn);
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(classColumn));
            tableColumn.setMinWidth(width);
        }
    }

    public TableColumn<Object,String> getTc(){
        return tableColumn;
    }
}
