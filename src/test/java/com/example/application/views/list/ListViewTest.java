package com.example.application.views.list;

import com.example.application.data.Contact;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {
    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Contact> contactGrid = listView.contactGrid;
        Contact firstContact = getFirstItem(contactGrid);

        ContactForm form = listView.contactForm;
        assertFalse(form.isVisible());
        contactGrid.asSingleSelect().setValue(firstContact);

        assertTrue(form.isVisible());
        assertEquals(firstContact.getFirstName(), form.firstName.getValue());
    }

    private Contact getFirstItem(Grid<Contact> contactGrid) {
        Contact contact = new Contact();
        return ((ListDataProvider<Contact>)contactGrid.getDataProvider()).getItems().iterator().next();
    }


}
