package ml.debius.systems.service;

import ml.debius.systems.model.Auth;
import ml.debius.systems.repository.AuthRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean verifyKey(String key) {
        Auth auth = this.authRepository.findByKey(key);
        return auth != null;
    }

    public Auth retrieveKey(String key){
        return this.authRepository.findByKey(key);
    }

}