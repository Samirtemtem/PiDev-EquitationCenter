package Entities;

import java.util.*;

/**
 * 
 */
public class Product {

	/**
	 * Default constructor
	 */
	public Product() {
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
	public Float Price;

	/**
	 * 
	 */
	public String Description;

	/**
	 * 
	 */
	public int StockQty;

	public Product(int id, String name, Float price, String description, int stockQty) {
		this.id = id;
		Name = name;
		Price = price;
		Description = description;
		StockQty = stockQty;
	}
	public Product(String name, Float price, String description, int stockQty) {
		Name = name;
		Price = price;
		Description = description;
		StockQty = stockQty;
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

	public Float getPrice() {
		return Price;
	}

	public void setPrice(Float price) {
		Price = price;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getStockQty() {
		return StockQty;
	}

	public void setStockQty(int stockQty) {
		StockQty = stockQty;
	}



}