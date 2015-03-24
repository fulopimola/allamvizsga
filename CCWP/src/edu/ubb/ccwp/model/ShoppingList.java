package edu.ubb.ccwp.model;

import java.util.ArrayList;

public class ShoppingList {
	
	private int listId;
	private String listName;
	private User user;
	private ArrayList<ProductInShop> list;
	
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ArrayList<ProductInShop> getList() {
		return list;
	}
	public void setList(ArrayList<ProductInShop> list) {
		this.list = list;
	}

}
