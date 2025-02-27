package edward.duong.hospital_mgmt.config;

import edward.duong.hospital_mgmt.domain.*;
import edward.duong.hospital_mgmt.domain.input_ports.*;
import edward.duong.hospital_mgmt.persistent.postgre.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public HospitalUseCase hospitalUseCasePostgres(final HospitalPersistent persistent) {
        return new DefaultHospitalUsecase(persistent);
    }

    @Bean
    public SpecialtyUseCase specialtyUseCasePostgres(final SpecialtyPersistent persistent) {
        return new DefaultSpecialtyUsecase(persistent);
    }

    @Bean
    public SpecialistUseCase specialistUseCasePostgres(final SpecialistPersistent persistent) {
        return new DefaultSpecialistUsecase(persistent);
    }

    @Bean
    public DiseaseUseCase diseaseUseCasePostgres(final DiseasePersistent persistent) {
        return new DefaultDiseaseUsecase(persistent);
    }

    @Bean
    public ScheduleUseCase scheduleUseCasePostgres(
            final SchedulePersistent persistent, HospitalUseCase hospitalUseCase) {
        return new DefaultScheduleUsecase(persistent, hospitalUseCase);
    }
}
