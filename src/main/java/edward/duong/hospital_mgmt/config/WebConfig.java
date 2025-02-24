package edward.duong.hospital_mgmt.config;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RequestInterceptor loggingInterceptor;

    @Value("${app.integration.health_buddy_be.url}")
    private String url;

    public WebConfig(RequestInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }

    @Bean
    public RestClient.Builder restClientBuilder(Tracer tracer, Propagator propagator) {
        return RestClient.builder()
            .baseUrl(url)
            .requestInterceptor((request, body, execution) -> {
                propagator.inject(
                    Objects.requireNonNull(tracer.currentSpan()).context(),
                    request,
                    (req, key, value) -> Objects.requireNonNull(req).getHeaders().add(key, value));
                return execution.execute(request, body);
            });
    }

    @Bean("healthBuddy")
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}
