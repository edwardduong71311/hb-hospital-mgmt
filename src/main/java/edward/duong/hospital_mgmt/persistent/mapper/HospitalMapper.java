package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.Hospital;
import edward.duong.hospital_mgmt.domain.models.Location;
import edward.duong.hospital_mgmt.persistent.mongo.items.HospitalDocument;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
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
    @Mapping(source = "location", target = "location")
    Hospital toModel(HospitalDocument a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "telephone", target = "telephone")
    @Mapping(source = "location", target = "location")
    HospitalDocument toDocument(Hospital a);

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
    HospitalEntity toSaveEntity(Hospital a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "telephone", target = "telephone")
    HospitalEntity toUpdateEntity(Hospital a);

    List<Hospital> documentToModels(List<HospitalDocument> a);

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
