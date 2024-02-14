package Entities;

import java.util.*;

/**
 * 
 */
public class Client {

	/**
	 * Default constructor
	 */
	public Client() {
	}

	/**
	 * 
	 */
	public int id;

	/**
	 * 
	 */
	public String Name;

	/**
	 * 
	 */
	public String Address;

	/**
	 * 
	 */
	public String Email;

	/**
	 * 
	 */
	public String Password;

	/**
	 * 
	 */

	public String PhoneNumber;

	public Client(int id, String name, String address, String email, String password, String phoneNumber) {
		this.id = id;
		Name = name;
		Address = address;
		Email = email;
		Password = password;
		PhoneNumber = phoneNumber;
	}
	public Client( String name, String address, String email, String password, String phoneNumber) {
		Name = name;
		Address = address;
		Email = email;
		Password = password;
		PhoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}



}