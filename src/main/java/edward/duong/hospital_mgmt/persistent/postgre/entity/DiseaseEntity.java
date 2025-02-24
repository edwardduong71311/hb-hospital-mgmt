package edward.duong.hospital_mgmt.persistent.postgre.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "disease")
public class DiseaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String status;
    private String symptom;
    private String treatment;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "disease_specialty",
            joinColumns = @JoinColumn(name = "disease"),
            inverseJoinColumns = @JoinColumn(name = "specialty")
    )
    private List<SpecialtyEntity> specialties;
}
