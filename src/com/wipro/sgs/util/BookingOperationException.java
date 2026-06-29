package com.wipro.sgs.util;

public class BookingOperationException extends Exception{
	@Override
	public String toString(){
		return "Invalid booking operation! Check booking hours, pricing, or operating time.";
	}

}
