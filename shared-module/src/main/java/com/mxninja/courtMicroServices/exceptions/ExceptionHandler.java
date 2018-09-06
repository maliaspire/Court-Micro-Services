package com.mxninja.courtMicroServices.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxninja.courtMicroServices.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@Slf4j
@Component
public class ExceptionHandler extends AbstractHandlerExceptionResolver {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ApiStatus status;
        if (ex instanceof ApiException) {
            status = ((ApiException) ex).getApiStatus();
        } else {
            status = ApiStatus.INTERNAL_SERVER_ERROR;
            log.error(ex.getMessage(), ex);
        }
        try {
            ResponseModel responseModel = new ResponseModel(status);
            String jsonStr = mapper.writeValueAsString(responseModel);
            response.setStatus(200);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(jsonStr);
            response.getWriter().flush();
            return new ModelAndView();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
