package classEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Embeddable
@Table(name = "doctor")
public class Doctor {

    @Id
    @SequenceGenerator( name = "doctor_id_seq", sequenceName = "doctor_id_seq", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "doctor_id_seq")
    private Integer id;

    private String name;
    private String speciality;
    private String birthday;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_child",
            joinColumns = { @JoinColumn(name = "id_doctor") },
            inverseJoinColumns = { @JoinColumn(name = "id_child") })
    private List<Child> childs;


    public List<Child> getChilds() {
        return childs;
    }

    public void setChilds(List<Child> childs) {
        this.childs = childs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /*public String getBirthdayStr() {
        return DoctorDao.birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthday = Utils.newDate(birthdayStr);
    }*/


    public Doctor(String name, String speciality, String birthday) {
        this.name = name;
        this.speciality = speciality;
        this.birthday = birthday;
    }

    public Doctor() {
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "Id = " + id +
                ", Name = '" + name + '\'' +
                ", Speciality = '" + speciality + '\'' +
                ", Birthday = " + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(getId(), doctor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, speciality, birthday);
    }

}