    package unicorns.backend.service;
    import unicorns.backend.dto.response.LoginResponse;


    public interface AuthService {

        LoginResponse login(String username, String password);
        void logout(String authHeader);
    }
