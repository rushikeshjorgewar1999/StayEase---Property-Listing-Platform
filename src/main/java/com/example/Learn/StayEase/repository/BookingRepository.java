package com.example.Learn.StayEase.repository;

import com.example.Learn.StayEase.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {



}
