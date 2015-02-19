/*
 * 
 */
package core.main_content.panes.middle_pane.viewer;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import data.TableDataModel;
import java.util.ArrayList;
import java.util.List;
import model.College;
import model.Device;
import model.Office;
import model.Property;
import model.School;

/**
 *
 * @author Mohammed Matar
 */
public class ViewerViewImpl extends VerticalLayout implements ViewerView, com.vaadin.data.Property.ValueChangeListener{
    private final Object[] VISIBLE_COLUMNS = {"id", "name", "cpu", "hdd", "ram"};
    private final String[] COLUMNS_HEADERS = {"#ID", "Type", "CPU Speed", "H.D.D Capacity", "RAM"};
    
    private List<ViewerValueChangeListener> valueChangeListeners =
            new ArrayList<>();
    private BeanItemContainer<College> collegeContainer   = 
            new BeanItemContainer<>(College.class);
    private BeanItemContainer<School>  schoolContainer    = 
            new BeanItemContainer<>(School.class);
    private BeanItemContainer<Office>  officeContainer    =
            new BeanItemContainer<>(Office.class);
    private BeanItemContainer<TableDataModel> mainContainer = 
            new BeanItemContainer<>(TableDataModel.class);
    {
        initRoot();
    }
    private void initRoot()
    {
        initComponents();
    }
    private void initComponents()
    {initComboBoxes();initMainTable();}
    @Override
    public void addViewerValueChangeListener(ViewerValueChangeListener listener) {
        valueChangeListeners.add(listener);
    }
    @Override
    public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
        for(ViewerValueChangeListener listener:valueChangeListeners){
            listener.valueChanged(event.getProperty().getValue());
        }
    }
    @Override
    public void fillFirstComboBox(List<College> colleges){
        collegeContainer.addAll(colleges);
    }
    private ComboBox collegeUI, schoolUI, officeUI;
    private void initComboBoxes(){
        HorizontalLayout parent = new HorizontalLayout();
        
        
        collegeUI = new ComboBox("select college : ", collegeContainer);
        schoolUI  = new ComboBox("select school : " , schoolContainer);
        officeUI  = new ComboBox("select office : " , officeContainer);
        
        
        collegeUI.addValueChangeListener(this);
        schoolUI. addValueChangeListener(this);
        officeUI. addValueChangeListener(this);
        
        collegeUI.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        collegeUI.setItemCaptionPropertyId("name");
        
        
        schoolUI.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        schoolUI.setItemCaptionPropertyId("name");
        
        officeUI.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        officeUI.setItemCaptionPropertyId("name");
        
        collegeUI.setVisible(true);
        schoolUI.setVisible(false);
        officeUI.setVisible(false);
        
        collegeUI.setNullSelectionAllowed(false);
        schoolUI.setNullSelectionAllowed(false);
        officeUI.setNullSelectionAllowed(false);
        
        parent.addComponents(collegeUI, schoolUI, officeUI);
        addComponent(parent);
        
    }
    private Table mainTable;
    private void initMainTable(){
        mainTable   = new Table("", mainContainer);
        mainTable.setVisibleColumns(VISIBLE_COLUMNS);
        
        mainTable.setColumnHeaders(COLUMNS_HEADERS);
        addComponent(mainTable);
    }
    
    public void initMainContainer(){
        mainContainer.removeAllItems();
    }
    public BeanItemContainer<College> getCollegeContainer() {
        return collegeContainer;
    }

    public void setCollegeContainer(BeanItemContainer<College> collegeContainer) {
        this.collegeContainer = collegeContainer;
    }

    public BeanItemContainer<School> getSchoolContainer() {
        return schoolContainer;
    }

    public void setSchoolContainer(BeanItemContainer<School> schoolContainer) {
        this.schoolContainer = schoolContainer;
    }

    public ComboBox getCollegeUI() {
        return collegeUI;
    }

    public void setCollegeUI(ComboBox collegeUI) {
        this.collegeUI = collegeUI;
    }

    public ComboBox getSchoolUI() {
        return schoolUI;
    }

    public void setSchoolUI(ComboBox schoolUI) {
        this.schoolUI = schoolUI;
    }

    public ComboBox getOfficeUI() {
        return officeUI;
    }

    public void setOfficeUI(ComboBox officeUI) {
        this.officeUI = officeUI;
    }

    public BeanItemContainer<Office> getOfficeContainer() {
        return officeContainer;
    }

    public void setOfficeContainer(BeanItemContainer<Office> officeContainer) {
        this.officeContainer = officeContainer;
    }
    
    public void initOfficeContainer(){
        officeUI.select(null);
        officeContainer.removeAllItems();
    }
    public void initSchoolContainer(){
        schoolUI.select(null);
        schoolContainer.removeAllItems();
    }
    public void initCollegeContainer(){
        collegeUI.select(null);
        collegeContainer.removeAllItems();
    }

    public BeanItemContainer<TableDataModel> getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(BeanItemContainer<TableDataModel> mainContainer) {
        this.mainContainer = mainContainer;
    }

    public Table getMainTable() {
        return mainTable;
    }

    public void setMainTable(Table mainTable) {
        this.mainTable = mainTable;
    }
    
}
