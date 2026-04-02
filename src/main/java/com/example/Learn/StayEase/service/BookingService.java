package com.example.Learn.StayEase.service;

import com.example.Learn.StayEase.entity.Booking;
import com.example.Learn.StayEase.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

        private final BookingRepository bookingRepository;

        @Autowired
        public BookingService(BookingRepository bookingRepository) {
            this.bookingRepository = bookingRepository;
        }

        public void saveBookingInDB(Booking booking) {
             bookingRepository.save(booking);
        }

        public Booking getBookingById(Long id) {
            return bookingRepository.findById(id).orElse(null);
        }

        public List<Booking> getAllUserBookings() {
            return bookingRepository.findAll();
        }

        public Booking updateBookingStatusById(Booking booking, Long id) {
            Booking bookingById = getBookingById(id);
            if(bookingById != null) {
                bookingById.setBookingStatus(booking.getBookingStatus());
                return bookingRepository.save(bookingById);
            }
            return null;
        }

        public void cancelBookingById(Long id,Booking booking) {
            Booking bookingById = getBookingById(id);
            if(bookingById != null) {
                bookingById.setBookingStatus(booking.getBookingStatus());
                bookingRepository.save(bookingById);
            }
        }

}
