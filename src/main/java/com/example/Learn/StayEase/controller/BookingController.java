package com.example.Learn.StayEase.controller;

import com.example.Learn.StayEase.entity.Booking;
import com.example.Learn.StayEase.exceptions.ApiResponse;
import com.example.Learn.StayEase.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> handleCreateBooking(@RequestBody Booking booking) {
        bookingService.saveBookingInDB(booking);
        return new ResponseEntity<>(ApiResponse.builder().message("Booking created Successfully").build(),HttpStatus.CREATED);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> handleGetBookingById(@PathVariable Long bookingId) {
        Booking bookingById = bookingService.getBookingById(bookingId);
        if(bookingById == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(bookingById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> handleGetAllBookings() {
        List<Booking> allUserBookings = bookingService.getAllUserBookings();
        if(allUserBookings.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(allUserBookings, HttpStatus.OK);
    }


    @PutMapping("/{bookingId}/status")
    public ResponseEntity<Booking> handleUpdateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        Booking booking1 = bookingService.updateBookingStatusById(booking, bookingId);
        if(booking1 == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(booking1, HttpStatus.OK);
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse> handleCancelBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
         bookingService.cancelBookingById(bookingId,booking);
         return new ResponseEntity<>(ApiResponse.builder().message("booking cancelled successfully").build(), HttpStatus.OK);
    }
}
