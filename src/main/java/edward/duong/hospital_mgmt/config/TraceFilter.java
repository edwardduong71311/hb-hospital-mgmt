package edward.duong.hospital_mgmt.config;

import brave.Span;
import brave.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Objects;

@Component
public class TraceFilter extends GenericFilterBean {
    private final Tracer tracer;

    TraceFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Span currentSpan = this.tracer.currentSpan();
        if (Objects.nonNull(currentSpan)) {
            ((HttpServletResponse) response).addHeader("trace-id", currentSpan.context().traceIdString());
        }

        chain.doFilter(request, response);
    }
}
