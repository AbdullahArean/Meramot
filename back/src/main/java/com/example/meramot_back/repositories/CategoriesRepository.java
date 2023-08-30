package com.example.meramot_back.repositories;

import com.example.meramot_back.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    @Query("SELECT c FROM Categories c WHERE c.post_id = ?1")
    List<Categories> findByPost_id(Long post_id);
}
