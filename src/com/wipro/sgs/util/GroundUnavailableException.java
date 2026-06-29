package com.wipro.sgs.util;

public class GroundUnavailableException extends Exception{
	@Override
	public String toString() {
		return "Ground Unavailable! The Selected time slot is already booked.";
	}

}
