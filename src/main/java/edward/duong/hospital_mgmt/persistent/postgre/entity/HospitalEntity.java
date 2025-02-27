package edward.duong.hospital_mgmt.persistent.postgre.entity;

import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "hospital")
public class HospitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String name;
    private String address;
    private String telephone;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HospitalSpecialistEntity> specialists;

    public void addSpecialists(List<Specialist> specialists) {
        if (Objects.isNull(this.specialists)) {
            this.specialists = new ArrayList<>();
        }

        this.specialists.clear();
        if (Objects.nonNull(specialists)) {
            for (Specialist spec : specialists) {
                HospitalSpecialistEntity entity = new HospitalSpecialistEntity();
                entity.setHospital(this);

                SpecialistEntity specialistEntity = new SpecialistEntity();
                specialistEntity.setId(Long.valueOf(spec.getId()));

                entity.setSpecialist(specialistEntity);
                this.specialists.add(entity);
            }
        }
    }
}
