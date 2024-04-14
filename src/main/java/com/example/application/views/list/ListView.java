package com.example.application.views.list;

import com.example.application.data.Contact;
import com.example.application.services.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@PageTitle("Contacts")
@Route(value = "")
public class ListView extends VerticalLayout {
    private H2 header;
    private TextField filterField;
    private Button addButton;
    private Grid<Contact> contactGrid;
    private ContactForm contactForm;
    private final CrmService crmService;

    public ListView(CrmService crmService) {
        this.crmService = crmService;
        contactGrid = new Grid<>(Contact.class, false);

        // Setting
        addClassNames("list-view");
        setSizeFull();

        configureContactGrid();
        configureContactForm();

        add(
                getToolbar(),
                getContent()
        );

        updateContactList();

        closeEditor();
    }

    private void closeEditor() {
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateContactList() {
        contactGrid.setItems(crmService.findAllContacts(filterField.getValue()));
    }

    private Component getContent() {
        HorizontalLayout contentLayout = new HorizontalLayout(contactGrid, contactForm);
        contentLayout.setFlexGrow(2, contactGrid);
        contentLayout.setFlexGrow(1, contactForm);
        contentLayout.addClassNames("content");
        contentLayout.setSizeFull();
        return contentLayout;
    }

    private void configureContactForm() {
        contactForm = new ContactForm(crmService.findAllCompanies(), crmService.findAllStatuses());
        contactForm.setWidth("25em");
    }

    private Component getToolbar() {
        filterField = new TextField("Filter");
        filterField.setPlaceholder("Filter by name");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);

        filterField.addValueChangeListener(e -> {
           // do something
            updateContactList();
        });

        addButton = new Button("Filter");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        horizontalLayout.add(filterField, addButton);

        return horizontalLayout;
    }

    private void configureContactGrid() {
        contactGrid.addClassNames("contact-grid");
        contactGrid.setSizeFull();

        contactGrid.addColumn(Contact::getFirstName).setHeader("First Name");
        contactGrid.addColumn(Contact::getLastName).setHeader("Last Name");
        contactGrid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        contactGrid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        contactGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        contactGrid.addSelectionListener(selection -> {
            Optional<Contact> optionalContact = selection.getFirstSelectedItem();
            if(optionalContact.isPresent()){
                Contact contact = optionalContact.get();
//                Notification.show(contact.getFirstName() + " " + contact.getLastName() + " selected.");
                editContact(contact);
            }
            else {
                closeEditor();
            }
        });
    }

    private void editContact(Contact contact) {
        contactForm.setContact(contact);
        contactForm.setVisible(true);
        addClassNames("editing");
    }

}
