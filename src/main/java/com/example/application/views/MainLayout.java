package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.dashboard.DashboardView;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;
    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        H1 logo = new H1("Header");
        logo.addClassNames("text-l", "m-m");

        Button logoutButton = new Button("Logout", e -> securityService.logout());

        HorizontalLayout headerLayout = new HorizontalLayout(new DrawerToggle(), logo, logoutButton);
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerLayout.expand(logo);
        headerLayout.setWidthFull();
        headerLayout.addClassNames("py-0", "px-m");

        addToNavbar(headerLayout);
    }

    private void createHeader() {
        RouterLink listLink = new RouterLink("List", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        dashboardLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(listLink, dashboardLink));
    }
}
