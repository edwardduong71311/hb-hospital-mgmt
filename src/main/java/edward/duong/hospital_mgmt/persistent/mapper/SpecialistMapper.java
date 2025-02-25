package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.Specialist;
import edward.duong.hospital_mgmt.persistent.mongo.items.SpecialistDocument;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialistEntity;
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
    Specialist toModel(SpecialistDocument a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    SpecialistDocument toDocument(Specialist a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    Specialist toModel(SpecialistEntity a);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    SpecialistEntity toSaveEntity(Specialist a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    SpecialistEntity toUpdateEntity(Specialist a);

    List<Specialist> documentToModels(List<SpecialistDocument> a);

    List<Specialist> entityToModels(List<SpecialistEntity> a);
}
