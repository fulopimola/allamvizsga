package edu.ubb.ccwp.ui;

import java.sql.SQLException;
import java.util.ArrayList;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import edu.ubb.ccwp.dao.DAOFactory;
import edu.ubb.ccwp.dao.ShoppingListDAO;
import edu.ubb.ccwp.exception.DAOException;
import edu.ubb.ccwp.model.Product;
import edu.ubb.ccwp.model.ProductInShop;
import edu.ubb.ccwp.model.Shop;
import edu.ubb.ccwp.model.ShoppingList;
import edu.ubb.ccwp.model.User;
import edu.ubb.ccwp.ui.SearchPage.ButtonListener;
import edu.ubb.ccwp.ui.SearchPage.ListButtonListener;

@SuppressWarnings("serial")
public class ShoppingListUI  extends CustomComponent implements View {

	public static final String NAME = "shoppinglist";
	private VerticalLayout baseLayout;
	private User user;
	private ArrayList<ProductInShop> list;
	private Table table;
	private Label totalPrice;
	private Button save;
	private ShoppingList slist;

	private HorizontalLayout hLayout;
	private VerticalLayout layout;
	private VerticalLayout textContent;

	public ShoppingListUI(){
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


	@SuppressWarnings("unchecked")
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		user = (User) getSession().getAttribute("user");
		
		slist = new ShoppingList();
		slist.setUser(user);

		baseLayout.addComponent(new BasePageUI(user));

		setImmediate(true);

		//Label footer = new Label("ide jar a lablec");
		//layout.addComponent(footer);

		Panel textPanel = new Panel();
		textPanel.setSizeFull();
		textPanel.setHeight("100%");
		textContent = new VerticalLayout();

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

		table = new Table("All Product!");
		table.setSelectable(true);
		table.setImmediate(true);

		textContent.addComponent(table);
		
		totalPrice = new Label("<b>Total price:<b> 0",  ContentMode.HTML);
		textContent.addComponent(totalPrice);
		
		save = new Button("Save this list!", new SaveButtonListener());
		textContent.addComponent(save);
		
		if(user == null || user.getUserID() == -1){
			save.setVisible(false);
		}else{
			save.setVisible(true);
		}
		
		list = (ArrayList<ProductInShop>) getSession().getAttribute("productList");
		if(list == null){
			list = new ArrayList<ProductInShop>();
			getSession().setAttribute("productList", list);
			table.setPageLength(1);
			loadList();

		}else{

			loadList();
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

	private void loadList() {
		// TODO Auto-generated method stub



		table.removeAllItems();
		table.addContainerProperty("Id", Integer.class, null);
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Description",  String.class, null);
		table.addContainerProperty("Shop", String.class, null);
		table.addContainerProperty("Quantity",  TextField.class, null);
		table.addContainerProperty("Price", Double.class, null);
		table.addContainerProperty("Quantity x Price", Double.class, null);
		table.addContainerProperty("Remove", Button.class, null);
		table.setColumnWidth("Description", 80);
		
		table.setPageLength(list.size());

		int i = 0;
		double totPr = 0;
		for (final ProductInShop prodList : list) {

			final TextField quantity = new TextField();
			quantity.setValue(prodList.getQuant()+"");
			quantity.setWidth("50px");
			quantity.setMaxLength(5); 
			quantity.setRequired(true);
			
			Property.ValueChangeListener listener =
					new Property.ValueChangeListener() {
	
				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					try{
						prodList.setQuant(Double.parseDouble(quantity.getValue()));
						loadList();
						
					}catch (NumberFormatException ex){
						quantity.setValue(prodList.getQuant()+"");	
					}
				}
			};
			quantity.addValueChangeListener(listener);
			quantity.setImmediate(true);
			    




			table.addItem(new Object[] {prodList.getProd().getProductId(), prodList.getProd().getProductName(), prodList.getProd().getProductDescription(), prodList.getShop().getShopName(),quantity, prodList.getPrice(), prodList.getPrice()*prodList.getQuant(), new Button("Remove",
					new RemoveButtonListener(prodList))}, i );
			i++;
			table.setItemDescriptionGenerator(new ItemDescriptionGenerator() {                             
				public String generateDescription(Component source, Object itemId, Object propertyId) {
					if(propertyId == "Description") {
						return table.getItem(itemId).getItemProperty(propertyId).getValue()+"";
					}
					return null;                                                                       

				}
			});
			totPr+= prodList.getPrice()*prodList.getQuant();
			
		}
		
		totalPrice.setValue("<b>Total priece:<b> "+totPr);
	}
	
	
	class RemoveButtonListener implements ClickListener{
		ProductInShop product;
		public RemoveButtonListener(ProductInShop prodList) {
			product = prodList;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub
			list.remove(product);
			loadList();


		}
	}
	
	class SaveButtonListener implements ClickListener{
		
		public SaveButtonListener() {
		}

		@Override
		public void buttonClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			DAOFactory df = DAOFactory.getInstance();
			ShoppingListDAO sl  = df.getShoppingListDAO();
			
			try {
				
				slist.setList(list);
				slist.setListName("valami2");
				slist = sl.saveShoppingList(slist);
				System.out.println("id "+slist.getListId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}