package classEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "hospital_room")
public class HospitalRoom {

    @Id
    @SequenceGenerator( name = "hospital_room_id_seq", sequenceName = "hospital_room_id_seq", allocationSize = 1, initialValue = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "hospital_room_id_seq")
    private int id;
    private String number;
    private String vip;
    @Column(name = "number_of_places")
    private String numberOfPlaces;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hospital_room")
    private List<Mother> mothers;

    public List<Mother> getMothers() {
        return mothers;
    }

    public void setMothers(List<Mother> mothers) {
        this.mothers = mothers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(String numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    /*public void setVipStr(String vipStr) {
        this.vipStr = vipStr;
        if (vipStr.equals("+"))
            this.vip= true;
        else
            if (vipStr.equals("-"))
                this.vip = false;
            else
                System.out.println("error");
            //исключение
    }*/

    public HospitalRoom(String number, String vip, String numberOfPlaces) {
        this.number = number;
        this.numberOfPlaces = numberOfPlaces;
        this.vip = vip;
    }

    public HospitalRoom(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HospitalRoom)) return false;
        HospitalRoom hospitalRoom = (HospitalRoom) o;
        return Objects.equals(getId(), hospitalRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, numberOfPlaces, vip);
    }

    @Override
    public String toString() {
        return "HospitalRoom{" +
                "Id=" + id +
                ", Number='" + number + '\'' +
                ", Number of places=" + numberOfPlaces +
                ", VIP=" + vip +
                '}';
    }
}
