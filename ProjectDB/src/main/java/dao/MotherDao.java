package dao;

import classEntity.Child;
import classEntity.HospitalRoom;
import classEntity.Mother;
import org.hibernate.Session;

import java.util.List;

public class MotherDao extends BasisDao<Integer, Mother> {

    private final Mother mother = new Mother();
    ChildDao childDao;
    HospitalRoomDao hospitalRoomDao;


    public Mother findById(Integer id) {
        return findByIdExemplar(id,mother);
    }

    public List<Mother> findByName(String name) {
        return findByAttribute(mother,name,"name");
    }

    public List<Mother> findByBirthday(String date) {
        return findByAttribute(mother,date,"birthday");
    }

    public List<Mother> findAll() throws NullPointerException {
        return findAllExemplar(mother);
    }

    //находит всех Childs данного экземпляра
    public List<Child> findChilds(Mother mother) {
        Session session =getSession();
        int id = mother.getId();
        @SuppressWarnings("unchecked")
        List<Child> list = (List<Child>)session.createQuery("SELECT c " +
                "FROM Mother m " +
                "Join m.childs c " +
                "WHERE m.id="+id).list();
        session.close();
        return list;
    }

    //находит HospitalRoom данного экземпляра
    public HospitalRoom findHospitalRoom(Mother mother) {
        Session session =getSession();
        int id = mother.getId();
        @SuppressWarnings("unchecked")
        List<HospitalRoom> list = (List<HospitalRoom>)session.createQuery("SELECT h " +
                "FROM Mother m " +
                "Join m.hospitalRoom h " +
                "WHERE m.id="+id).list();
        session.close();
        if (list.isEmpty())
            return null;
        return list.get(0);
    }

    public void add(Mother mother) {
        addExemplar(mother);
    }

    public void updateName(Mother mother, String name) {
        mother.setName(name);
        updateExemplar(mother);
    }

    public void updateBirthday(Mother mother, String date){
        mother.setBirthday(date);
        updateExemplar(mother);
    }

    public void updateChilds(Mother mother, List<Child> childs){
        mother.setChilds(childs);
        updateExemplar(mother);
    }

    public void updateHospitalRoom(Mother mother, HospitalRoom hospitalRoom){
        mother.setHospitalRoom(hospitalRoom);
        updateExemplar(mother);
    }

    public void delete(Mother mother) {
        deleteCascade(mother);
    }

    public void deleteCascade(Mother mother){
        for (Child child : findChilds(mother))
            childDao.delete(child);
        deleteExemplar(mother);
    }

    public void deleteAll() {
        for (Mother mother : findAll())
            deleteCascade(mother);
    }
}