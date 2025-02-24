package edward.duong.hospital_mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = { ZipkinAutoConfiguration.class })
public class HospitalMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalMgmtApplication.class, args);
	}

}
