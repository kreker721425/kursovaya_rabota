package actionJFX;

import classEntity.*;
import dao.*;
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

public class ActionHospitalRoom {

    private static HospitalRoomDao hospitalRoomDao = new HospitalRoomDao();
    private static MotherDao motherDao = new MotherDao();

    private static List<String> filter = new ArrayList<>();

    static MyAlert numberFormatAlert = new MyAlert(Alert.AlertType.ERROR,"Поля: 'Номер палаты', 'Число мест в палате' и 'ID'\n должны содержать только цифры");
    static MyAlert vipFormatAlert = new MyAlert(Alert.AlertType.ERROR,"Некорректно введен Vip-статус! (+/-)");
    static MyAlert exemplarNotFound = new MyAlert(Alert.AlertType.ERROR, "Экземпляр не найден!");
    static MyAlert connectionExists = new MyAlert(Alert.AlertType.ERROR, "Такая связь уже существует");

    public static void findWindows(Window window){
        Stage stage = new Stage();
        stage.setTitle("Поиск");

        MyLabel lbWelcome = new MyLabel("Выберите критерии для поиска по таблице", 14, 128, 15);
        MyLabel lbId = new MyLabel("по id", 12, 40, 50);
        MyLabel lbNumber = new MyLabel("по номеру палаты", 12, 100, 50);
        MyLabel lbVip = new MyLabel("по VIP-статусу(да/нет)", 12, 220, 50);
        MyLabel lbNumberOfPlace = new MyLabel("по числу мест в палате", 12, 375, 50);

        MyTextField tfId = new MyTextField(50, 12, 30, 70);
        MyTextField tfNumber = new MyTextField(100, 12, 100, 70);
        MyTextField tfVip = new MyTextField(135, 12, 220, 70);
        MyTextField tfNumberOfPlace = new MyTextField(130, 12, 375, 70);

        MyButton btnNext = new MyButton("Принять", 12, 475, 30, 110);
        btnNext.btn.setOnAction(event -> {
            if ((!tfId.getText().isEmpty()) || (!tfNumber.getText().isEmpty()) ||
                    (!tfVip.getText().isEmpty()) || (!tfNumberOfPlace.getText().isEmpty())) {
                if (!filter.isEmpty())
                    filter.clear();
                if ((!tfVip.getText().isEmpty()) && (!tfVip.getText().equals("+")) && (!tfVip.getText().equals("-")))
                    vipFormatAlert.showAndWait();
                else {
                    try {
                        if (!tfId.getText().isEmpty())
                            Integer.parseInt(tfId.getText());
                        if (!tfNumber.getText().isEmpty())
                            Integer.parseInt(tfNumber.getText());
                        if (!tfNumberOfPlace.getText().isEmpty())
                            Integer.parseInt(tfNumberOfPlace.getText());
                        filter.add(tfId.getText());
                        filter.add(tfNumber.getText());
                        filter.add(tfVip.getText());
                        filter.add(tfNumberOfPlace.getText());
                        stage.close();
                    } catch (NumberFormatException e) {
                        numberFormatAlert.showAndWait();
                    }
                }
            } else
                stage.close();
        });


        Pane root = new Pane(lbWelcome.getLb(), lbId.getLb(), lbNumber.getLb(), lbVip.getLb(), lbNumberOfPlace.getLb(),
                tfId.getTf(), tfNumber.getTf(), tfVip.getTf(), tfNumberOfPlace.getTf(), btnNext.getBtn());
        Scene scene = new Scene(root, 535, 150, Color.TRANSPARENT);
        MyStage stg = new MyStage(stage,scene,window);
        stg.showAndWait();
    }

    public static void addWindows(Window window){
        Stage stage = new Stage();
        stage.setTitle("Добавление");

        MyLabel lbWelcome = new MyLabel("Заполните данные об экземпляре", 14, 85, 15);
        MyLabel lbNumber = new MyLabel("Номер палаты", 12, 26, 50);
        MyLabel lbVip = new MyLabel("Vip-статус(+/-)", 12, 140, 50);
        MyLabel lbNumberOfPlace = new MyLabel("Число мест в палате", 12, 268, 50);

        MyTextField tfNumber = new MyTextField(100, 12, 25, 70);
        MyTextField tfVip = new MyTextField(100, 12, 150, 70);
        MyTextField tfNumberOfPlace = new MyTextField(100, 12, 275, 70);

        MyButton btnNext = new MyButton("Добавить", 12, 150, 225, 110);
        btnNext.btn.setOnAction(event -> {
            if ((!lbNumber.getText().isEmpty()) && (!lbVip.getText().isEmpty()) && (!lbNumberOfPlace.getText().isEmpty())) {
                if ((!tfVip.getText().equals("+")) && (!tfVip.getText().equals("-")))
                    vipFormatAlert.showAndWait();
                else {
                    if (!filter.isEmpty())
                        filter.clear();
                    try {
                        Integer.parseInt(tfNumber.getText());
                        Integer.parseInt(tfNumberOfPlace.getText());
                        filter.add(tfNumber.getText());
                        filter.add(tfVip.getText());
                        filter.add(tfNumberOfPlace.getText());
                        stage.close();
                    } catch (NumberFormatException e) {
                        numberFormatAlert.showAndWait();
                    }
            }
        }
        });
        MyButton btnExit = new MyButton("Назад", 12, 150, 25, 110);
        btnExit.setOnActionExit(stage);

        Pane root = new Pane(lbWelcome.getLb(), lbNumber.getLb(), lbVip.getLb(), lbNumberOfPlace.getLb(),
                tfNumber.getTf(), tfVip.getTf(), tfNumberOfPlace.getTf(), btnNext.getBtn(), btnExit.getBtn());
        Scene scene = new Scene(root, 400, 150, Color.TRANSPARENT);
        MyStage stg = new MyStage(stage,scene,window);
        stg.showAndWait();
    }

