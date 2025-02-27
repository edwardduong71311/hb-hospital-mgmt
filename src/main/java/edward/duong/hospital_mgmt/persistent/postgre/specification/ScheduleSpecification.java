package edward.duong.hospital_mgmt.persistent.postgre.specification;

import edward.duong.hospital_mgmt.domain.models.hospital.ScheduleCriteria;
import edward.duong.hospital_mgmt.persistent.postgre.entity.ScheduleEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class ScheduleSpecification {
    public static Specification<ScheduleEntity> filterByCriteria(ScheduleCriteria criteria) {
        return (Root<ScheduleEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (Objects.nonNull(criteria.getId())) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), criteria.getId()));
            }
            if (Objects.nonNull(criteria.getHospitalId())) {
                predicate = cb.and(predicate, cb.equal(root.get("hospital"), criteria.getHospitalId()));
            }
            if (Objects.nonNull(criteria.getDate())) {
                predicate = cb.and(predicate, cb.equal(root.get("date"), criteria.getDate()));
            }

            return predicate;
        };
    }
}
