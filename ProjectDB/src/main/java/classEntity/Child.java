package classEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "child")
public class Child {

    @Id
    @SequenceGenerator( name = "child_id_seq", sequenceName = "child_id_seq", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "child_id_seq")
    private Integer id;
    private String name;
    private String birthday;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mother")
    private Mother mother;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_child",
            joinColumns = { @JoinColumn(name = "id_child") },
            inverseJoinColumns = { @JoinColumn(name = "id_doctor") })
    private List<Doctor> doctors;


    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Mother getMother() {
        return mother;
    }

    public void setMother(Mother mother) {
        this.mother = mother;
    }

    public Child(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public Child(){
    }


    @Override
    public String toString() {
        return "Child{" +
                "Id = " + id +
                ", Name = '" + name + '\'' +
                ", Birthday = " + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child)) return false;
        Child child = (Child) o;
        return Objects.equals(getId(), child.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday, mother, doctors);
    }

}