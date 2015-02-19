/*
 * 
 */
package core.main_content.panes.middle_pane.viewer;

import data.TableDataModel;
import db_utils.DBUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.College;
import model.Device;
import model.Office;
import model.Property;
import model.School;
import services.CollegeJpaController;

/**
 *
 * @author Mohammed Matar
 */
public class ViewerModel {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DBUtils.DEFAULT_PERSISTENCE_UNIT);
    private CollegeJpaController collegeService = new CollegeJpaController(emf);
    private College     college;
    private School      school;
    private Office      office;
    
    
    public List<TableDataModel> getTableDataModel(Office office){
        
        List<TableDataModel> data = new ArrayList<>();
        TableDataModel    tableDataModel;
        for(Device device:office.getDeviceList()){
            tableDataModel = new TableDataModel();
            
            tableDataModel.setId(device.getId());
            tableDataModel.setName(device.getName());
            for(Property property:device.getPropertyList()){
                switch(property.getName()){
                    case "cpu":{tableDataModel.setCpu(property.getValue());}break;
                        case "ram":{tableDataModel.setRam(property.getValue());}break;
                        case "hdd capacity":{tableDataModel.setHdd(property.getValue());}break;
                }
            }
            data.add(tableDataModel);
        }
        return data;
    }
    public List<College> getColleges(){
        return collegeService.findCollegeEntities();
    }
    public List<School>  getCollegeSchools(College college){
        return collegeService.findCollege(college.getId()).getSchoolList();
    }
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
    
    
}
