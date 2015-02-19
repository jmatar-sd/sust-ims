package charts;

import java.util.List;

public class BarChart<T, U> extends BarChartComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 643854277618021434L;
	private static final String CHART_CLASS		 =  "ct-chart"
							   ,BAR_CHART_CLASS	 =  "bar-chart";
	{
		init();
	}
	private void init(){
		setStyleName(BAR_CHART_CLASS);
		addStyleName(CHART_CLASS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BarChartState<T, U> getState(){
		return (BarChartState<T, U>) super.getState();
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
	public void setViewWidthFull(boolean isWidthFull){
		getState().fullWidth	= isWidthFull;
	}
	public boolean isViewWidthFull(){
		return getState().fullWidth;
	}
	public void setScaleMinSpace(int scaleMinSpace){
		getState().scaleMinSpace	= scaleMinSpace;
	}
	public int	getScaleMinSpace(){
		return getState().scaleMinSpace;
	}
	public void setAxisXOffset(int axisXOffset){
		getState().axisXOffset	= axisXOffset;
	}
	public int getAxisXOffset(){
		return getState().axisXOffset;
	}
	public void setAxisYOffset(int axisYOffset){
		getState().axisYOffset	= axisYOffset;
	}
	public int getAxisYOffset(){
		return getState().axisYOffset;
	}
	public void setSeriesBarDistance(int seriesBarDistance){
		getState().seriesBarDistance	= seriesBarDistance;
	}
	public int getSeriesBarDistance(){
		return getState().seriesBarDistance;
	}
	public void setCenterBarsAllowed(boolean centerBarsAllowed){
		getState().centerBars	= centerBarsAllowed;
	}
	public boolean isCenterBarsAllowed(){
		return getState().centerBars;
	}
	public void setAxisYLineNumbersVisible(boolean axisYLineNumberVisible){
		getState().showAxisYLineNumber	= axisYLineNumberVisible;
	}
	public boolean isAxisYLineNumbersVisible(){
		return getState().showAxisYLineNumber;
	}
	public void setStackBarsAllowed(boolean stackBarsAllowed){
		getState().stackBars	= stackBarsAllowed;
	}
	public boolean isStackBarsAllowed(){
		return getState().stackBars;
	}
	public void setAxisYLabel(String label){
		getState().axisYLabel 	= label;
	}
	public String getAxisYLabel(){
		return getState().axisYLabel;
	}
}