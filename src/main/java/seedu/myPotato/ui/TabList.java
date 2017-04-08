package seedu.myPotato.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

//@@author A0144895N
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

    public void switchTo(String tab) {
        assert tab != null;

        int tabIndex = -1;
        for (int i = 0; i < TAB_LIST.length; i++) {
            if (tab.equals(TAB_LIST[i].toLowerCase())) {
                tabIndex = i;
                break;
            }
        }

        tabList.getSelectionModel().select(tabIndex);
    }

    public String getCurrentTab() {
        return tabList.getSelectionModel().getSelectedItem().textProperty().toString();
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
