package edu.ubb.ccwp.ui;

import help.MySub;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import edu.ubb.ccwp.model.Product;
import edu.ubb.ccwp.model.Shop;
import edu.ubb.ccwp.model.User;
import edu.ubb.ccwp.ui.SearchPage.ListButtonListener;

@SuppressWarnings("serial")
public class ShoppingList  extends CustomComponent implements View {

	public static final String NAME = "shoppinglist";
	private Button save;
	private VerticalLayout layout;
	private VerticalLayout baseLayout;
	private User user;
	private Shop shop;
	private Product product;

	public ShoppingList(int productId, int shopId){
		layout = new VerticalLayout();
		layout.setSizeFull();
		save = new Button("Valami", new ShopButtonListener("Shopping  List"));

		baseLayout = new VerticalLayout();
		layout.addComponent(baseLayout);

		layout.addComponent(save);

		setCompositionRoot(layout);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		user = (User) getSession().getAttribute("user");
		baseLayout.addComponent(new BasePageUI(user));
		
		

	}
	
	class ShopButtonListener implements ClickListener{

		String menuitem;
		public ShopButtonListener(String menuitem) {
			this.menuitem = menuitem;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub


		}

	}
}