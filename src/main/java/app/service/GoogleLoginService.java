package app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class GoogleLoginService {

    public String thirdLoginByGoogle(HttpServletRequest request){
        String code = request.getParameter("code");
        log.info("code = {}",code);
        return "google";
    }
}
