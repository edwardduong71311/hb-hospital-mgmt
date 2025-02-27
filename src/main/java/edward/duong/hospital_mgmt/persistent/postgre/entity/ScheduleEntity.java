package edward.duong.hospital_mgmt.persistent.postgre.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private LocalTime fromTime;
    private LocalTime toTime;

    @ManyToOne
    @JoinColumn(name = "hospital")
    private HospitalEntity hospital;

    @ManyToOne
    @JoinColumn(name = "specialist")
    private SpecialistEntity specialist;

    @ManyToOne
    @JoinColumn(name = "appointment")
    private AppointmentEntity appointment;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
