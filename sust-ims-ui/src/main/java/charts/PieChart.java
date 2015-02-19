package charts;

import java.util.List;

public class PieChart<T, U> extends PieChartComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8804166587358978074L;
	public PieChart(){
		
	}
	
	private static final String CHART_CLASS		 =  "ct-chart"
			   				   ,PIE_CHART_CLASS	 =  "pie-chart";
	{
		init();	
	}
	private void init(){
		setStyleName(PIE_CHART_CLASS);
		addStyleName(CHART_CLASS);	
	}
	@SuppressWarnings("unchecked")
	@Override
	public PieChartState<T, U> getState(){
		return (PieChartState<T, U>) super.getState();
	}
	public void setLabels(List<T> labels){
		getState().labels	= labels; 
	}
	public List<T> getLabels(){
		return getState().labels;  
	}
	public void setSerises(List<U> serises){
		getState().series	= serises;
	}
	public  List<U> getSerises(){
		return  getState().series; 
	}
	public void setShowPrecentageEnabled(boolean showPrecentageEnabled){
		getState().showPrecentage	= showPrecentageEnabled;
	}
	public boolean isShowPrecentageEnabled(){
		return getState().showPrecentage;
	}
	public void setChartLabelsVisible(boolean showLabel){
		getState().showLabel	= showLabel;
	}
	public boolean isChartLabelsVisible(){
		return	getState().showLabel;
	}
	public void setDonutEnabled(boolean isEnabled){
		getState().donut	= isEnabled;
	}
	public boolean	isDonutEnabled(){
		return getState().donut;
	}
	public void setDonutWidth(int donutWidth){
		getState().donutWidth	= donutWidth;
	}
	public int	getDonutWidth(){
		return	getState().donutWidth;
	}
	public void setStartAngle(int startAngle){
		getState().startAngle	= startAngle;
	}
	public int  getStartAngle(){
		return  getState().startAngle;
	}
	public void setTotal(int total){
		getState().total	= total;
	}
	public int	getTotal(){
		return	getState().total;
	}
	
}