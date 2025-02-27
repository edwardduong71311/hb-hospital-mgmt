package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import edward.duong.hospital_mgmt.domain.models.spec.Specialty;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialistEntity;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialistSpecialtyEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpecialistMapper {
    SpecialistMapper INSTANCE = Mappers.getMapper(SpecialistMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "specialties", target = "specialties")
    Specialist toModel(SpecialistEntity a);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    SpecialistEntity toSaveEntity(Specialist a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    SpecialistEntity toUpdateEntity(Specialist a);

    @Mapping(source = "specialty.id", target = "id")
    @Mapping(source = "specialty.name", target = "name")
    @Mapping(source = "specialty.status", target = "status")
    @Mapping(source = "specialty.description", target = "description")
    Specialty toModel(SpecialistSpecialtyEntity a);

    List<Specialist> entityToModels(List<SpecialistEntity> a);
}
