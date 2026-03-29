package com.example.Learn.StayEase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long bookingId;
    @NonNull
    private Long propertyId;
    @NonNull
    private Long guestId;
    @NonNull
    LocalDate bookingDate;
    @NonNull
    LocalDate checkInDate;
    @NonNull
    LocalDate checkOutDate;
    @NonNull
    private int guests;
    private String bookingStatus;
    private String paymentStatus;
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User guest;


}
