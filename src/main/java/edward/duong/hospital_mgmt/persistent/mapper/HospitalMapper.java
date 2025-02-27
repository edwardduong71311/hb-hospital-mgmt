package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.*;
import edward.duong.hospital_mgmt.domain.models.hospital.Hospital;
import edward.duong.hospital_mgmt.domain.models.spec.Specialist;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalSpecialistEntity;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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
    Hospital toModel(HospitalEntity a);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "specialists", target = "specialists")
    HospitalEntity toSaveEntity(Hospital a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "specialists", target = "specialists")
    HospitalEntity toUpdateEntity(Hospital a);

    @Mapping(source = "specialist.id", target = "id")
    @Mapping(source = "specialist.name", target = "name")
    @Mapping(source = "specialist.status", target = "status")
    Specialist toModel(HospitalSpecialistEntity a);

    List<Hospital> entityToModels(List<HospitalEntity> a);

    default Point map(Location location) {
        if (location == null) {
            return null;
        }
        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(location.getLongitude(), location.getLatitude()));
    }

    default Location map(Point point) {
        if (point == null) {
            return null;
        }

        return Location.builder().longitude(point.getX()).latitude(point.getY()).build();
    }
}
