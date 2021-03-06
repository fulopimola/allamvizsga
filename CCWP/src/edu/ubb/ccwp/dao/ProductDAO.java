package edu.ubb.ccwp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.NotInShopException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.exception.RateNotFound;
import edu.ubb.ccwp.model.Product;

public interface ProductDAO {
	
	Product getProductByProductId(int productID) throws DAOException, ProductNotFoundException, SQLException, NotInShopException;
	ArrayList<Product> getAllProduct() throws DAOException, SQLException, NotInShopException;
	ArrayList<Product> getProductSearch(String likeName, int shopId, int compId, int catId) throws DAOException, SQLException,  NotInShopException;
	Product getProductByProductname(String str) throws DAOException, SQLException, ProductNotFoundException, NotInShopException;
	double[][] getproductInShopPrice(int productId) throws SQLException, DAOException,  NotInShopException;
	int getUserProductRate(int userID, int productId) throws SQLException, DAOException, RateNotFound;
	int updateRate(int userID, int productId, int rate) throws SQLException, DAOException, ProductNotFoundException, NotInShopException;
	int insertRate(int userID, int productId, int rate) throws SQLException, DAOException, ProductNotFoundException, NotInShopException;
	void updateProduct(Product prod) throws SQLException, DAOException;
	double getProductPriceInShop(int productId, int shopId) throws SQLException, DAOException;
	
}
