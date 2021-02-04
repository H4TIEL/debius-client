package ml.debius.systems.repository;

import ml.debius.systems.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Integer> {

    Auth findByKey(String key);

}