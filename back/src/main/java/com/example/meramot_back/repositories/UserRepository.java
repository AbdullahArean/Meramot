package com.example.meramot_back.repositories;

import com.example.meramot_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User getReferenceByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.uid = ?1")
    User getReferenceByUser_id(UUID uid);

    @Query("SELECT count(u) FROM User u WHERE u.email = ?1")
    int countByEmail(String email);

}
