package actionJFX;

import classEntity.Child;
import classEntity.HospitalRoom;
import classEntity.Mother;
import dao.ChildDao;
import dao.HospitalRoomDao;
import dao.MotherDao;
import elementsJFX.*;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;

public class ActionMother {

    private static HospitalRoomDao hospitalRoomDao = new HospitalRoomDao();
    private static ChildDao childDao = new ChildDao();
    private static MotherDao motherDao = new MotherDao();

    private static List<String> filter = new ArrayList<>();

    static MyAlert numberFormatAlert = new MyAlert(Alert.AlertType.ERROR, "ID должен содержать только цифры");
    static MyAlert failureInfo = new MyAlert(Alert.AlertType.ERROR, "Заполнены не все поля!");
    static MyAlert exemplarNotFound = new MyAlert(Alert.AlertType.ERROR, "Экземпляр не найден!");
    static MyAlert hospitalRoomIsFull = new MyAlert(Alert.AlertType.ERROR, "Палата заполнена!");
    static MyAlert connectionExists = new MyAlert(Alert.AlertType.ERROR, "Такая связь уже существует");

    public static void findWindows(Window window) {
        Stage stage = new Stage();
        stage.setTitle("Поиск");

        MyLabel lbWelcome = new MyLabel("Выберите критерии для поиска по таблице", 14, 123, 15);
        MyLabel lbId = new MyLabel("По id", 12, 40, 50);
        MyLabel lbName = new MyLabel("По имени", 12, 200, 50);
        MyLabel lbBirthday = new MyLabel("По дню рождения", 12, 370, 50);

        MyTextField tfId = new MyTextField(50, 12, 30, 70);
        MyTextField tfName = new MyTextField(220, 12, 115, 70);
        MyTextField tfBirthday = new MyTextField(100, 12, 370, 70);

        MyButton btnNext = new MyButton("Принять", 12, 450, 30, 110);
        btnNext.btn.setOnAction(event -> {
            if ((!tfId.getText().isEmpty()) || (!tfName.getText().isEmpty()) || (!tfBirthday.getText().isEmpty())) {
                if (!filter.isEmpty())
                    filter.clear();
                try {
                    if (!tfId.getText().isEmpty())
                        Integer.parseInt(tfId.getText());
                    filter.add(tfId.getText());
                    filter.add(tfName.getText());
                    if (!tfBirthday.getText().isEmpty()) {
                        if (ActionUtils.checkDate(tfBirthday.getText()) != null) {
                            filter.add(ActionUtils.checkDate(tfBirthday.getText()));
                            stage.close();
                        }
                    } else {
                        filter.add(tfBirthday.getText());
                        stage.close();
                    }
                } catch (NumberFormatException e) {
                    numberFormatAlert.showAndWait();
                }
            } else
                stage.close();
        });


        Pane root = new Pane(lbWelcome.getLb(), lbId.getLb(), lbName.getLb(), lbBirthday.getLb(),
                tfId.getTf(), tfName.getTf(), tfBirthday.getTf(), btnNext.getBtn());
        Scene scene = new Scene(root, 500, 150, Color.TRANSPARENT);
        MyStage stg = new MyStage(stage, scene, window);
        stg.showAndWait();
    }

    public static void addWindows(Window window) {
        Stage stage = new Stage();
        stage.setTitle("Добавление");

        MyLabel lbWelcome = new MyLabel("Заполните данные об экземпляре", 14, 48, 15);
        MyLabel lbName = new MyLabel("Имя", 12, 97, 50);
        MyLabel lbBirthday = new MyLabel("День рождения", 12, 208, 50);

        MyTextField tfName = new MyTextField(170, 12, 25, 70);
        MyTextField tfBirthday = new MyTextField(80, 12, 220, 70);

        MyButton btnNext = new MyButton("Принять", 12, 130, 170, 110);
        btnNext.btn.setOnAction(event -> {
            if ((!tfName.getText().isEmpty()) && (!tfBirthday.getText().isEmpty())) {
                if (!filter.isEmpty())
                    filter.clear();
                if (ActionUtils.checkDate(tfBirthday.getText()) != null) {
                    filter.add(tfName.getText());
                    filter.add(ActionUtils.checkDate(tfBirthday.getText()));
                    stage.close();
                }
            } else
                failureInfo.showAndWait();
        });
        MyButton btnExit = new MyButton("Назад", 12, 130, 25, 110);
        btnExit.setOnActionExit(stage);

        Pane root = new Pane(lbWelcome.getLb(), lbName.getLb(), lbBirthday.getLb(),
                tfName.getTf(), tfBirthday.getTf(), btnNext.getBtn(), btnExit.getBtn());
        Scene scene = new Scene(root, 325, 150, Color.TRANSPARENT);
        MyStage stg = new MyStage(stage, scene, window);
        stg.showAndWait();
    }

