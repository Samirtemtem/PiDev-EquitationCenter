package Entities;

/**
 *
 */
public class Product {

	/**
	 * Default constructor
	 */
	public Product(String name, String description, double price, int stockQty) {
		this.Name=name;
		this.Description=description;
		this.Price=(float)price;
		this.setStockQty(stockQty);
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

	/**
	 * Blob image data
	 */
	public byte[] blobImage;

	public Product(int productID, String name, String description, double price, int stockQty) {
		this.id = productID;
		Name = name;
		Price = (float) price;
		Description = description;
		StockQty = stockQty;
	}

	@Override
	public String toString() {
		return Name;
	}

	public Product(int id, String name, Float price, String description, int stockQty, byte[] blobImage) {
		this.id = id;
		Name = name;
		Price = price;
		Description = description;
		StockQty = stockQty;
		this.blobImage = blobImage;
	}

	public Product(String name, String description, float price, int stockQty, byte[] blobImage) {
		Name = name;
		Price = price;
		Description = description;
		StockQty = stockQty;
		this.blobImage = blobImage;
	}

	public Product(String name, Float price, String description, int stockQty) {
		Name = name;
		Price = price;
		Description = description;
		StockQty = stockQty;
	}

	public Product(int id, String name, Float price, String description, int stockQty) {
		this.id = id;
		Name = name;
		Price = price;
		Description = description;
		StockQty = stockQty;
	}

	public Product() {

	}

	public Product(int productID, String name, String description, double price, int stockQty, byte[] blobImage) {
		this.id = productID;
		this.Name = name;
		this.setDescription(description);
		this.Price = (float) price;
		this.setStockQty(stockQty);
		this.blobImage = blobImage;
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

	public byte[] getBlobImage() {
		return blobImage;
	}

	public void setBlobImage(byte[] blobImage) {
		this.blobImage = blobImage;
	}
}
