package edu.ubb.ccwp.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.ubb.ccwp.dao.ShoppingListDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.ProductInShop;
import edu.ubb.ccwp.model.ShoppingList;
import edu.ubb.ccwp.model.User;

public class ShoppingListJdbcDAO implements ShoppingListDAO {

	@Override
	public ShoppingList saveShoppingList(ShoppingList shl) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		
		//ki kell keresni, hogy a lista mar el van-e mentve.
		shl.setListId(saveShoppingListName(shl.getListName(), shl.getUser()));
		
		return shl;
	}
	
	public int saveShoppingListName(String name, User user) throws SQLException, DAOException{

		String command = "INSERT INTO `shoppinglist`(`ListName`, `users_UserID`) VALUES (?, ?);";
		PreparedStatement statement = JdbcConnection.getConnection()
				.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, name);
		statement.setInt(2, user.getUserID());
		statement.executeUpdate();
		ResultSet result = statement.getGeneratedKeys();
		result.next();
		return result.getInt(1);
	}

}
