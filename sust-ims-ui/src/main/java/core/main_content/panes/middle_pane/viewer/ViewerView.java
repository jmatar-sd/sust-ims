/*
 * 
 */
package core.main_content.panes.middle_pane.viewer;

import java.util.List;
import model.College;

/**
 *
 * @author Mohammed Matar
 */
public interface ViewerView {
    interface ViewerValueChangeListener{void valueChanged(Object obj);}
    public void addViewerValueChangeListener(ViewerValueChangeListener listener);
    public void fillFirstComboBox(List<College> colleges);
}
