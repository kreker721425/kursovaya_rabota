import actionJFX.*;
import classEntity.*;
import dao.*;
import elementsJFX.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Menu extends Application {

    ObservableList<Child> listChilds = FXCollections.observableArrayList();
    ObservableList<Mother> listMothers = FXCollections.observableArrayList();
    ObservableList<HospitalRoom> listHospitalRoom = FXCollections.observableArrayList();
    ObservableList<Doctor> listDoctors = FXCollections.observableArrayList();

    boolean flagFind = false;
    boolean flagFindDaughters = false;

    Doctor doctor = new Doctor();
    Child child = new Child();
    Mother mother = new Mother();
    HospitalRoom hospitalRoom = new HospitalRoom();

    DoctorDao doctorDao = new DoctorDao();
    ChildDao childDao = new ChildDao();
    MotherDao motherDao = new MotherDao();
    HospitalRoomDao hospitalRoomDao = new HospitalRoomDao();

    MyAlert numberFormat = new MyAlert(Alert.AlertType.ERROR,"Здесь могут содержаться только цифры");
    MyAlert vipFormat = new MyAlert(Alert.AlertType.ERROR,"Некорректно введен Vip-статус! (+/-)");


    public static void main(String[] args) {
        DoctorDao doctorDao = new DoctorDao();
        doctorDao.findAll();
        Application.launch(args);
    }

    public void reset(){
        this.flagFind = false;
        this.flagFindDaughters = false;
        this.doctor = null;
        this.child = null;
        this.mother = null;
        this.hospitalRoom = null;
        if (!listDoctors.isEmpty())
            listDoctors.clear();
        if (!listChilds.isEmpty())
            listChilds.clear();
        if (!listMothers.isEmpty())
            listMothers.clear();
        if (!listHospitalRoom.isEmpty())
            listHospitalRoom.clear();
        if (!listDoctors.isEmpty())
            listDoctors.clear();
    }

    @Override
    public void start(Stage stage) {
        MyLabel lbEnterTable = new MyLabel("Выберите таблицу для просмотра:",12,25,20);

        MyButton btnAdd = new MyButton("Добавить",12,120,25,330);
        MyButton btnFind = new MyButton("Поиск",12,120,170,330);
        MyButton btnDelete = new MyButton("Удалить",12,120,310,330);
        MyButton btnDeleteAll = new MyButton("Удалить всё",12,120,455,330);
        MyButton btnReset = new MyButton("Вернуть",12,120,465,20);

        MyButton btnDoctors = new MyButton("Врачи",12,120,170,370);
        MyButton btnMother = new MyButton("Мать",12,120,310,370);
        MyButton btnChilds = new MyButton("Дети",12,120,170,370);
        MyButton btnHospitalRoom = new MyButton("Палата",12,120,310,370);
        MyButton btnMothers = new MyButton("Матери",12,120,170,370);

        btnAdd.setDisable(true);
        btnDelete.setDisable(true);
        btnDeleteAll.setDisable(true);
        btnFind.setDisable(true);
        btnReset.setWork(false);

        btnDoctors.setWork(false);
        btnChilds.setWork(false);
        btnMother.setWork(false);
        btnMothers.setWork(false);
        btnHospitalRoom.setWork(false);


        TableView table = new TableView<>();
        table.setPrefWidth(600);
        table.setPrefHeight(270);
        table.setEditable(true);
        TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();

        ComboBox<String> langsComboBox = new ComboBox(FXCollections.observableArrayList("Врачи", "Дети", "Матери", "Палаты"));
        langsComboBox.setLayoutX(250);
        langsComboBox.setLayoutY(20);
        langsComboBox.setPrefHeight(20);
        langsComboBox.setPrefWidth(200);

        langsComboBox.setOnAction(e -> {

            table.getColumns().clear();
            btnFind.setDisable(false);
            btnDeleteAll.setDisable(false);

            //Настройка кнопок
            switch (langsComboBox.getValue()) {
                case "Врачи": {
                    btnMother.setWork(false);
                    btnMothers.setWork(false);
                    btnHospitalRoom.setWork(false);
                    btnDoctors.setWork(false);
                    btnChilds.setWork(true);

                    btnDelete.setDisable(true);
                    btnChilds.setDisable(true);

                    btnReset.setWork(flagFind);
                    btnAdd.setDisable(flagFind);

                    if (flagFindDaughters) {
                        btnReset.setWork(true);
                        btnAdd.setWork(true);
                        btnFind.setDisable(true);
                    }

                    btnDeleteAll.btn.setOnAction(event -> {
                        ActionDoctor.deleteAll(flagFind,flagFindDaughters,listDoctors,child);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Врачи");
                    });
                    btnAdd.btn.setOnAction(event -> {
                        ActionDoctor.add(stage,listDoctors,flagFindDaughters,child);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Врачи");
                    });
                    btnFind.btn.setOnAction(event -> {
                        flagFind = ActionDoctor.find(stage,listDoctors);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Врачи");
                    });
                    btnReset.btn.setOnAction(event -> {
                        reset();
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Врачи");
                    });
                    selectionModel.selectedItemProperty().addListener((ChangeListener<Doctor>)(exemplar, oldVal, newVal) -> {
                        btnDelete.setDisable(exemplar.getValue() == null);
                        btnChilds.setDisable(exemplar.getValue() == null);

                        btnDelete.btn.setOnAction(event -> {
                            ActionDoctor.delete(flagFind,flagFindDaughters,listDoctors,exemplar.getValue(),child);
                            langsComboBox.setValue("Дети");
                            langsComboBox.setValue("Врачи");

                        });
                        btnChilds.btn.setOnAction(event -> {
                            flagFindDaughters = true;
                            doctor = exemplar.getValue();
                            child = null;
                            mother = null;
                            hospitalRoom = null;
                            listChilds = FXCollections.observableArrayList(doctorDao.findChilds(doctor));
                            langsComboBox.setValue("Дети");
                        });
                    });
                    break;
                }
                case "Дети": {
                    btnMother.setWork(true);
                    btnMothers.setWork(false);
                    btnHospitalRoom.setWork(false);
                    btnDoctors.setWork(true);
                    btnChilds.setWork(false);

                    btnDelete.setDisable(true);
                    btnDoctors.setDisable(true);
                    btnMother.setDisable(true);

                    btnReset.setWork(flagFind);
                    btnAdd.setDisable(flagFind);

                    if (flagFindDaughters) {
                        btnReset.setWork(true);
                        btnAdd.setWork(true);
                        btnFind.setDisable(true);
                    }

                    btnDeleteAll.btn.setOnAction(event -> {
                        ActionChild.deleteAll(flagFind,flagFindDaughters,listChilds,doctor,mother);
                        langsComboBox.setValue("Матери");
                        langsComboBox.setValue("Дети");
                    });
                    btnAdd.btn.setOnAction(event -> {
                        ActionChild.add(stage,listChilds,flagFindDaughters,doctor,mother);
                        langsComboBox.setValue("Матери");
                        langsComboBox.setValue("Дети");
                    });
                    btnFind.btn.setOnAction(event -> {
                        flagFind = ActionChild.find(stage, listChilds);
                        langsComboBox.setValue("Матери");
                        langsComboBox.setValue("Дети");
                    });
                    btnReset.btn.setOnAction(event -> {
                        reset();
                        langsComboBox.setValue("Матери");
                        langsComboBox.setValue("Дети");
                    });
                    selectionModel.selectedItemProperty().addListener((ChangeListener<Child>)(exemplar, oldVal, newVal) -> {
                        btnDelete.setDisable(exemplar.getValue() == null);
                        btnDoctors.setDisable(exemplar.getValue() == null);
                        btnMother.setDisable(exemplar.getValue() == null);

                        btnDelete.btn.setOnAction(event -> {
                            ActionChild.delete(flagFind,flagFindDaughters,listChilds,exemplar.getValue(),doctor,mother);
                            langsComboBox.setValue("Матери");
                            langsComboBox.setValue("Дети");
                        });
                        btnDoctors.btn.setOnAction(event -> {
                            flagFindDaughters = true;
                            child = exemplar.getValue();
                            mother = null;
                            hospitalRoom = null;
                            doctor = null;
                            listDoctors = FXCollections.observableArrayList(childDao.findDoctors(child));
                            langsComboBox.setValue("Врачи");
                        });
                        btnMother.btn.setOnAction(event -> {
                            flagFindDaughters = true;
                            child = exemplar.getValue();
                            mother = null;
                            hospitalRoom = null;
                            doctor = null;
                            listMothers = FXCollections.observableArrayList(childDao.findMother(child));
                            langsComboBox.setValue("Матери");
                        });
                    });
                    break;
                }
                case "Матери": {
                    btnMother.setWork(false);
                    btnMothers.setWork(false);
                    btnHospitalRoom.setWork(true);
                    btnDoctors.setWork(false);
                    btnChilds.setWork(true);

                    btnDelete.setDisable(true);
                    btnHospitalRoom.setDisable(true);
                    btnChilds.setDisable(true);

                    btnReset.setWork(flagFind);
                    btnAdd.setDisable(flagFind);

                    if (flagFindDaughters) {
                        btnReset.setWork(true);
                        btnAdd.setWork(true);
                        btnFind.setDisable(true);
                    }

                    btnDeleteAll.btn.setOnAction(event -> {
                        ActionMother.deleteAll(flagFind,flagFindDaughters,listMothers,child,hospitalRoom);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Матери");
                    });
                    btnAdd.btn.setOnAction(event -> {
                        ActionMother.add(stage,listMothers,flagFindDaughters,child,hospitalRoom);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Матери");
                    });
                    btnFind.btn.setOnAction(event -> {
                        flagFind = ActionMother.find(stage,listMothers);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Матери");
                    });
                    btnReset.btn.setOnAction(event -> {
                        reset();
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Матери");
                    });
                    selectionModel.selectedItemProperty().addListener((ChangeListener<Mother>)(exemplar, oldVal, newVal) -> {
                        btnDelete.setDisable(exemplar.getValue() == null);
                        btnChilds.setDisable(exemplar.getValue() == null);
                        btnHospitalRoom.setDisable(exemplar.getValue() == null);

                        btnDelete.btn.setOnAction(event -> {
                            ActionMother.delete(flagFind,flagFindDaughters,listMothers,mother,child,hospitalRoom);
                            langsComboBox.setValue("Дети");
                            langsComboBox.setValue("Матери");
                        });
                        btnChilds.btn.setOnAction(event -> {
                            flagFindDaughters = true;
                            mother = exemplar.getValue();
                            child = null;
                            hospitalRoom = null;
                            doctor = null;
                            listChilds = FXCollections.observableArrayList(motherDao.findChilds(mother));
                            langsComboBox.setValue("Дети");
                        });
                        btnHospitalRoom.btn.setOnAction(event -> {
                            flagFindDaughters = true;
                            mother = exemplar.getValue();
                            child = null;
                            hospitalRoom = null;
                            doctor = null;
                            listHospitalRoom = FXCollections.observableArrayList(motherDao.findHospitalRoom(mother));
                            langsComboBox.setValue("Палаты");
                        });
                    });
                    break;
                }
                case "Палаты": {
                    btnMother.setWork(false);
                    btnMothers.setWork(true);
                    btnHospitalRoom.setWork(false);
                    btnDoctors.setWork(false);
                    btnChilds.setWork(false);

                    btnDelete.setDisable(true);
                    btnMothers.setDisable(true);

                    btnReset.setWork(flagFind);
                    btnAdd.setDisable(flagFind);

                    if (flagFindDaughters) {
                        btnReset.setWork(true);
                        btnAdd.setWork(true);
                        btnFind.setDisable(true);
                    }

                    btnDeleteAll.btn.setOnAction(event -> {
                        ActionHospitalRoom.deleteAll(flagFind,flagFindDaughters,listHospitalRoom,mother);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Палаты");
                    });
                    btnAdd.btn.setOnAction(event -> {
                        ActionHospitalRoom.add(stage,listHospitalRoom,flagFindDaughters,mother);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Палаты");
                    });
                    btnFind.btn.setOnAction(event -> {
                        flagFind =ActionHospitalRoom.find(stage,listHospitalRoom);
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Палаты");
                    });
                    btnReset.btn.setOnAction(event -> {
                        reset();
                        langsComboBox.setValue("Дети");
                        langsComboBox.setValue("Палаты");
                    });
                    selectionModel.selectedItemProperty().addListener((ChangeListener<HospitalRoom>)(exemplar, oldVal, newVal) -> {
                        btnDelete.setDisable(exemplar.getValue() == null);
                        btnMothers.setDisable(exemplar.getValue() == null);

                        btnDelete.btn.setOnAction(event -> {
                            ActionHospitalRoom.delete(flagFind,flagFindDaughters,listHospitalRoom,exemplar.getValue(),mother);
                            langsComboBox.setValue("Дети");
                            langsComboBox.setValue("Палаты");
                        });
                        btnMothers.btn.setOnAction(event -> {
                            flagFindDaughters = true;
                            hospitalRoom = exemplar.getValue();
                            child = null;
                            mother = null;
                            doctor = null;
                            listMothers = FXCollections.observableArrayList(hospitalRoomDao.findMothers(hospitalRoom));
                            langsComboBox.setValue("Матери");
                        });
                    });
                    break;
                }
            }

            //Настройка таблицы
            switch (langsComboBox.getValue()) {
                case "Врачи": {
                    MyTableColumn idCol = new MyTableColumn("Id","id",50,false);

                    MyTableColumn nameCol = new MyTableColumn("Имя","name",200,true);
                    nameCol.tableColumn.setOnEditCommit(event -> {
                        Doctor doctor = (Doctor)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        System.out.println(doctor);
                        doctorDao.updateName(doctor,event.getNewValue());
                    });

                    MyTableColumn specialityCol = new MyTableColumn("Специальность","speciality",200,true);
                    specialityCol.tableColumn.setOnEditCommit(event -> {
                        Doctor doctor = (Doctor)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        System.out.println(doctor);
                        doctorDao.updateSpeciality(doctor,event.getNewValue());
                    });

                    MyTableColumn birthdayCol = new MyTableColumn("День рождения","birthday",100,true);
                    birthdayCol.tableColumn.setOnEditCommit(event -> {
                        Doctor doctor = (Doctor)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        if (ActionUtils.checkDate(event.getNewValue())!=null)
                            doctorDao.updateBirthday(doctor,ActionUtils.checkDate(event.getNewValue()));
                    });

                    table.getColumns().addAll(idCol.getTc(), nameCol.getTc(), specialityCol.getTc(), birthdayCol.getTc());
                    if(flagFind||flagFindDaughters)
                        table.setItems(listDoctors);
                    else {
                        table.setItems(FXCollections.observableArrayList(doctorDao.findAll()));
                    }
                    break;
                }
                case "Дети": {
                    MyTableColumn idCol = new MyTableColumn("Id","id",50,false);

                    MyTableColumn nameCol = new MyTableColumn("Имя","name",225,true);
                    nameCol.tableColumn.setOnEditCommit(event -> {
                        Child child = (Child)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        childDao.updateName(child,event.getNewValue());
                    });

                    MyTableColumn birthdayCol = new MyTableColumn("День рождения","birthday",100,true);
                    birthdayCol.tableColumn.setOnEditCommit(event -> {
                        Child child = (Child)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        if (ActionUtils.checkDate(event.getNewValue())!=null)
                            childDao.updateBirthday(child,ActionUtils.checkDate(event.getNewValue()));
                    });

                    table.getColumns().addAll(idCol.getTc(), nameCol.getTc(), birthdayCol.getTc());
                    if(flagFind||flagFindDaughters)
                        table.setItems(listChilds);
                    else
                        table.setItems(FXCollections.observableArrayList(childDao.findAll()));
                    break;
                }
                case "Матери": {
                    MyTableColumn idCol = new MyTableColumn("Id","id",50,false);

                    MyTableColumn nameCol = new MyTableColumn("Имя","name",225,true);
                    nameCol.tableColumn.setOnEditCommit(event -> {
                        Mother mother = (Mother)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        motherDao.updateName(mother,event.getNewValue());
                    });

                    MyTableColumn birthdayCol = new MyTableColumn("День рождения","birthday",100,true);
                    birthdayCol.tableColumn.setOnEditCommit(event -> {
                        Mother mother = (Mother)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        if (ActionUtils.checkDate(event.getNewValue())!=null)
                            motherDao.updateBirthday(mother,ActionUtils.checkDate(event.getNewValue()));
                    });

                    table.getColumns().addAll(idCol.getTc(), nameCol.getTc(), birthdayCol.getTc());
                    if(flagFind||flagFindDaughters)
                        table.setItems(FXCollections.observableArrayList(listMothers));
                    else
                        table.setItems(FXCollections.observableArrayList(motherDao.findAll()));
                    break;
                }
                case "Палаты": {
                    MyTableColumn idCol = new MyTableColumn("Id","id",50,false);

                    MyTableColumn numberCol = new MyTableColumn("Номер палаты","number",100,true);
                    numberCol.tableColumn.setOnEditCommit(event -> {
                        HospitalRoom hospitalRoom = (HospitalRoom) event.getTableView().getItems().get(event.getTablePosition().getRow());
                        try {
                            Integer.parseInt(event.getNewValue());
                            hospitalRoomDao.updateNumber(hospitalRoom, event.getNewValue());
                        } catch (NumberFormatException exception) {
                            numberFormat.showAndWait();
                        }
                    });

                    MyTableColumn vipCol = new MyTableColumn("VIP-статус","vip",100,true);
                    vipCol.tableColumn.setOnEditCommit(event -> {
                        HospitalRoom hospitalRoom = (HospitalRoom)event.getTableView().getItems().get(event.getTablePosition().getRow());
                            if (event.getNewValue().equals("+") || (event.getNewValue().equals("-"))) {
                                hospitalRoomDao.updateVip(hospitalRoom, event.getNewValue());
                            }
                            else
                                vipFormat.showAndWait();
                    });

                    MyTableColumn numberOfPlacesCol = new MyTableColumn("Число мест","numberOfPlaces",100,true);
                    numberOfPlacesCol.tableColumn.setOnEditCommit(event -> {
                        HospitalRoom hospitalRoom = (HospitalRoom)event.getTableView().getItems().get(event.getTablePosition().getRow());
                        try {
                            Integer.parseInt(event.getNewValue());
                            hospitalRoomDao.updateNumberOfPlace(hospitalRoom, event.getNewValue());
                        } catch (NumberFormatException exception) {
                            numberFormat.showAndWait();
                        }
                    });

                    table.getColumns().addAll(idCol.getTc(), numberCol.getTc(), vipCol.getTc(), numberOfPlacesCol.getTc());
                    if(flagFind||flagFindDaughters)
                        table.setItems(FXCollections.observableArrayList(listHospitalRoom));
                    else
                        table.setItems(FXCollections.observableArrayList(hospitalRoomDao.findAll()));
                    break;
                }
                default:
                    table.getColumns().clear();
                    break;
            }
        });

        VBox vbox = new VBox(table);
        vbox.setPrefHeight(270);
        vbox.setPrefWidth(600);
        vbox.setLayoutX(0);
        vbox.setLayoutY(50);
        vbox.setPadding(new Insets(5, 10, 0, 10));

        Pane pane = new Pane(lbEnterTable.getLb(),langsComboBox,btnAdd.getBtn(),btnDelete.getBtn(),btnFind.getBtn(),btnReset.getBtn(),
                btnDeleteAll.getBtn(),btnChilds.getBtn(),btnDoctors.getBtn(),btnHospitalRoom.getBtn(),btnMother.getBtn(), btnMothers.getBtn());

        Pane root = new Pane(pane, vbox);

        Scene scene = new Scene(root, 600, 420, Color.TRANSPARENT);

        stage.setTitle("База данных родильного дома");
        MyStage stg = new MyStage(stage,scene);
        stg.show();
    }
}