package core.main_content.panes.tools_pane;

import  com.vaadin.ui.Button.ClickEvent;
public interface ToolsPaneView{
	/*__*/ interface ToolsPaneListener  {void btnClick(ClickEvent event);}
	public void 	 addListener(ToolsPaneListener listener);
	public void 	 showMessage(String msg);
}