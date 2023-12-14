package hu.bankblaze.bankblaze.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teller")
public class Teller {

    @Id
    private Long id;
    private String name;
}
