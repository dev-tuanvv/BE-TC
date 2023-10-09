package com.tutorcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorcenter.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
