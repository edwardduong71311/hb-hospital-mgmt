package edward.duong.hospital_mgmt.controller.mapper;

import edward.duong.hospital_mgmt.controller.models.hospitals.HospitalReq;
import edward.duong.hospital_mgmt.controller.models.hospitals.HospitalRes;
import edward.duong.hospital_mgmt.domain.models.Hospital;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HospitalMapper {
    HospitalMapper INSTANCE = Mappers.getMapper(HospitalMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location.longitude", target = "longitude")
    @Mapping(source = "location.latitude", target = "latitude")
    HospitalRes toResponse(Hospital hospital);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "longitude", target = "location.longitude")
    @Mapping(source = "latitude", target = "location.latitude")
    Hospital toModel(HospitalReq hospital);

    List<HospitalRes> toResponseList(List<Hospital> hospital);
}
