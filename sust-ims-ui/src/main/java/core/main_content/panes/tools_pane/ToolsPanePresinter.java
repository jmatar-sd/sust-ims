package core.main_content.panes.tools_pane;

import java.util.ArrayList;
import java.util.List;

import charts.BarChart;
import charts.LineChart;
import charts.PieChart;

import com.vaadin.ui.Button.ClickEvent;

import core.main_content.panes.middle_pane.MiddlePane;

public class ToolsPanePresinter implements ToolsPaneView.ToolsPaneListener{
			private	ToolsPaneViewImpl  view;
			private	ToolsPaneModel	   model;
			private MiddlePane  	   middlePane;
                        
                        
			public 		 		 	   ToolsPanePresinter(ToolsPaneModel model, ToolsPaneViewImpl view, MiddlePane middlePane)	
			{this.view 	= view;this.model 	= model;this.middlePane = middlePane;view.addListener(this);}
  @Override public 	void	 	  	   btnClick(ClickEvent event)
			{
				if(event.getButton()	  == view.getLineChartBtn())
				{displayLineChart();  }
				else if(event.getButton() == view. getBarChartBtn())
				{displayBarChart();   }
				else if(event.getButton() == view. getPieChartBtn())
				{displayPieChart();   }
				else if(event.getButton() == view.  getDataOnTableBtn())
				{displayDataOnTable();        }
				else if(event.getButton() == view.   getManageDataBtn())
				{displayManageData(); }
				else if(event.getButton() == view.  getUserInfoBtn())
				{displayUserInfo();}
			}
			private void displayLineChart()
			{

				lineChart = new LineChart<>();

//				lineChart.setLabels(model.getLabels());
				List<List<Integer>>  serises = new ArrayList<>();
				
				List<Integer> s = new ArrayList<>();
				s.add(10);s.add(33);s.add(26);
				s.add(52);s.add(66);s.add(62);
				
				serises.add(s);
				
				List<Integer> ss = new ArrayList<>();
				ss.add(63);ss.add(3);ss.add(33);
				ss.add(63);ss.add(20);ss.add(22);
				
				serises.add(ss);
				
//				serises.add(model.getHddCapacities());
				
				lineChart.setSerises(serises);

				lineChart.setVisible(true);
				lineChart.setWidth("100%");
				lineChart.setHeight("90%");
				lineChart.setViewLow(100);
				lineChart.setAreaVisible(true);
				lineChart.setAnimationEnabled(true);
				lineChart.setAnimationDelays(500);
				middlePane.getChartsPane().removeAllComponents();
				middlePane.getChartsPane().addComponent(lineChart);
			}
			private void displayBarChart()
			{
				List<Integer>  s = new ArrayList<>();
				s.add(23);s.add(66);s.add(11);
				
				BarChart barChart = new BarChart();
				barChart.setSerises(s);
				
				barChart.setVisible(true);
				barChart.setWidth("100%");
				barChart.setHeight("90%");
				
				middlePane.getChartsPane().removeAllComponents();
				middlePane.getChartsPane().addComponent(barChart);
			}
			private void displayPieChart()
			{
				
				List<Integer> serise = new ArrayList<>();
				serise.add(66);
				serise.add(23);
				serise.add(11);
				
				PieChart pieChart = new PieChart();
				pieChart.setSerises(serise);
//				pieChart.setDonutEnabled(true);
				pieChart.setShowPrecentageEnabled(true);
				
				pieChart.setVisible(true);
				pieChart.setWidth("100%");
				pieChart.setHeight("90%");
				
				middlePane.getChartsPane().removeAllComponents();
				middlePane.getChartsPane().addComponent(pieChart);
			}
			private void displayDataOnTable()
			{       middlePane.getChartsPane().removeAllComponents();middlePane.getChartsPane().addComponent(middlePane.getChartsPane().getViewerUI());}
			private void displayManageData()
			{}
			private void displayUserInfo()
			{}
			private LineChart<String, Integer> lineChart;
}