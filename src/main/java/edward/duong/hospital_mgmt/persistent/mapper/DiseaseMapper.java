package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.spec.Disease;
import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import edward.duong.hospital_mgmt.persistent.postgre.entity.DiseaseEntity;
import edward.duong.hospital_mgmt.persistent.postgre.entity.DiseaseSpecialtyEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiseaseMapper {
    DiseaseMapper INSTANCE = Mappers.getMapper(DiseaseMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "symptom", target = "symptom")
    @Mapping(source = "treatment", target = "treatment")
    @Mapping(source = "specialties", target = "specialties")
    Disease toModel(DiseaseEntity a);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "symptom", target = "symptom")
    @Mapping(source = "treatment", target = "treatment")
    DiseaseEntity toSaveEntity(Disease a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "symptom", target = "symptom")
    @Mapping(source = "treatment", target = "treatment")
    DiseaseEntity toUpdateEntity(Disease a);

    @Mapping(source = "specialty.id", target = "id")
    @Mapping(source = "specialty.name", target = "name")
    @Mapping(source = "specialty.status", target = "status")
    @Mapping(source = "specialty.description", target = "description")
    Specialty toModel(DiseaseSpecialtyEntity a);

    List<Disease> entityToModels(List<DiseaseEntity> a);
}
