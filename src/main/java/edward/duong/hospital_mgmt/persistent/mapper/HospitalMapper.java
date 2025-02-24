package edward.duong.hospital_mgmt.persistent.mapper;

import edward.duong.hospital_mgmt.domain.models.Hospital;
import edward.duong.hospital_mgmt.domain.models.Location;
import edward.duong.hospital_mgmt.persistent.mongo.items.HospitalDocument;
import edward.duong.hospital_mgmt.persistent.postgre.entity.HospitalEntity;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HospitalMapper {
    HospitalMapper INSTANCE = Mappers.getMapper(HospitalMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    Hospital toModel(HospitalDocument a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "location", target = "location")
    HospitalDocument toDocument(Hospital a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    Hospital toModel(HospitalEntity a);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    HospitalEntity toSaveEntity(Hospital a);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    HospitalEntity toUpdateEntity(Hospital a);

    List<Hospital> documentToModels(List<HospitalDocument> employees);
    List<Hospital> entityToModels(List<HospitalEntity> employees);

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

        return Location.builder()
                .longitude(point.getX())
                .latitude(point.getY())
                .build();
    }
}
