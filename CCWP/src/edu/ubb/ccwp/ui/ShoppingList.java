package edu.ubb.ccwp.ui;

import java.util.ArrayList;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import edu.ubb.ccwp.model.ProductInShop;
import edu.ubb.ccwp.model.User;
import edu.ubb.ccwp.ui.SearchPage.ButtonListener;

@SuppressWarnings("serial")
public class ShoppingList  extends CustomComponent implements View {

	public static final String NAME = "shoppinglist";
	private VerticalLayout baseLayout;
	private User user;
	private ArrayList<ProductInShop> list;
	
	private HorizontalLayout hLayout;
	private VerticalLayout layout;
	
	public ShoppingList(){
		layout = new VerticalLayout();
		layout.setSizeFull();
		baseLayout = new VerticalLayout();
		layout.addComponent(baseLayout);
		hLayout = new HorizontalLayout();
		
		hLayout.setSizeFull();
		hLayout.setHeight("100%");
		hLayout.setWidth("100%");

		layout.addComponent(hLayout);
		layout.setExpandRatio(hLayout, 1.0f);
		setCompositionRoot(layout);
		menuLoad();
		
	}


	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		user = (User) getSession().getAttribute("user");

		baseLayout.addComponent(new BasePageUI(user));

		setImmediate(true);

		Label footer = new Label("ide jar a lablec");

		layout.addComponent(footer);

		Panel textPanel = new Panel();
		textPanel.setSizeFull();
		textPanel.setHeight("100%");
		VerticalLayout textContent = new VerticalLayout();

		textContent.setHeight("100%");
		textContent.setWidth("100%");
		textContent.setMargin(true);
		textPanel.setContent(textContent);
		
		hLayout.addComponent(textPanel);
		hLayout.setExpandRatio(textPanel, 1.0f);

		VerticalLayout panelContent = new VerticalLayout();
		panelContent.setSizeFull();
		panelContent.setMargin(true);
		layout.addComponent(panelContent); // Also clears


		list = (ArrayList<ProductInShop>) getSession().getAttribute("productList");
		if(list == null){
			System.out.println(" null ");
			list = new ArrayList<ProductInShop>();
			getSession().setAttribute("productList", list);

		}else{
			for (ProductInShop prodList : list) {


				System.out.println(prodList.getProd().getProductName()+" " + prodList.getShop().getShopName());
			}
		}

	}
	private void menuLoad(){

		Panel verticalMenuPanel = new Panel();
		verticalMenuPanel.setHeight("100%");
		verticalMenuPanel.setWidth(null);
		VerticalLayout verticalMenuContent = new VerticalLayout();
		verticalMenuContent.addComponent(new Button("valamiii"));
		verticalMenuContent.setWidth(null);
		verticalMenuContent.setMargin(true);
		verticalMenuPanel.setContent(verticalMenuContent);
		hLayout.addComponent(verticalMenuPanel);


	}


	class ShopButtonListener implements ClickListener{
		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			

		}

	}
}