package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.Specialty;
import edward.duong.hospital_mgmt.persistent.mongo.items.SpecialtyDocument;
import edward.duong.hospital_mgmt.persistent.postgre.entity.SpecialtyEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpecialtyMapper {
    SpecialtyMapper INSTANCE = Mappers.getMapper(SpecialtyMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    Specialty toModel(SpecialtyDocument a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    SpecialtyDocument toDocument(Specialty a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    Specialty toModel(SpecialtyEntity a);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    SpecialtyEntity toSaveEntity(Specialty a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "description", target = "description")
    SpecialtyEntity toUpdateEntity(Specialty a);

    List<Specialty> documentToModels(List<SpecialtyDocument> a);

    List<Specialty> entityToModels(List<SpecialtyEntity> a);
}
