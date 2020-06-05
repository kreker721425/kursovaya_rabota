package dao;

import classEntity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import java.io.Serializable;
import java.util.List;

public abstract class BasisDao<PK extends Serializable, T> {

    protected Session getSession() throws NullPointerException{
        return new AnnotationConfiguration().configure().buildSessionFactory().openSession();
    }

    //поиск по id
    public T findByIdExemplar(Integer id, T entity) {
        Session session = getSession();
        String strEntity = findEntity(entity);
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) session.createQuery("SELECT c FROM "+strEntity+" c WHERE c.id="+id.toString()+"").list();
        session.close();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    //поиск по атрибуту
    public List<T> findByAttribute(T entity, String name, String attribute) {
        Session session = getSession();
        String str = findEntity(entity);
        System.out.println(str);
        @SuppressWarnings("unchecked")
        List<T> list = session.createQuery(
                "SELECT s FROM "+str+" s where "+attribute+" = '"+name+"'").list();
        session.close();
        return list;
    }

    //поиск всех экземпляров таблицы
    public List<T> findAllExemplar(T entity) {
        Session session = getSession();
        String str = findEntity(entity);
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) session.createQuery("select s from "+str+" s").list();
        session.close();
        return list;
    }

    //добавить экземпляр
    public void addExemplar(T entity) {
        Session session = getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }

    //обновить екземпляр
    public void updateExemplar(T entity){
        Session session = getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    //удалить все экземпляры
    public void deleteAllExemplars(T entity) {
        String entityString = findEntity(entity);
        Session session = getSession();
        Transaction tx1 = session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<T> list = session.createQuery("select s from "+entityString+" s").list();

        for(T s : list)
            session.delete(s);
        tx1.commit();
        session.close();
    }

    //удалить выбранный экземпляр
    public void deleteExemplar(T entity) {
        findExemplar(entity);
        Session session = getSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(entity);
        tx1.commit();
        session.close();
    }

    //поиск названия сущности
    public String findEntity(T entity){
        Doctor doctor= new Doctor();
        Child child = new Child();
        Mother mother = new Mother();
        HospitalRoom hospitalRoom = new HospitalRoom();
        if (doctor.getClass()==entity.getClass())
            return "Doctor";
        else
        if (child.getClass()==entity.getClass())
            return "Child";
        else
        if (mother.getClass()==entity.getClass())
            return "Mother";
        else
        if (hospitalRoom.getClass()==entity.getClass())
            return "HospitalRoom";
        else
            return null;
    }

    //поиск экземпляра, на предмет наличия
    public boolean findExemplar(T entity){
        Session session = getSession();
        String str = findEntity(entity);
        boolean flag=false;
        for (T s: (List<T>) session.createQuery("select s from "+ str +" s").list())
            if (entity.equals(s)) {
                flag = true;
                break;
            }
        session.close();
        if (!flag) {
            System.out.println("Not found "+str);
            return false;
        }
        else return true;
    }
}
