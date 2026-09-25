package krupkoillia.chesstracker.userservice.repository;

import krupkoillia.chesstracker.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
