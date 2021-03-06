package edu.ubb.ccwp.ui;

import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.ProductDAO;
import edu.ubb.ccwp.dao.ShopDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.exception.NotInShopException;
import edu.ubb.ccwp.exception.ProductNotFoundException;
import edu.ubb.ccwp.exception.ShopNotFoundException;
import edu.ubb.ccwp.model.Product;
import edu.ubb.ccwp.model.ProductInShop;
import edu.ubb.ccwp.model.Shop;

@SuppressWarnings("serial")
public class ShoppingListSubWindow extends Window  {

	private Button ok;
	private Button cancel;
	private VerticalLayout layout;
	private int prodId;
	private Product prod;
	private Table table;
	private SearchPage search;
	private TextField quality;
	private Label error;



	@SuppressWarnings("serial")
	public ShoppingListSubWindow(int id, SearchPage sp) {
		super("Add shopping list"); 
		center();
		prodId = id;
		search = sp;


		table = new Table();
		table.setSelectable(true);
		table.setImmediate(true);
		table.setHeight("140px");
		table.setNullSelectionAllowed(false);

		quality = new TextField("Quality");
		quality.setMaxLength(5);
		quality.setWidth("50px");
		quality.setValue("1");
		quality.setRequired(true);
		
		error = new Label("Pleas enter a number!");
		error.setVisible(false);
		
		layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		// Disable the close button
		setClosable(false);


		loadProductPrice();




		// Trivial logic for closing the sub-window
		ok = new Button("Shop");
		ok.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {

					try {
						double numb = Double.parseDouble(quality.getValue());
						
						Shop shop = new Shop();

						DAOFactory df = DAOFactory.getInstance();

						shop = df.getShopsDAO().getShopByShopName((String) table.getItem(table.getValue()).getItemProperty("Shop").getValue());

						double price = df.getProductDAO().getProductPriceInShop(prod.getProductId(), shop.getShopId());

						ProductInShop productInShop = new ProductInShop();
						productInShop.setProd(prod);
						productInShop.setShop(shop);
						productInShop.setPrice(price);
						productInShop.setQuant(numb);



						search.addShopList(productInShop);

						close(); // Close the sub-window

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (NumberFormatException ex){
						quality.setComponentError(new UserError("Enter a number!"));
						error.setVisible(true);
					}
				

			}
		});

		cancel = new Button("Cancel");
		cancel.addClickListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				close();
			}
		});

		layout.addComponent(table);
		
		HorizontalLayout hor = new HorizontalLayout();
		hor.addComponent(quality);
		hor.addComponent(error);
		layout.addComponent(hor);

		HorizontalLayout hz = new HorizontalLayout();
		hz.addComponent(ok);
		hz.addComponent(cancel);
		layout.addComponent(hz);
	}

	private void loadProductPrice() {
		// TODO Auto-generated method stub
		table.addContainerProperty("Shop", String.class, null);
		table.addContainerProperty("Price", Double.class, null);


		try {
			DAOFactory df = DAOFactory.getInstance();
			ProductDAO prodDao = df.getProductDAO();
			ShopDAO shopDao = df.getShopsDAO();

			prod = prodDao.getProductByProductId(prodId);
			table.setCaption(prod.getProductName());
			ArrayList<Shop> shops = new ArrayList<Shop>();

			shops = shopDao.getShopsByProductId(prodId);

			int i = 0;
			for (Shop shop : shops) {
				table.addItem(new Object[] {shop.getShopName(), prodDao.getProductPriceInShop(prod.getProductId(), shop.getShopId())}, i);
				i++;
			}
			table.select(0);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotInShopException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShopNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}

