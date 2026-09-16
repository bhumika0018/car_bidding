package com.carsale.auth.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.carsale.auth.entity.User;
import com.carsale.auth.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findFirstByEmail(String email);

	Optional<User> findById(Long userId);

	Optional<User> findByName(String username);

	Optional<User> findByUserRole(UserRole customer);

	List<User> findAllByUserRole(UserRole customer);

}
