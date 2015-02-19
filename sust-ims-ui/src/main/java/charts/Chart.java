package charts;


public interface Chart {
	
	public void 	 setTitle(String chartTitle);
	public String 	 getTitle();
	
	public interface ChartListener{}
	public void 	 addChartListener(ChartListener listener);
}
