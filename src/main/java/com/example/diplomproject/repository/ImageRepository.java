package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ImageRepository extends JpaRepository<Photo, Long> {
}
