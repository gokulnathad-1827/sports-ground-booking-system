package com.wipro.sgs.util;

public class BookingNotFoundException extends Exception{
	@Override
	public String toString(){
		return "Booking ID not found.";
	}

}
