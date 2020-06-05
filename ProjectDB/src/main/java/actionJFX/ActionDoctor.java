package actionJFX;

import classEntity.Child;
import classEntity.Doctor;
import dao.ChildDao;
import dao.DoctorDao;
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


public class ActionDoctor {

    private static DoctorDao doctorDao = new DoctorDao();
    private static ChildDao childDao = new ChildDao();

    private static List<String> filter = new ArrayList<>();

    static MyAlert failureInfo = new MyAlert(Alert.AlertType.ERROR,"Заполнены не все поля!");
    static MyAlert numberFormatAlert = new MyAlert(Alert.AlertType.ERROR,"ID должен содержать только цифры");
    static MyAlert exemplarNotFound = new MyAlert(Alert.AlertType.ERROR,"Экземпляр не найден!");
    static MyAlert connectionExists = new MyAlert(Alert.AlertType.ERROR,"Такая связь уже существует");



    public static void findWindows(Window window){
        Stage stage = new Stage();
        stage.setTitle("Поиск");

        MyLabel lbWelcome = new MyLabel("Выберите критерии для поиска по таблице", 14, 60, 15);
        MyLabel lbId = new MyLabel("По id", 12, 40, 50);
        MyLabel lbName = new MyLabel("По имени", 12, 210, 50);
        MyLabel lbSpeciality = new MyLabel("По специальности", 12, 80, 100);
        MyLabel lbBirthday = new MyLabel("По дню рождения", 12, 265, 100);

        MyTextField tfId = new MyTextField(50, 12, 30, 70);
        MyTextField tfName = new MyTextField(250, 12, 115, 70);
        MyTextField tfSpeciality = new MyTextField(200, 12, 30, 120);
        MyTextField tfBirthday = new MyTextField(100, 12, 265, 120);

        MyButton btnNext = new MyButton("Применить", 12, 335, 30, 160);
        btnNext.btn.setOnAction(event -> {
            if ((!tfId.getText().isEmpty()) || (!tfName.getText().isEmpty()) ||
                    (!tfSpeciality.getText().isEmpty()) || (!tfBirthday.getText().isEmpty())) {
                if (!filter.isEmpty())
                    filter.clear();
                try {
                    if (!tfId.getText().isEmpty())
                        Integer.parseInt(tfId.getText());
                    filter.add(tfId.getText());
                    filter.add(tfName.getText());
                    filter.add(tfSpeciality.getText());
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

        Pane root = new Pane(lbWelcome.getLb(), lbId.getLb(), lbName.getLb(), lbSpeciality.getLb(), lbBirthday.getLb(),
                tfId.getTf(), tfName.getTf(), tfSpeciality.getTf(), tfBirthday.getTf(), btnNext.getBtn());
        Scene scene = new Scene(root, 400, 200, Color.TRANSPARENT);
        MyStage stg = new MyStage(stage,scene,window);
        stg.showAndWait();
    }

    public static void addWindows(Window window){
        Stage stage = new Stage();
        stage.setTitle("Добавление");

        MyLabel lbWelcome = new MyLabel("Заполните данные об экземпляре", 14, 135, 15);
        MyLabel lbName = new MyLabel("Имя", 12, 97, 50);
        MyLabel lbSpeciality = new MyLabel("Специальность", 12, 247, 50);
        MyLabel lbBirthday = new MyLabel("День рождения", 12, 383, 50);

        MyTextField tfName = new MyTextField(170, 12, 25, 70);
        MyTextField tfSpeciality = new MyTextField(150, 12, 220, 70);
        MyTextField tfBirthday = new MyTextField(80, 12, 395, 70);

        MyButton btnNext = new MyButton("Добавить", 12, 200, 275, 110);
        btnNext.btn.setOnAction(event -> {
            if ((!tfName.getText().isEmpty()) && (!tfSpeciality.getText().isEmpty()) && (!tfBirthday.getText().isEmpty())) {
                if (!filter.isEmpty())
                    filter.clear();
                if (ActionUtils.checkDate(tfBirthday.getText())!=null) {
                    filter.add(tfName.getText());
                    filter.add(tfSpeciality.getText());
                    filter.add(ActionUtils.checkDate(tfBirthday.getText()));
                    stage.close();
                }
            } else
                failureInfo.showAndWait();
        });

        MyButton btnExit = new MyButton("Назад", 12, 200, 25, 110);
        btnExit.setOnActionExit(stage);

        Pane root = new Pane(lbWelcome.getLb(), lbName.getLb(), lbSpeciality.getLb(), lbBirthday.getLb(),
                tfName.getTf(), tfSpeciality.getTf(), tfBirthday.getTf(), btnNext.getBtn(), btnExit.getBtn());
        Scene scene = new Scene(root, 500, 150, Color.TRANSPARENT);
        MyStage stg = new MyStage(stage,scene,window);
        stg.showAndWait();
    }

    public static boolean find(Stage stage, ObservableList<Doctor> listDoctors){
        findWindows(stage);
        if (!listDoctors.isEmpty())
            listDoctors.clear();
        if (!filter.isEmpty()) {

            if (!filter.get(0).isEmpty()) {
                Doctor doctor = doctorDao.findById(Integer.parseInt(filter.get(0)));
                if ((filter.get(1).isEmpty() && filter.get(2).isEmpty() && filter.get(3).isEmpty()) ||
                        (filter.get(1).equals(doctor.getName()) && filter.get(2).equals(doctor.getSpeciality()) && filter.get(3).equals(doctor.getBirthday())) ||
                        (filter.get(1).isEmpty() && filter.get(2).equals(doctor.getSpeciality()) && filter.get(3).equals(doctor.getBirthday())) ||
                        (filter.get(2).isEmpty() && filter.get(1).equals(doctor.getName()) && filter.get(3).equals(doctor.getBirthday())) ||
                        (filter.get(3).isEmpty() && filter.get(2).equals(doctor.getSpeciality()) && filter.get(1).equals(doctor.getName())) ||
                        (filter.get(1).isEmpty() && filter.get(2).isEmpty() && filter.get(3).equals(doctor.getBirthday())) ||
                        (filter.get(1).isEmpty() && filter.get(3).isEmpty() && filter.get(2).equals(doctor.getSpeciality())) ||
                        (filter.get(2).isEmpty() && filter.get(3).isEmpty() && filter.get(1).equals(doctor.getName())))
                    listDoctors.add(doctor);

            } else
            if (!filter.get(1).isEmpty()) {
                List<Doctor> doctors = doctorDao.findByName(filter.get(1));
                for (Doctor doctor : doctors) {
                    if ((filter.get(2).isEmpty() && filter.get(3).isEmpty()) ||
                            (filter.get(2).equals(doctor.getSpeciality()) && filter.get(3).equals(doctor.getBirthday())) ||
                            (filter.get(2).isEmpty() && filter.get(3).equals(doctor.getBirthday())) ||
                            (filter.get(3).isEmpty() && filter.get(2).equals(doctor.getSpeciality())))
                        listDoctors.add(doctor);
                }
            } else
            if (!filter.get(2).isEmpty()) {
                List<Doctor> doctors = doctorDao.findBySpeciality(filter.get(2));
                for (Doctor doctor : doctors)
                    if (filter.get(3).isEmpty() || filter.get(3).equals(doctor.getBirthday()))
                        listDoctors.add(doctor);
            } else
            if (!filter.get(3).isEmpty()) {
                List<Doctor> doctors = doctorDao.findByBirthday(filter.get(3));
                listDoctors.addAll(doctors);
            }
            filter.clear();
            return true;
        }
        else
            return false;
    }

    public static void add(Stage stage, ObservableList<Doctor> listDoctors, boolean flagFindDaughters, Child child){
        if (flagFindDaughters) {
            ActionUtils.enterId(stage, filter);
            if (!filter.isEmpty()) {
                try {
                    Doctor doctorById = doctorDao.findById(Integer.parseInt(filter.get(0)));
                    boolean flag = false;
                    for (Doctor doctor : listDoctors) {
                        if (doctorById.equals(doctor)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        listDoctors.add(doctorById);
                        childDao.updateDoctors(child, listDoctors);
                    }
                    else
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
                doctorDao.add(new Doctor(filter.get(0), filter.get(1),filter.get(2)));
                filter.clear();
            }
        }
    }

    public static void deleteAll(boolean flagFind, boolean flagFindDaughters, ObservableList<Doctor> listDoctors, Child child){
        if (flagFind) {
            for (Doctor doctor : listDoctors)
                doctorDao.delete(doctor);
            listDoctors.clear();
        }
        else
        if (flagFindDaughters) {
            childDao.updateDoctors(child, null);
            listDoctors.clear();
        }
        else
            doctorDao.deleteAll();
    }

    public static void delete(boolean flagFind, boolean flagFindDaughters, ObservableList<Doctor> listDoctors, Doctor doctor,Child child){
        if (flagFind) {
            doctorDao.delete(doctor);
            listDoctors.remove(doctor);
        } else if (flagFindDaughters) {
            listDoctors.remove(doctor);
            childDao.updateDoctors(child, listDoctors);
        } else {
            doctorDao.delete(doctor);
        }
    }
}
