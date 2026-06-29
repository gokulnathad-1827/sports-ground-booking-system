package com.wipro.sgs.service;

import java.util.ArrayList;
import java.util.Iterator;

import com.wipro.sgs.entity.Booking;
import com.wipro.sgs.entity.Ground;
import com.wipro.sgs.entity.User;
import com.wipro.sgs.util.BookingNotFoundException;
import com.wipro.sgs.util.GroundNotFoundException;
import com.wipro.sgs.util.GroundUnavailableException;
import com.wipro.sgs.util.InvalidUserException;

public class BookingService {

    private ArrayList<User> users;
    private ArrayList<Ground> grounds;
    private ArrayList<Booking> bookings;

    public BookingService(ArrayList<User> users,
                          ArrayList<Ground> grounds,
                          ArrayList<Booking> bookings) {
        this.users = users;
        this.grounds = grounds;
        this.bookings = bookings;
    }

    public boolean validateUser(String userId)
            throws InvalidUserException {

        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }

        throw new InvalidUserException();
    }

    public Ground findGround(String groundId)
            throws GroundNotFoundException {

        for (Ground ground : grounds) {
            if (ground.getGroundId().equals(groundId)) {
                return ground;
            }
        }

        throw new GroundNotFoundException();
    }

    public boolean checkSlotAvailability(String groundId,
                                         String date,
                                         String timeSlot)
            throws GroundUnavailableException {

        for (Booking booking : bookings) {

            if (booking.getGroundId().equals(groundId)
                    && booking.getDate().equals(date)
                    && booking.getTimeSlot().equals(timeSlot)) {

                throw new GroundUnavailableException();
            }
        }

        return true;
    }

    public double calculateFare(Ground ground, String timeSlot) {

        String[] parts = timeSlot.split("-");

        int startHour = Integer.parseInt(parts[0].trim().split(" ")[0]);
        int endHour = Integer.parseInt(parts[1].trim().split(" ")[0]);

        int duration = endHour - startHour;

        return ground.getHourlyRate() * duration;
    }

    public Booking bookGround(String userId,
                               String groundId,
                               String date,
                               String timeSlot)
            throws Exception {

        validateUser(userId);

        Ground ground = findGround(groundId);

        checkSlotAvailability(groundId, date, timeSlot);

        double fare = calculateFare(ground, timeSlot);

        String bookingId = "B" + (bookings.size() + 1);

        Booking booking = new Booking(
                bookingId,
                userId,
                groundId,
                date,
                timeSlot,
                fare);

        bookings.add(booking);

        return booking;
    }

    public void cancelBooking(String bookingId)
            throws BookingNotFoundException {

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {

            Booking booking = iterator.next();

            if (booking.getBookingId().equals(bookingId)) {
                iterator.remove();
                return;
            }
        }

        throw new BookingNotFoundException();
    }

    public void printUserBookings(String userId) {

        for (Booking booking : bookings) {

            if (booking.getUserId().equals(userId)) {

                System.out.println("Booking ID : "
                        + booking.getBookingId());

                System.out.println("Ground ID : "
                        + booking.getGroundId());

                System.out.println("Date : "
                        + booking.getDate());

                System.out.println("Time Slot : "
                        + booking.getTimeSlot());

                System.out.println("Fare : "
                        + booking.getTotalFare());

                System.out.println("--------------------------");
            }
        }
    }

    public void printGroundSchedule(String groundId,
                                    String date) {

        for (Booking booking : bookings) {

            if (booking.getGroundId().equals(groundId)
                    && booking.getDate().equals(date)) {

                System.out.println("Booking ID : "
                        + booking.getBookingId());

                System.out.println("User ID : "
                        + booking.getUserId());

                System.out.println("Time Slot : "
                        + booking.getTimeSlot());

                System.out.println("--------------------------");
            }
        }
    }
}
