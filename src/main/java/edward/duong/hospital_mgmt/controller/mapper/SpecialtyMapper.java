package edward.duong.hospital_mgmt.controller.mapper;

import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyReq;
import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyRes;
import edward.duong.hospital_mgmt.domain.models.Specialty;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpecialtyMapper {
    SpecialtyMapper INSTANCE = Mappers.getMapper(SpecialtyMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    SpecialtyRes toResponse(Specialty specialty);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Specialty toModel(SpecialtyReq req);

    List<SpecialtyRes> toResponseList(List<Specialty> specialties);
}
