package edu.ubb.ccwp.model;

public class ProductInShop {
	private Product prod;
	private Shop shop;
	private double price;
	private boolean isDiscont;
	private int discontId;
	private boolean found;
	
	public Product getProd() {
		return prod;
	}
	public void setProd(Product prod) {
		this.prod = prod;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isDiscont() {
		return isDiscont;
	}
	public void setDiscont(boolean isDiscont) {
		this.isDiscont = isDiscont;
	}
	public int getDiscontId() {
		return discontId;
	}
	public void setDiscontId(int discontId) {
		this.discontId = discontId;
	}
	public boolean isFound() {
		return found;
	}
	public void setFound(boolean found) {
		this.found = found;
	}

}
