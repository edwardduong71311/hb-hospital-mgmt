package edward.duong.hospital_mgmt.persistent.postgre.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

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
    private String telephone;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "hospital_specialist",
            joinColumns = @JoinColumn(name = "hospital"),
            inverseJoinColumns = @JoinColumn(name = "specialist")
    )
    private List<SpecialtyEntity> specialties;
}
