package dao;

import classEntity.HospitalRoom;
import classEntity.Mother;
import org.hibernate.Session;

import java.util.List;


public class HospitalRoomDao extends BasisDao<Integer, HospitalRoom> {

    private final HospitalRoom hospitalRoom = new HospitalRoom();
    MotherDao motherDao;


    public HospitalRoom findById(Integer id) {
        return findByIdExemplar(id,hospitalRoom);
    }

    public List<HospitalRoom> findByNumber(String number) {
        return findByAttribute(hospitalRoom,number,"number");
    }

    public List<HospitalRoom> findByVip(String vip) {
        return findByAttribute(hospitalRoom,vip,"vip");
    }

    public List<HospitalRoom> findByNumberOfPlace(String numberOfPlace) {
        return findByAttribute(hospitalRoom,numberOfPlace,"numberOfPlaces");
    }

    public List<HospitalRoom> findAll() throws NullPointerException {
        return findAllExemplar(hospitalRoom);
    }

    //находит всех Mothers данного экземпляра
    public List<Mother> findMothers(HospitalRoom hospitalRoom) {
        Session session = getSession();
        int id = hospitalRoom.getId();
        @SuppressWarnings("unchecked")
        List<Mother> list = (List<Mother>)session.createQuery("SELECT m " +
                "FROM HospitalRoom h " +
                "Join h.mothers m " +
                "WHERE h.id="+id).list();
        session.close();
        return list;
    }

    public void add(HospitalRoom hospitalRoom) {
        addExemplar(hospitalRoom);
    }

    public void updateNumber(HospitalRoom hospitalRoom, String number) {
        hospitalRoom.setNumber(number);
        updateExemplar(hospitalRoom);
    }

    public void updateVip(HospitalRoom hospitalRoom, String vip){
        hospitalRoom.setVip(vip);
        updateExemplar(hospitalRoom);
    }

    public void updateNumberOfPlace(HospitalRoom hospitalRoom, String numberOfPlace){
        hospitalRoom.setNumberOfPlaces(numberOfPlace);
        updateExemplar(hospitalRoom);
    }

    public void updateMothers(HospitalRoom hospitalRoom, List<Mother> mothers){
        hospitalRoom.setMothers(mothers);
        updateExemplar(hospitalRoom);
    }

    public void delete(HospitalRoom hospitalRoom) {
        deleteExemplar(hospitalRoom);
    }

    public void deleteAll() {
        deleteAllExemplars(hospitalRoom);
    }
}