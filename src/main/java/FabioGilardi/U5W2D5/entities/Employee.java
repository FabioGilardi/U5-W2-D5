package FabioGilardi.U5W2D5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    //    ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String username, name, surname, email, avatar;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Device> deviceList;

    public Employee(String username, String name, String surname, String email, List<Device> deviceList) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        setDeafaultAvatar();
        this.deviceList = deviceList;
    }

    public void setDeafaultAvatar() {
        this.avatar = "https://ui-avatars.com/api/?name=" + this.name + "+" + this.surname;
    }
}
