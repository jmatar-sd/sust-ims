package charts;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class LineChartState<T, U> extends JavaScriptComponentState{
	/**
	 * 
	 */
	private static final long serialVersionUID 		= 9019066179921418098L;
	
	public List<List<U>>   series					= new ArrayList<>(); 
	public List<T>		   labels					= new ArrayList<>();
	
	public int 				   high					= 100
						     , low			 		= 0
						     
							 , animationDelays		= 80
							 , animationDurations	= 500;
	
	
	public boolean 			   showArea 			= false
							 , animationEnabled 	= false
							 , fullWidth			= false
							 , showLine 			= true
							 , showPoint			= true
							 , showLabel			= true
							 , showGrid 	    	= true;
							 
	
}
