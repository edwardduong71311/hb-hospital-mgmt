package edward.duong.hospital_mgmt.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Setter;
import net.logstash.logback.encoder.LogstashEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Setter
public class LogAppender extends AppenderBase<ILoggingEvent> {
    private LogstashEncoder encoder;

    private final RestTemplate restTemplate = new RestTemplate();
    private String url;
    private String username;
    private String password;

    @Override
    public void start() {
        encoder = new LogstashEncoder();
        encoder.setIncludeMdc(true);
        encoder.start();
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        try {
            String jsonLog = new String(encoder.encode(eventObject));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            headers.set("Authorization", "Basic " + encodedAuth);

            HttpEntity<String> request = new HttpEntity<>(jsonLog, headers);
            restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            addError("Failed to send log to " + url, e);
        }
    }

    @Override
    public void stop() {
        encoder.stop();
        super.stop();
    }
}