package edward.duong.hospital_mgmt.persistent.postgre.specification;

import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyCriteria;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialtyEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public class SpecialtySpecification {
    public static Specification<SpecialtyEntity> filterByCriteria(SpecialtyCriteria criteria) {
        return (Root<SpecialtyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(criteria.getId())) {
                predicates.add(cb.equal(root.get("id"), criteria.getId()));
            }
            if (Objects.nonNull(criteria.getName())) {
                predicates.add(cb.like(root.get("name"), criteria.getName()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
