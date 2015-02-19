
package core.main_content.panes.middle_pane.viewer;

import com.vaadin.ui.Notification;
import java.util.List;
import model.College;
import model.Office;
import model.School;

/**
 *
 * @author Mohammed Matar
 */
public class ViewerPresinter implements ViewerView.ViewerValueChangeListener{
    private static final String NO_OFFICES_MSG = "Sorry there is no offices avaliable !";
    private ViewerViewImpl view;
    private ViewerModel    model;
    public  ViewerPresinter(ViewerViewImpl view, ViewerModel model){
        this.view   = view;
        this.model  = model;
        view.addViewerValueChangeListener(this);
        view.fillFirstComboBox(model.getColleges());
    }
    @Override
    public void valueChanged(Object obj) {
        if (obj instanceof College){
            displaySchoolsOrOffices((College)obj);
            view.initMainContainer();
        }else if (obj instanceof School){
            displayOffices(((School)obj).getOfficeList());
            view.initMainContainer();
        }else if (obj instanceof Office){
            fillMainTable(((Office) obj));
        }
        
    }
    private void displaySchoolsOrOffices(College college){
        view.initSchoolContainer();
        view.initOfficeContainer();
        if(!college.getSchoolList().isEmpty()){
            view.getSchoolContainer().addAll(college.getSchoolList());
            view.getOfficeUI().setVisible(false);
            view.getSchoolUI().setVisible(true);
        }else {
            view.getSchoolUI().setVisible(false);
            displayOffices(college.getOfficeList());
        }
    }
    private void displayOffices(List<Office> offices){
        view.initOfficeContainer();
        if(!offices.isEmpty()){
        view.getOfficeContainer().addAll(offices);
        view.getOfficeUI().setVisible(true);
        }else{
            view.getOfficeUI().setVisible(false);
            Notification.show(NO_OFFICES_MSG);
        }
    }
    private void fillMainTable(Office office){
        view.initMainContainer();
        view.getMainContainer().addAll(model.getTableDataModel(office));
    }
}
