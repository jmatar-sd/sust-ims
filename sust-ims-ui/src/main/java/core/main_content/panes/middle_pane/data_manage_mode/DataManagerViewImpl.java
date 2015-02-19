package core.main_content.panes.middle_pane.data_manage_mode;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.VerticalLayout;

public class DataManagerViewImpl extends VerticalLayout implements DataManagerView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5540344492589957549L;
	private List<DataManagerClickListener> clickListeners = 
			new ArrayList<>();	
	{initRoot();initComponents();}
	private void initRoot(){
		
	}
	private void initComponents(){
		
	}
	@Override
	public void addDataManagerClickListener(DataManagerClickListener listener) {
		
	}
	

}
 