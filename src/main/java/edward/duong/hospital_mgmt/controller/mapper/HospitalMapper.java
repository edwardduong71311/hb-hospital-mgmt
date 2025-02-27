package edward.duong.hospital_mgmt.controller.mapper;

import edward.duong.hospital_mgmt.controller.models.hospital.*;
import edward.duong.hospital_mgmt.domain.models.hospital.Hospital;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HospitalMapper {
    HospitalMapper INSTANCE = Mappers.getMapper(HospitalMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "location.longitude", target = "longitude")
    @Mapping(source = "location.latitude", target = "latitude")
    @Mapping(source = "specialists", target = "specialists")
    HospitalRes toResponse(Hospital hospital);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "longitude", target = "location.longitude")
    @Mapping(source = "latitude", target = "location.latitude")
    @Mapping(source = "specialists", target = "specialists")
    Hospital toModel(HospitalReq hospital);

    List<HospitalRes> toResponseList(List<Hospital> hospital);
}
