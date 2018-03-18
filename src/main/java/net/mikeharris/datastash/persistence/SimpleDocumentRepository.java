package net.mikeharris.datastash.persistence;

import org.springframework.data.repository.CrudRepository;

import net.mikeharris.datastash.model.SimpleDocument;

public interface SimpleDocumentRepository extends CrudRepository<SimpleDocument, Long> { 

}
