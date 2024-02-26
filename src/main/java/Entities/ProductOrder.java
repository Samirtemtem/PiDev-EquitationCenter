package Entities;

import java.util.*;

/**
 * 
 */
public class ProductOrder {



	/**
	 * 
	 */
	public int Id;

	/**
	 * 
	 */
	public Float Price;

	/**
	 * 
	 */
	public int Qty;

	/**
	 * 
	 */
	public String Status;

	public ProductOrder(int id, Float price, int qty, String status) {
		Id = id;
		Price = price;
		Qty = qty;
		Status = status;
	}
	public ProductOrder(Float price, int qty, String status) {
		Price = price;
		Qty = qty;
		Status = status;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Float getPrice() {
		return Price;
	}

	public void setPrice(Float price) {
		Price = price;
	}

	public int getQty() {
		return Qty;
	}

	public void setQty(int qty) {
		Qty = qty;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
}