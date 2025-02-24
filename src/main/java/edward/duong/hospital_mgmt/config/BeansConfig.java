package edward.duong.hospital_mgmt.config;

import edward.duong.hospital_mgmt.config.database.MongoConfig;
import edward.duong.hospital_mgmt.config.database.PostgresConfig;
import edward.duong.hospital_mgmt.domain.DefaultHospital;
import edward.duong.hospital_mgmt.domain.input_ports.HospitalUseCase;
import edward.duong.hospital_mgmt.persistent.MongoHospitalPersistent;
import edward.duong.hospital_mgmt.persistent.PostgresHospitalPersistent;
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

}
