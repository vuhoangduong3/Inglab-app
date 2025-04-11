//package unicorns.backend.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//public class LoggingFilter implements Filter {
//
//    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
//        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);
//
//        // Log request
//        logRequest(wrappedRequest);
//
//        chain.doFilter(wrappedRequest, wrappedResponse);
//
//        // Log response
//        logResponse(wrappedResponse);
//    }
//
//    private void logRequest(HttpServletRequest request) throws IOException {
//        String body = new String(request.getInputStream().readAllBytes());
//        logger.info("=== Request: {} {} | Body: {}", request.getMethod(), request.getRequestURI(), body);
//    }
//
//    private void logResponse(CachedBodyHttpServletResponse response) throws IOException {
//        String body = new String(response.getBody());
//        logger.info("==== Response: {} | Body: {}", response.getStatus(), body);
//    }
//}