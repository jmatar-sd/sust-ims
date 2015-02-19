package core;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import core.main_content.MainContent;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.College;
import model.Device;
import model.Office;
import model.Property;
import model.School;
import services.CollegeJpaController;
import services.DeviceJpaController;
import services.OfficeJpaController;
import services.SchoolJpaController;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.jmatar.MyAppWidgetset")
public class MainApp extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initSampleData();
    	mainContent 	= new MainContent();
    	setContent(mainContent);
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainApp.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    private	MainContent 	mainContent;
    
    private void initSampleData(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sustPU");
                School school = new School();
                school.setName("electronic school");
                
                College college = new College();
                college.setName("eng college");
                
                College college2 = new College();
                college2.setName("seound college");
                
                CollegeJpaController collegeService = new CollegeJpaController(emf);
                
                collegeService.create(college);
                collegeService.create(college2);
                
                SchoolJpaController schoolService = new SchoolJpaController(emf);
                school.setCollegeID(college);
                schoolService.create(school);
                
                OfficeJpaController officeService = new OfficeJpaController(emf);
                Office office = new Office();
                
                office.setName("elec manager");
                office.setCollegeID(college2);
                officeService.create(office);
                
                Office office2 = new Office();
                office2.setName("office of school");
                office2.setSchoolID(school);
                
                officeService.create(office2);
                
                Device pc1 = new Device();
                
                pc1.setName("pc");
                pc1.setOfficeID(office);
                pc1.getPropertyList().add(new Property(1, "cpu", "core I5"));
                pc1.getPropertyList().add(new Property(2, "ram", "4G"));
                pc1.getPropertyList().add(new Property(3, "hdd capacity", "500GB"));
                
                Device pc = new Device();
                
                pc.setName("pc2");
                pc.setOfficeID(office2);
                pc.getPropertyList().add(new Property(4, "cpu", "core I7"));
                pc.getPropertyList().add(new Property(5, "ram", "8G"));
                pc.getPropertyList().add(new Property(6, "hdd capacity", "1TB"));
                
                DeviceJpaController deviceService = new DeviceJpaController(emf);
                deviceService.create(pc1);
                deviceService.create(pc);
    }
}

//private PieChart<String, Integer> 	pieChart;

// pieChart= new PieChart<>();
		
// 		pieChart.setWidth("100%");
// 		pieChart.setHeight("400px");
// 		List<Integer> series	= new ArrayList<>();
// 		List<String> labels	= new ArrayList<>();
		
// 		series.add(15);series.add(3);series.add(4);
// 		labels.add("orange");labels.add("pananna");labels.add("apple");
		
// 		pieChart.setLabels(labels);
// 		pieChart.setSerises(series);
		
		
// 		final VerticalLayout layout = new VerticalLayout();
// 		layout.setMargin(true);
// 		setContent(layout);
		


// TextField   text    = new TextField("text");

//         final VerticalLayout layout = new VerticalLayout();
//         layout.setMargin(true);
//         setContent(layout);

//         Button button = new Button("Click Me");
//         button.addClickListener(new Button.ClickListener() {
//             @Override
//             public void buttonClick(ClickEvent event) {
//                 layout.addComponent(new Label("Thank you for clicking"));
//             }
//         });
//         layout.addComponent(button);
//         layout.addComponent(text);