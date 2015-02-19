package core.main_content.panes.tools_pane;

import  java.util.List;
import  java.util.ArrayList;
import  com.vaadin.ui.HorizontalLayout;
import 	com.vaadin.ui.Button;
import  com.vaadin.ui.Button.ClickListener;
import  com.vaadin.ui.Button.ClickEvent;
import  com.vaadin.ui.themes.ValoTheme;
import  core.fonts.PowerGlassIcons;
import  charts.LineChart;
import  charts.BarChart;
import  charts.PieChart;
import core.main_content.panes.middle_pane.viewer.ViewerModel;
import core.main_content.panes.middle_pane.viewer.ViewerPresinter;
import core.main_content.panes.middle_pane.viewer.ViewerViewImpl;


public class ToolsPaneViewImpl extends HorizontalLayout implements ToolsPaneView, ClickListener{
	private static final String PANE_WIDTH		= "100%" 
							   ,PANE_HEIGHT		= "89px"
							   ,BTN_WIDTH		= "89px";
        
	{initRoot();initComponents();}
	private void initRoot(){
		setWidth(PANE_WIDTH);
		setHeight(PANE_HEIGHT);
		setStyleName("tools-pane");
		setSpacing(true);
		setMargin(true);
	}
	private void initComponents(){
		initButtons();
	}
	private void initButtons(Button... buttons){
		// for(Button button: buttons){
		// }
		lineChartBtn 	= new Button(PowerGlassIcons.LINE_CHART_ICON);
		barChartBtn 	= new Button(PowerGlassIcons.BAR_CHART_ICON);
		pieChartBtn 	= new Button(PowerGlassIcons.PIE_CHART_ICON);
		dataOnTableBtn	= new Button(PowerGlassIcons.TEXT_FILE);
		manageDataBtn	= new Button(PowerGlassIcons.GEARS);
		userInfoBtn		= new Button(PowerGlassIcons.USERS);


		addComponents(lineChartBtn, barChartBtn, pieChartBtn, dataOnTableBtn, manageDataBtn
			, userInfoBtn);
		initProperties(lineChartBtn, barChartBtn, pieChartBtn, dataOnTableBtn, manageDataBtn
			, userInfoBtn);
		
		lineChartBtn.	addStyleName("line-chart-btn");
		barChartBtn.	addStyleName("bar-chart-btn");
		pieChartBtn.	addStyleName("pie-chart-btn");
		dataOnTableBtn.	addStyleName("data-on-table-btn");
		manageDataBtn.	addStyleName("manage-data-btn");
		userInfoBtn.	addStyleName("user-info-btn");

		initLineChartComponent();initBarChartComponent();initPieChartComponent();
	}
	private void initProperties(Button... buttons){
		for (Button button : buttons) {
			button.setStyleName("btn");
			button.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
			button.addClickListener(this);
		}
	}
	@Override public void 	 addListener(ToolsPaneListener listener){  
			listeners.add(listener);
	}
	@Override public void 	 buttonClick(ClickEvent event){
		for (ToolsPaneListener listener : listeners) {
			listener.btnClick(event);
		}
	}

	@Override public void 	 showMessage(String msg){
	}
	private   void initLineChartComponent(){
		lineChart 	= new LineChart();
		lineChart.setVisible(false);
	}
	private   void initBarChartComponent(){
		barChart 	= new BarChart();
		barChart.setVisible(false);
	}
	private   void initPieChartComponent(){
		pieChart 	= new PieChart();
		pieChart.setVisible(false);
	}
        
        
	private List<ToolsPaneListener> listeners 
	= new ArrayList<>();
	private Button lineChartBtn
				  ,barChartBtn
				  ,pieChartBtn
				  ,dataOnTableBtn
				  ,manageDataBtn
				  ,userInfoBtn;
	public Button 		getLineChartBtn()	 {return lineChartBtn;}
	public Button 		getBarChartBtn() 	 {return barChartBtn; }
	public Button 		getPieChartBtn() 	 {return pieChartBtn; }
	public Button 		getDataOnTableBtn()  {return dataOnTableBtn;  }
	public Button 		getManageDataBtn()   {return manageDataBtn;	  }
	public Button 		getUserInfoBtn()  	 {return userInfoBtn;  }

	public LineChart 	lineChart;private BarChart barChart;private PieChart pieChart;

	public LineChart 	getLineChart()	 {return lineChart;}
	public BarChart 	getBarChart ()	 {return barChart; }
	public PieChart 	getPieChart ()	 {return pieChart; }
}