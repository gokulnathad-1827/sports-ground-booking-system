package com.wipro.sgs.util;

public class InvalidUserException extends Exception{
	@Override
	public String toString() {
		return "Invalid User! User ID not found.";
	}

}
