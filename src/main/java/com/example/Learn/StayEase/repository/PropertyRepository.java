package com.example.Learn.StayEase.repository;

import com.example.Learn.StayEase.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {

}
