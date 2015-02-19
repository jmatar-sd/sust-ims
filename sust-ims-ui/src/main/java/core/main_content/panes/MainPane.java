package core.main_content.panes;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Alignment;
import core.main_content.panes.tools_pane.ToolsPaneViewImpl;
import core.main_content.panes.tools_pane.ToolsPaneModel;
import core.main_content.panes.tools_pane.ToolsPanePresinter;
import core.main_content.panes.middle_pane.MiddlePane;

public class MainPane extends VerticalLayout{
	private static final String PANE_WIDTH		= "100%"
						       ,PANE_HEIGHT		= "100%"
						       ,PANE_STYLE_NAME = "main-pane";
	{
		initRoot();
		initComponents();
	}
	private void initRoot(){
 		setWidth(PANE_WIDTH);
 		setHeight(PANE_HEIGHT);
 		setStyleName(PANE_STYLE_NAME);
	}
	private void initComponents(){
		initMiddlePane();initToolsPane();
	}
	private void initToolsPane(){
		toolsPane 			= new ToolsPaneViewImpl();
		toolsPaneModel 		= new ToolsPaneModel();
		new ToolsPanePresinter(toolsPaneModel, toolsPane, middlePane);
		addComponent(toolsPane);
		setComponentAlignment(toolsPane, Alignment.BOTTOM_CENTER);
		
	}
	private void initMiddlePane(){
		middlePane 	= new MiddlePane();
		addComponent(middlePane);
		setComponentAlignment(middlePane, Alignment.TOP_CENTER);
		setExpandRatio(middlePane, 1.0f);
		
	}
	private ToolsPaneViewImpl 		toolsPane;private ToolsPaneModel toolsPaneModel;
	private MiddlePane 				middlePane;
}