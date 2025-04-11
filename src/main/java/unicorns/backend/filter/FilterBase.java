package unicorns.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import unicorns.backend.util.DateTimeUtil;

import java.util.Date;

@Log4j2
public class FilterBase {
    public void doFilterAndLog(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
            MultiReadHttpServletResponse wrappedResponse = new MultiReadHttpServletResponse(response);
            Date startTime = new Date();

            log.info("=========== Start request " + wrappedRequest.getRequestURL());
            request = wrappedRequest;
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            filterChain.doFilter(request, wrappedResponse);
            String uri = request.getRequestURL().toString();
            Date endTime = new Date();
            log.info("============ Start time of Request " + uri + " : " + DateTimeUtil.toYMDTime(startTime)
                    + "\n ========= REQUEST with Uri: " + uri + " : " + IOUtils.toString(wrappedRequest.getReader())
                    + "\n ========= RESPONSE with Uri: " + uri + " : " + new String(wrappedResponse.getCopy(), wrappedResponse.getCharacterEncoding())
                    + "\n ============ End time of Request " + uri + " : " + DateTimeUtil.toYMDTime(endTime)
                    + "\n============ Time run of Request " + uri + " : " + (endTime.getTime() - startTime.getTime()));
        } catch (Exception e) {
            log.error(e);
        }
    }
}
