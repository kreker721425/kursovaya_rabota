package dao;

import classEntity.Child;
import classEntity.Doctor;
import org.hibernate.Session;

import java.util.List;


public class DoctorDao extends BasisDao<Integer, Doctor>{

    private final Doctor doctor = new Doctor();
    ChildDao childDao;


    public Doctor findById(Integer id) {
        return findByIdExemplar(id,doctor);
    }

    public List<Doctor> findByName(String name) {
        return findByAttribute(doctor,name,"name");
    }

    public List<Doctor> findBySpeciality(String speciality) {
        return findByAttribute(doctor,speciality,"speciality");
    }

    public List<Doctor> findByBirthday(String date) {
        return findByAttribute(doctor,date,"birthday");
    }

    public List<Doctor> findAll() throws NullPointerException {
        return findAllExemplar(doctor);
    }

    //находит всех Childs данного экземпляра
    public List<Child> findChilds(Doctor doctor) {
        Session session =getSession();
        int id = doctor.getId();
        @SuppressWarnings("unchecked")
        List<Child> list = (List<Child>)session.createQuery("SELECT c " +
                "FROM Doctor d " +
                "Join d.childs c " +
                "WHERE d.id="+id).list();
        session.close();
        return list;
    }

    public void add(Doctor doctor) {
        addExemplar(doctor);
    }

    public void updateName(Doctor doctor, String name) {
        doctor.setName(name);
        updateExemplar(doctor);
    }

    public void updateSpeciality(Doctor doctor, String speciality){
        doctor.setSpeciality(speciality);
        updateExemplar(doctor);
    }

    public void updateBirthday(Doctor doctor, String date){
        doctor.setBirthday(date);
        updateExemplar(doctor);
    }

    public void updateChilds(Doctor doctor, List<Child> childs){
        doctor.setChilds(childs);
        updateExemplar(doctor);
    }

    public void delete(Doctor doctor) {
        deleteExemplar(doctor);
    }

    public void deleteAll() {
        deleteAllExemplars(doctor);
    }
}
