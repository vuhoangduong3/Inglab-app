package unicorns.backend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Authorization {

    private static  JwtUtil jwtUtil;

    public void isAdmin(String AuthHeader){

        if(AuthHeader == null || AuthHeader.isEmpty()){
            throw new ApplicationException(ApplicationCode.INVALID_TOKEN);
        }

        String token = jwtUtil.extractToken(AuthHeader);
        String role = jwtUtil.extractRole(token);

        if(!role.equals("admin")){
            throw new ApplicationException(ApplicationCode.INVALID_ROLE);
        }
    }
}
