package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.Disease;
import edward.duong.hospital_mgmt.persistent.mongo.items.DiseaseDocument;
import edward.duong.hospital_mgmt.persistent.postgre.entity.DiseaseEntity;
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
    Disease toModel(DiseaseDocument a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "symptom", target = "symptom")
    @Mapping(source = "treatment", target = "treatment")
    DiseaseDocument toDocument(Disease a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "symptom", target = "symptom")
    @Mapping(source = "treatment", target = "treatment")
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

    List<Disease> documentToModels(List<DiseaseDocument> a);

    List<Disease> entityToModels(List<DiseaseEntity> a);
}
