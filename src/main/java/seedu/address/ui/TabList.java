package seedu.address.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class TabList extends UiPart<Region> {

    private static final String[] TAB_LIST = { "All", "Today", "Completed" };

    private static final String FXML = "TabList.fxml";

    private ArrayList<Tab> tabs;

    @FXML
    private TabPane tabList;

    public TabList(AnchorPane tabPlaceholder) {
        super(FXML);
        tabPlaceholder.getChildren().add(tabList);
        tabs = new ArrayList<Tab>();
        initTabs();
    }

    private void initTabs() {
        for (String s : TAB_LIST) {
            tabs.add(new Tab(s));
        }
        for (Tab l : tabs) {
            tabList.getTabs().add(l);
        }
    }

}
