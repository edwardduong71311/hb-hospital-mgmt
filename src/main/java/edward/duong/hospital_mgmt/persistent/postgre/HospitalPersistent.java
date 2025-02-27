package edward.duong.hospital_mgmt.persistent.postgre;

import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.hospital.Hospital;
import edward.duong.hospital_mgmt.domain.models.hospital.HospitalCriteria;
import edward.duong.hospital_mgmt.persistent.mapper.HospitalMapper;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
import edward.duong.hospital_mgmt.persistent.postgre.repo.PostgresHospitalRepo;
import edward.duong.hospital_mgmt.persistent.postgre.specification.HospitalSpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class HospitalPersistent implements edward.duong.hospital_mgmt.domain.output_ports.HospitalPersistent {
    private final PostgresHospitalRepo repo;

    public HospitalPersistent(PostgresHospitalRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Hospital> getHospitals(Pagination pagination) {
        Page<HospitalEntity> hospitals =
                repo.findAll(Pageable.ofSize(pagination.getSize()).withPage(pagination.getPage()));
        return HospitalMapper.INSTANCE.entityToModels(hospitals.getContent());
    }

    @Override
    public List<Hospital> getHospitalsByCriteria(HospitalCriteria criteria, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        Specification<HospitalEntity> spec = HospitalSpecification.filterByCriteria(criteria);
        return HospitalMapper.INSTANCE.entityToModels(
                repo.findAll(spec, pageable).get().toList());
    }

    @Override
    public Hospital getHospitalByCriteria(HospitalCriteria criteria) {
        Specification<HospitalEntity> spec = HospitalSpecification.filterByCriteria(criteria);
        return HospitalMapper.INSTANCE.toModel(repo.findOne(spec).orElse(null));
    }

    @Override
    public Hospital createHospital(Hospital hospital) {
        hospital.setId(null);

        HospitalEntity toSave = HospitalMapper.INSTANCE.toSaveEntity(hospital);
        toSave.addSpecialists(hospital.getSpecialists());

        HospitalEntity saved = repo.save(toSave);
        return HospitalMapper.INSTANCE.toModel(saved);
    }

    @Override
    public Hospital updateHospital(Hospital hospital) {
        HospitalEntity toUpdate = HospitalMapper.INSTANCE.toUpdateEntity(hospital);
        toUpdate.addSpecialists(hospital.getSpecialists());

        HospitalEntity saved = repo.save(toUpdate);
        return HospitalMapper.INSTANCE.toModel(saved);
    }
}
