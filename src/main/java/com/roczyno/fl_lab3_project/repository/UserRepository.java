package com.roczyno.fl_lab3_project.repository;

import com.roczyno.fl_lab3_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
