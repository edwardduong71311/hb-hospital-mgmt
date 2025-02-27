package edward.duong.hospital_mgmt.controller.mapper;

import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistReq;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistRes;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
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
    SpecialistRes toResponse(Specialist specialist);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "specialties", target = "specialties")
    Specialist toModel(SpecialistReq req);

    List<SpecialistRes> toResponseList(List<Specialist> specialists);
}
