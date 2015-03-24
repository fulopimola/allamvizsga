package edu.ubb.ccwp.dao;

import java.sql.SQLException;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.ShoppingList;

public interface ShoppingListDAO {

	ShoppingList saveShoppingList(ShoppingList spl) throws SQLException, DAOException;

}
