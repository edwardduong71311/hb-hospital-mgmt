package edward.duong.hospital_mgmt.controller.mapper;

import edward.duong.hospital_mgmt.controller.models.disease.DiseaseReq;
import edward.duong.hospital_mgmt.controller.models.disease.DiseaseRes;
import edward.duong.hospital_mgmt.domain.models.Disease;
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
    DiseaseRes toResponse(Disease disease);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "symptom", target = "symptom")
    @Mapping(source = "treatment", target = "treatment")
    Disease toModel(DiseaseReq req);

    List<DiseaseRes> toResponseList(List<Disease> diseases);
}
