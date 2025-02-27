package edward.duong.hospital_mgmt.persistent.postgre.specification;

import edward.duong.hospital_mgmt.domain.models.spec.SpecialistCriteria;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialistEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class SpecialistSpecification {
    public static Specification<SpecialistEntity> filterByCriteria(SpecialistCriteria criteria) {
        return (Root<SpecialistEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(criteria.getId())) {
                predicates.add(cb.equal(root.get("id"), criteria.getId()));
            }
            if (Objects.nonNull(criteria.getName())) {
                predicates.add(cb.equal(root.get("name"), criteria.getName()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
