package dao;

import classEntity.Child;
import classEntity.Doctor;
import classEntity.Mother;
import org.hibernate.Session;

import java.util.List;

public class ChildDao extends BasisDao<Integer, Child>{

    private final Child child = new Child();

    DoctorDao doctorDao;
    MotherDao motherDao;


    public Child findById(Integer id) {
        return findByIdExemplar(id,child);
    }

    public List<Child> findByName(String name) {
        return findByAttribute(child,name,"name");
    }

    public List<Child> findByBirthday(String date) {
        return findByAttribute(child,date,"birthday");
    }

    public List<Child> findAll() throws NullPointerException {
        return findAllExemplar(child);
    }

    //находит всех Doctors данного экземпляра
    public List<Doctor> findDoctors(Child child) {
        Session session = getSession();
        int id = child.getId();
        @SuppressWarnings("unchecked")
        List<Doctor> list = (List<Doctor>)session.createQuery("SELECT d " +
                "FROM Child c " +
                "Join c.doctors d " +
                "WHERE c.id="+id).list();
        session.close();
        return list;
    }

    //находит Mother данного экземпляра
    public Mother findMother(Child child) {
        Session session = getSession();
        int id = child.getId();
        @SuppressWarnings("unchecked")
        List<Mother> list = (List<Mother>)session.createQuery("SELECT m " +
                "FROM Child c " +
                "Join c.mother m " +
                "WHERE c.id="+id).list();
        session.close();
        if (list.isEmpty())
            return null;
        return list.get(0);
    }

    public void add(Child child) {
        addExemplar(child);
    }

    public void updateName(Child child, String name) {
        child.setName(name);
        updateExemplar(child);
    }

    public void updateBirthday(Child child, String date){
        child.setBirthday(date);
        updateExemplar(child);
    }

    public void updateDoctors(Child child, List<Doctor> doctors){
        child.setDoctors(doctors);
        updateExemplar(child);
    }

    public void updateMother(Child child, Mother mother){
        child.setMother(mother);
        updateExemplar(child);
    }

    public void delete(Child child) {
        deleteCascade(child);
    }

    public void deleteCascade(Child child){
        Mother mother = findMother(child);
        if ((mother != null) && (motherDao.findChilds(mother).size() == 1))
            motherDao.delete(mother);
        deleteExemplar(child);
    }

    public void deleteAll() {
        for (Child child : findAll())
            deleteCascade(child);
    }
}
