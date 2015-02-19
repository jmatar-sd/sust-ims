 package core.main_content;

 import com.vaadin.ui.VerticalLayout;
 import com.vaadin.ui.Button;
 import core.main_content.panes.MainPane;
 import com.vaadin.server.Page;

 public class MainContent extends VerticalLayout{
 	{
 		initRoot();
 		initComponents();
 	}
 	// initialize the root properties -> { classes names, width, height ... etc }
 	private void initRoot(){
 		setWidth("100%");
 		setHeight("100%");
 		setMargin(true);
 		setStyleName("main-content");
 	}
 	// initiallize childs components  -> { call  define every component and give it basic configrations }
 	private void initComponents(){
 		initPanes();
 	}
 	private void initPanes(){
 		mainPane 	= new MainPane();
 		addComponent(mainPane);
 	}

 	private MainPane	mainPane;
 }