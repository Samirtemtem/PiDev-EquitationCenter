package Entities;

/**
 *
 */
public class ProductOrder {

	public ProductOrder(Float price, int qty, String status, int product_id, int id_client, Float total_price) {
		Price = price;
		Qty = qty;
		Status = status;
		Product_id = product_id;
		this.id_client = id_client;
		Total_price = total_price;
	}

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

	/**
	 *
	 */
	public int Product_id;

	/**
	 *
	 */
	public int id_client; // New attribute

	/**
	 *
	 */
	public Float Total_price;

	public ProductOrder(int id, Float price, int qty, String status, int product_id, float total_Price, int id_client) {
		Id = id;
		Price = price;
		Qty = qty;
		Status = status;
		Product_id = product_id;
		this.id_client = id_client;
		Total_price = total_Price;
	}

	public ProductOrder(int id, Float price, int qty, String status, int product_id, int id_client, Float total_price) {
		Id = id;
		Price = price;
		Qty = qty;
		Status = status;
		Product_id = product_id;
		this.id_client = id_client;
		Total_price = total_price;
	}

	public ProductOrder(Float price, int qty, String status, int product_id, Float total_price) {
		Price = price;
		Qty = qty;
		Status = status;
		Product_id = product_id;
		this.id_client = id_client;
		Total_price = total_price;
	}

	public ProductOrder() {

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

	public void setPrice(Float Price) {
		this.Price = Price;
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

	public int getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(int Product_id) {
		this.Product_id = Product_id;
	}

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public Float getTotal_price() {
		return Total_price;
	}

	public void setTotal_price(Float totalPrice) {
		Total_price = totalPrice;
	}
}
