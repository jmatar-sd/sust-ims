package charts;

import java.util.List;


public class LineChart<T, U> extends LineChartComponent {
	/**
	 * 
	 */
	private static final long   serialVersionUID =  4549227562743389608L;
	private static final String CHART_CLASS		 =  "ct-chart"
							   ,LINE_CHART_CLASS =  "line-chart";
	{
		init();
	}
	public LineChart(){
		
	}
	public void setLabels(List<T> labels){
		getState().labels	= labels; 
	}
	public List<T> getLabels(){
		return getState().labels;  
	}
	public void setSerises(List<List<U>> serises){
		getState().series	= serises;
	}
	public  List<List<U>> getSerises(){
		return  getState().series; 
	}
	
	public void setViewHigh(int high){
		getState().high	= high;
	}
	public int  getViewHigh(){
		return  getState().high;
	} 
	public void setViewLow(int low){  
		getState().low	= low;
	}
	public int  getViewLow(){
		return getState().low;
	}
	public void setAreaVisible(boolean isVisible){ 
		getState().showArea = isVisible;
	}
	public boolean isAreaVisible(){
		return getState().showArea;
	}
	public void setLineVisible(boolean isVisible){
		getState().showLine = isVisible;
	}
	public boolean isLineVisible(){
		return getState().showLine;
	}
	public void setPointVisible(boolean isVisible){
		getState().showPoint = isVisible;
	}
	public boolean isPointVisible(){
		return getState().showPoint;
	}
	public void setViewWidthFull(boolean isWidthFull){
		getState().fullWidth	= isWidthFull;
	} 
	public boolean isViewWidthFull(){
		return getState().fullWidth; 
	}
	public void setLabelVisible(boolean isLabelVisible){
		getState().showLabel	= isLabelVisible;
	} 
	public boolean isLabelVisible(){
		return getState().showLabel;
	}
	public void setGridVisible(boolean isGridVisible){
		getState().showGrid	= isGridVisible;
	}
	public boolean  isGridVisible(){
		return getState().showGrid;
	}
	public void setAnimationEnabled(boolean isEnabled){
		getState().animationEnabled	= isEnabled;
	}
	public boolean isAnimationEnabled(){
		return getState().animationEnabled;
	}
	public void setAnimationDelays(int animationDelays){
		getState().animationDelays	= animationDelays;
	}
	public int getAnimationDelays(){
		return getState().animationDelays;
	}
	public void setAnimationDurations(int animationDurations){
		getState().animationDurations	= animationDurations;
	}
	public int getAnimationDurations(){
		return getState().animationDurations;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LineChartState<T, U>	getState(){
		return (LineChartState<T, U>) super.getState();
	}
	private void init(){
		setStyleName(CHART_CLASS);
		addStyleName(LINE_CHART_CLASS);
	}	
}
