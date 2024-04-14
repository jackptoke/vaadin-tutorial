package com.example.application.repositories;


import com.example.application.data.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "SELECT c FROM Contact c WHERE lower(c.firstName) LIKE lower(concat('%', :filterText, '%')) "
    + "OR lower(c.lastName) LIKE lower(concat('%', :filterText, '%');", nativeQuery = true)
    List<Contact> search(@Param("filterText") String filterText);
}
