package Entities;

import java.util.*;

public class ProductOrder {

	private int id;
	private Float price;
	private int qty;
	private String status;
	private Float totalPrice;
	private Product product;

	public ProductOrder(int id, Float price, int qty, String status, Product product) {
		this.id = id;
		this.price = price;
		this.qty = qty;
		this.status = status;
		this.product = product;
		this.totalPrice = calculateTotalPrice();
	}
	public ProductOrder(Float price, int qty, String status, Product product) {
		this.price = price;
		this.qty = qty;
		this.status = status;
		this.product = product;
		this.totalPrice = calculateTotalPrice();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
		this.totalPrice = calculateTotalPrice();
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
		this.totalPrice = calculateTotalPrice();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private Float calculateTotalPrice() {
		return price * qty;
	}
}