    public static boolean find(Stage stage, ObservableList<Mother> listMothers) {
        findWindows(stage);
        if (!listMothers.isEmpty())
            listMothers.clear();
        if (!filter.isEmpty()) {

            if (!filter.get(0).isEmpty()) {
                Mother mother = motherDao.findById(Integer.parseInt(filter.get(0)));
                if ((filter.get(1).isEmpty() && filter.get(2).isEmpty()) ||
                        (filter.get(1).equals(mother.getName()) && filter.get(2).equals(mother.getBirthday())) ||
                        (filter.get(1).isEmpty() && filter.get(2).equals(mother.getBirthday())) ||
                        (filter.get(2).isEmpty() && filter.get(1).equals(mother.getName())))
                    listMothers.add(mother);
            } else if (!filter.get(1).isEmpty()) {
                List<Mother> mothers = motherDao.findByName(filter.get(1));
                for (Mother mother : mothers)
                    if (filter.get(2).isEmpty() || filter.get(2).equals(mother.getBirthday()))
                        listMothers.add(mother);
            } else if (!filter.get(2).isEmpty()) {
                List<Mother> mothers = motherDao.findByBirthday(filter.get(2));
                listMothers.addAll(mothers);
            }
            filter.clear();
            return true;
        } else
            return false;
    }

    public static void add(Stage stage, ObservableList<Mother> listMothers, boolean flagFindDaughters, Child child, HospitalRoom hospitalRoom) {
        if (flagFindDaughters) {
            ActionUtils.enterId(stage, filter);
            if (!filter.isEmpty()) {
                try {
                    Mother motherById = motherDao.findById(Integer.parseInt(filter.get(0)));
                    boolean flag = false;
                    for (Mother mother : listMothers) {
                        if (motherById.equals(mother)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        if (child != null) {
                            listMothers.setAll(motherById);
                            childDao.updateMother(child, listMothers.get(0));
                        }
                        if (hospitalRoom != null) {
                            if (hospitalRoomDao.findMothers(hospitalRoom).size() != Integer.parseInt(hospitalRoom.getNumberOfPlaces())) {
                                listMothers.add(motherById);
                                hospitalRoomDao.updateMothers(hospitalRoom, listMothers);
                            } else
                                hospitalRoomIsFull.showAndWait();
                        }
                    } else
                        connectionExists.showAndWait();
                    filter.clear();
                } catch (NullPointerException exception) {
                    exemplarNotFound.showAndWait();
                }
            }
        } else {
            addWindows(stage);
            if (!filter.isEmpty()) {
                motherDao.add(new Mother(filter.get(0), filter.get(1)));
                filter.clear();
            }
        }
    }

    public static void deleteAll(boolean flagFind, boolean flagFindDaughters, ObservableList<Mother> listMothers, Child child, HospitalRoom hospitalRoom) {
        if (flagFind) {
            for (Mother mother : listMothers)
                motherDao.delete(mother);
            listMothers.clear();
        } else if (flagFindDaughters) {
            listMothers.clear();
            if (child != null)
                childDao.updateMother(child, null);
            if (hospitalRoom != null)
                hospitalRoomDao.updateMothers(hospitalRoom, null);
        } else
            motherDao.deleteAll();
    }

    public static void delete(boolean flagFind, boolean flagFindDaughters, ObservableList<Mother> listMothers,Mother mother, Child child, HospitalRoom hospitalRoom){
        if (flagFind) {
            motherDao.delete(mother);
            listMothers.remove(mother);
        } else if (flagFindDaughters) {
            listMothers.remove(mother);
            if (child != null)
                childDao.updateMother(child, null);
            if (hospitalRoom != null)
                hospitalRoomDao.updateMothers(hospitalRoom, listMothers);
        } else {
            motherDao.delete(mother);
        }
    }
}