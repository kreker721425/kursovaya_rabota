package classEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mother")
public class Mother {

    @Id
    @SequenceGenerator( name = "mother_id_seq", sequenceName = "mother_id_seq", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "mother_id_seq")
    private Integer id;
    private String name;
    private String birthday;

    @OneToMany(fetch = FetchType.LAZY/*, mappedBy = "mother", cascade = CascadeType.ALL, orphanRemoval = true*/)
    @JoinColumn(name = "id_mother")
    private List<Child> childs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hospital_room")
    private HospitalRoom hospitalRoom;

    public List<Child> getChilds() {
        return childs;
    }

    public void setChilds(List<Child> childs) {
        this.childs = childs;
    }

    public HospitalRoom getHospitalRoom() {
        return hospitalRoom;
    }

    public void setHospitalRoom(HospitalRoom hospitalRoom) {
        this.hospitalRoom = hospitalRoom;
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

    public Mother(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public Mother(){
    }

    @Override
    public String toString() {
        return "Mother{" +
                "Id = " + id +
                ", Name = '" + name + '\'' +
                ", Birthday = " + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mother)) return false;
        Mother mother = (Mother) o;
        return Objects.equals(getId(), mother.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday, childs, hospitalRoom);
    }

}