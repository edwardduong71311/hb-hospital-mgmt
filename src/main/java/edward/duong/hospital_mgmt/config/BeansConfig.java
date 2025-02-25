package edward.duong.hospital_mgmt.config;

import edward.duong.hospital_mgmt.config.database.MongoConfig;
import edward.duong.hospital_mgmt.config.database.PostgresConfig;
import edward.duong.hospital_mgmt.domain.DefaultDisease;
import edward.duong.hospital_mgmt.domain.DefaultHospital;
import edward.duong.hospital_mgmt.domain.DefaultSpecialist;
import edward.duong.hospital_mgmt.domain.DefaultSpecialty;
import edward.duong.hospital_mgmt.domain.input_ports.DiseaseUseCase;
import edward.duong.hospital_mgmt.domain.input_ports.HospitalUseCase;
import edward.duong.hospital_mgmt.domain.input_ports.SpecialistUseCase;
import edward.duong.hospital_mgmt.domain.input_ports.SpecialtyUseCase;
import edward.duong.hospital_mgmt.persistent.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    @ConditionalOnBean({MongoConfig.class})
    public HospitalUseCase hospitalUseCaseMongo(final MongoHospitalPersistent persistent) {
        return new DefaultHospital(persistent);
    }

    @Bean
    @ConditionalOnBean({PostgresConfig.class})
    public HospitalUseCase hospitalUseCasePostgres(final PostgresHospitalPersistent persistent) {
        return new DefaultHospital(persistent);
    }

    @Bean
    @ConditionalOnBean({MongoConfig.class})
    public SpecialtyUseCase specialtyUseCaseMongo(final MongoSpecialtyPersistent persistent) {
        return new DefaultSpecialty(persistent);
    }

    @Bean
    @ConditionalOnBean({PostgresConfig.class})
    public SpecialtyUseCase specialtyUseCasePostgres(final PostgresSpecialtyPersistent persistent) {
        return new DefaultSpecialty(persistent);
    }

    @Bean
    @ConditionalOnBean({MongoConfig.class})
    public SpecialistUseCase specialistUseCaseMongo(final MongoSpecialistPersistent persistent) {
        return new DefaultSpecialist(persistent);
    }

    @Bean
    @ConditionalOnBean({PostgresConfig.class})
    public SpecialistUseCase specialistUseCasePostgres(final PostgresSpecialistPersistent persistent) {
        return new DefaultSpecialist(persistent);
    }

    @Bean
    @ConditionalOnBean({MongoConfig.class})
    public DiseaseUseCase diseaseUseCaseMongo(final MongoDiseasePersistent persistent) {
        return new DefaultDisease(persistent);
    }

    @Bean
    @ConditionalOnBean({PostgresConfig.class})
    public DiseaseUseCase diseaseUseCasePostgres(final PostgresDiseasePersistent persistent) {
        return new DefaultDisease(persistent);
    }
}
