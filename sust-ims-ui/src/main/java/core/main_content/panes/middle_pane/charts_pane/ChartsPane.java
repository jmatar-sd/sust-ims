package core.main_content.panes.middle_pane.charts_pane;

import com.vaadin.ui.VerticalLayout;
import core.main_content.panes.middle_pane.viewer.ViewerModel;
import core.main_content.panes.middle_pane.viewer.ViewerPresinter;
import core.main_content.panes.middle_pane.viewer.ViewerViewImpl;

public class ChartsPane extends VerticalLayout{
	private static final String ROOT_WIDTH = "100%"
							   ,ROOT_HEIGHT= "100%";
        private ViewerViewImpl  viewerUI;
	{initRoot();iniComponents();}
	private void initRoot(){setWidth(ROOT_WIDTH);setHeight(ROOT_HEIGHT);setStyleName("charts-pane");setMargin(true);}
	private void iniComponents(){
            initViewerUI();
	}
        private   void initViewerUI(){
            viewerUI    = new ViewerViewImpl();
            ViewerModel model = new ViewerModel();
            new ViewerPresinter(viewerUI, model);
        }

        public ViewerViewImpl getViewerUI() {
            return viewerUI;
        }

        public void setViewerUI(ViewerViewImpl viewerUI) {
            this.viewerUI = viewerUI;
        }
}