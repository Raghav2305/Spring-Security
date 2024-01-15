package com.example.raghav.SpringSecurityClient.Repository;

import com.example.raghav.SpringSecurityClient.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
