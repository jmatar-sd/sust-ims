package charts;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class BarChartState<T, U> extends JavaScriptComponentState{

	/**
	 *   
	 */  
	private static final long serialVersionUID = 8038188992042585750L;
	public List<List<U>>   series					= new ArrayList<>();  
	public List<T>		   labels					= new ArrayList<>();
	
	public int 				   high					= 100
		     				 , low			 		= 0
		     				 , scaleMinSpace		= 10 // if( become 0 ) browser frozen ! 
		     				 , axisXOffset			= 100
		     				 , axisYOffset			= 100
		     				 , seriesBarDistance    = 10;
	
	public boolean 			   centerBars 			= true
			 				 , fullWidth			= false
			 				 , showAxisYLineNumber  = true
			 				 , stackBars			= false;
	
	public String 			   axisYLabel			= "";
}