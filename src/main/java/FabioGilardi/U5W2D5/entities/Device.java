package FabioGilardi.U5W2D5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Device {

    //    ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String type, status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(String type, String status, Employee employee) {
        this.type = type;
        this.status = status;
        this.employee = employee;
    }
}
