package core.main_content.panes.middle_pane.sust_logo;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Image;
import com.vaadin.ui.Alignment;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;

import com.vaadin.shared.ui.label.ContentMode;

public class SustLogo extends VerticalLayout{
	private static final String ROOT_WIDTH = "100%"
							   ,ROOT_HEIGHT= "100%"
							   ,TITLE 	   = "<center color='white'><p><h3>Sudan University Of Science & Technology<br/>"
							   +"<strong>Computer Center </strong><br/>TSU & Networking Department</h3></p></center>"
							   ;
	public  static final Resource LOGO_IMAGE = new ThemeResource("Sudan_university_logo.jpg");
	{initRoot();iniComponents();}
	private void initRoot(){setWidth(ROOT_WIDTH);setHeight(ROOT_HEIGHT);setStyleName("logo-pane");}
	private void iniComponents(){
		initLogo();initTitle();addComponents(logo, title);
		setComponentAlignment(logo, Alignment.TOP_CENTER);
		setComponentAlignment(title, Alignment.TOP_RIGHT);
	}
	private void initLogo()
	{
		logo 	= new Image("", LOGO_IMAGE);
		logo.setStyleName("sust-logo-image");
		logo.setWidth("122px");
		logo.setHeight("122px");
	}
	private void initTitle()
	{
		title 	= new Label(TITLE, ContentMode.HTML);
		title.setStyleName("sust-logo-title");
		title.setWidth("98%");
	}
	private Image   logo;
	private Label 	title;
}