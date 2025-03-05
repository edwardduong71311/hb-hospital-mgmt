package edward.duong.hospital_mgmt.persistent.postgre.entity;

import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "disease")
@EntityListeners(AuditingEntityListener.class)
public class DiseaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;
    private String symptom;
    private String treatment;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiseaseSpecialtyEntity> specialties;

    public void addSpecialties(List<Specialty> specialties) {
        if (Objects.isNull(this.specialties)) {
            this.specialties = new ArrayList<>();
        }

        this.specialties.clear();
        if (Objects.nonNull(specialties)) {
            for (Specialty specialty : specialties) {
                DiseaseSpecialtyEntity entity = new DiseaseSpecialtyEntity();
                entity.setDisease(this);

                SpecialtyEntity specialtyEntity = new SpecialtyEntity();
                specialtyEntity.setId(Long.valueOf(specialty.getId()));
                entity.setSpecialty(specialtyEntity);

                this.specialties.add(entity);
            }
        }
    }
}
