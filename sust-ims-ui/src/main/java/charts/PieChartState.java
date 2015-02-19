package charts;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class PieChartState<T, U> extends JavaScriptComponentState{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1107412575786837142L;
	public List<U>  	   series					= new ArrayList<>();  
	public List<T>		   labels					= new ArrayList<>();
	
	public int			   donutWidth				= 60
						  ,startAngle				= 270
						  ,total					= 0
						  ;
	public boolean 		   showPrecentage			= true
						  ,showLabel				= true
						  ,donut					= true
						  ;

}
