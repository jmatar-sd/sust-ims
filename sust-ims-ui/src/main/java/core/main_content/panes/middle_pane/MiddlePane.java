package core.main_content.panes.middle_pane;

import com.vaadin.ui.HorizontalSplitPanel;
import core.main_content.panes.middle_pane.charts_pane.ChartsPane;
import core.main_content.panes.middle_pane.sust_logo.SustLogo;
import com.vaadin.server.Sizeable.Unit;

public class MiddlePane extends HorizontalSplitPanel{
	private static final String ROOT_WIDTH	   = "100%"
							   ,ROOT_HEIGHT	   = "100%";
	private static final int    SPLIT_POSITION = 60;
	{initRoot();iniComponents();}
	private void initRoot(){setWidth(ROOT_WIDTH);setHeight(ROOT_HEIGHT);}
	private void iniComponents(){
		initChartsPane();initSustLogo();
		setFirstComponent(chartsPane);setSecondComponent(sustLogo);
		setSplitPosition (SPLIT_POSITION, Unit.PERCENTAGE);setLocked(true);
	}
	private void initChartsPane(){chartsPane 	= new ChartsPane();}
	private void initSustLogo  (){sustLogo      = new SustLogo();}

	private ChartsPane 				 chartsPane;
	private SustLogo    			 sustLogo;

	public ChartsPane getChartsPane(){return chartsPane;}
}