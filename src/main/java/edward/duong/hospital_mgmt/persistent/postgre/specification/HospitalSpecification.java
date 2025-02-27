package edward.duong.hospital_mgmt.persistent.postgre.specification;

import edward.duong.hospital_mgmt.domain.models.hospital.HospitalCriteria;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class HospitalSpecification {
    public static Specification<HospitalEntity> filterByCriteria(HospitalCriteria criteria) {
        return (Root<HospitalEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(criteria.getId())) {
                predicates.add(cb.equal(root.get("id"), criteria.getId()));
            }
            if (Objects.nonNull(criteria.getName())) {
                predicates.add(cb.equal(root.get("name"), criteria.getName()));
            }
            if (Objects.nonNull(criteria.getLocation())) {
                predicates.add(cb.isTrue(cb.function(
                        "ST_DWithin",
                        Boolean.class,
                        root.get("location"),
                        cb.function(
                                "ST_SetSRID",
                                Object.class,
                                cb.function(
                                        "ST_MakePoint",
                                        Object.class,
                                        cb.literal(criteria.getLocation().getLongitude()),
                                        cb.literal(criteria.getLocation().getLatitude())),
                                cb.literal(4326)),
                        cb.literal(criteria.getRadius() * 1000) // Convert km to meters
                        )));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
