package model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {

    private String employeeId;
    private String medCertificate;
    private LocalDate employmentDate;
    private boolean isAdmin;

    // Removed the worksAtWarehouse field and its annotations
    // Removed the relationship between Manager and Warehouse

    public Manager(String login, String password, LocalDate birthDate, String name, String surname, String employeeId, String medCertificate, LocalDate employmentDate, boolean isAdmin) {
        super(login, password, birthDate, name, surname);
        this.employeeId = employeeId;
        this.medCertificate = medCertificate;
        this.employmentDate = employmentDate;
        this.isAdmin = isAdmin;
    }

    public Manager(String login, String password, LocalDate birthDate) {
        // Assuming there's a purpose for this constructor, it remains unchanged
    }

    @Override
    public String toString() {
        return "Manager{" +
                "employeeId='" + employeeId + '\'' +
                ", medCertificate='" + medCertificate + '\'' +
                ", employmentDate=" + employmentDate +
                ", isAdmin=" + isAdmin +
                ", name='" + name + '\'' +
                '}';
    }
}
