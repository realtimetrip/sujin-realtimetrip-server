package sujin.realtimetrip.User.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujin.realtimetrip.User.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