    public static boolean find(Stage stage, ObservableList<HospitalRoom> listHospitalRoom) {
        findWindows(stage);
        if (!listHospitalRoom.isEmpty())
            listHospitalRoom.clear();
        if (!filter.isEmpty()) {

            if (!filter.get(0).isEmpty()) {
                HospitalRoom hospitalRoom = hospitalRoomDao.findById(Integer.parseInt(filter.get(0)));
                if ((filter.get(1).isEmpty() && filter.get(2).isEmpty() && filter.get(3).isEmpty()) ||
                        (filter.get(1).equals(hospitalRoom.getNumber()) && filter.get(2).equals(hospitalRoom.getVip()) && filter.get(3).equals(hospitalRoom.getNumberOfPlaces())) ||
                        (filter.get(1).isEmpty() && filter.get(2).equals(hospitalRoom.getVip()) && filter.get(3).equals(hospitalRoom.getNumberOfPlaces())) ||
                        (filter.get(2).isEmpty() && filter.get(1).equals(hospitalRoom.getNumber()) && filter.get(3).equals(hospitalRoom.getNumberOfPlaces())) ||
                        (filter.get(3).isEmpty() && filter.get(2).equals(hospitalRoom.getVip()) && filter.get(1).equals(hospitalRoom.getNumber())) ||
                        (filter.get(1).isEmpty() && filter.get(2).isEmpty() && filter.get(3).equals(hospitalRoom.getNumberOfPlaces())) ||
                        (filter.get(1).isEmpty() && filter.get(3).isEmpty() && filter.get(2).equals(hospitalRoom.getVip())) ||
                        (filter.get(2).isEmpty() && filter.get(3).isEmpty() && filter.get(1).equals(hospitalRoom.getNumber())))
                    listHospitalRoom.add(hospitalRoom);

            } else
            if (!filter.get(1).isEmpty()) {
                List<HospitalRoom> hospitalRooms = hospitalRoomDao.findByNumber(filter.get(1));
                for (HospitalRoom hospitalRoom : hospitalRooms) {
                    if ((filter.get(2).isEmpty() && filter.get(3).isEmpty()) ||
                            (filter.get(2).equals(hospitalRoom.getVip()) && filter.get(3).equals(hospitalRoom.getNumberOfPlaces())) ||
                            (filter.get(2).isEmpty() && filter.get(3).equals(hospitalRoom.getNumberOfPlaces())) ||
                            (filter.get(3).isEmpty() && filter.get(2).equals(hospitalRoom.getVip())))
                        listHospitalRoom.add(hospitalRoom);
                }
            } else
            if (!filter.get(2).isEmpty()) {
                List<HospitalRoom> hospitalRooms = hospitalRoomDao.findByVip(filter.get(2));
                for (HospitalRoom hospitalRoom : hospitalRooms)
                    if (filter.get(3).isEmpty() || filter.get(3).equals(hospitalRoom.getNumberOfPlaces()))
                        listHospitalRoom.add(hospitalRoom);
            } else
            if (!filter.get(3).isEmpty()) {
                List<HospitalRoom> hospitalRooms = hospitalRoomDao.findByNumberOfPlace(filter.get(3));
                listHospitalRoom.addAll(hospitalRooms);
            }
            filter.clear();
            return true;
        }
        else
            return false;
    }

    public static void add(Stage stage, ObservableList<HospitalRoom> listHospitalRoom, boolean flagFindDaughters, Mother mother) {
        if (flagFindDaughters) {
            ActionUtils.enterId(stage, filter);
            if (!filter.isEmpty()) {
                try {
                    HospitalRoom hospitalRoomById = hospitalRoomDao.findById(Integer.parseInt(filter.get(0)));
                    boolean flag = false;
                    for (HospitalRoom hospitalRoom : listHospitalRoom) {
                        if (hospitalRoomById.equals(hospitalRoom)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        if (mother != null) {
                            listHospitalRoom.setAll(hospitalRoomById);
                            motherDao.updateHospitalRoom(mother, listHospitalRoom.get(0));
                        }
                    } else
                        connectionExists.showAndWait();
                    filter.clear();
                }
                catch (NullPointerException exception) {
                    exemplarNotFound.showAndWait();
                }
            }
        }
        else {
            addWindows(stage);
            if (!filter.isEmpty()) {
                hospitalRoomDao.add(new HospitalRoom(filter.get(0), filter.get(1), filter.get(2)));
                filter.clear();
            }
        }
    }

    public static void deleteAll(boolean flagFind, boolean flagFindDaughters, ObservableList<HospitalRoom> listHospitalRoom, Mother mother) {
        if (flagFind) {
            for (HospitalRoom hospitalRoom : listHospitalRoom)
                hospitalRoomDao.delete(hospitalRoom);
            listHospitalRoom.clear();
        }
        else
        if (flagFindDaughters) {
            listHospitalRoom.clear();
            if (mother != null)
                motherDao.updateHospitalRoom(mother, null);
        }
        else
            hospitalRoomDao.deleteAll();
    }

    public static void delete(boolean flagFind, boolean flagFindDaughters, ObservableList<HospitalRoom> listHospitalRoom,HospitalRoom hospitalRoom, Mother mother){
        if (flagFind) {
            hospitalRoomDao.delete(hospitalRoom);
            listHospitalRoom.remove(hospitalRoom);
        } else if (flagFindDaughters) {
            listHospitalRoom.remove(hospitalRoom);
            if (mother != null)
                motherDao.updateHospitalRoom(mother, null);
        } else {
            hospitalRoomDao.delete(hospitalRoom);
        }
    }

}
