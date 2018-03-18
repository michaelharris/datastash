package net.mikeharris.datastash.persistence;

import java.util.List;



import net.mikeharris.datastash.model.SimpleDocument;

public interface SimpleDocumentService {
	
	public SimpleDocument persistSimpleDocument(SimpleDocument simpleDocument);

	public Iterable<SimpleDocument> listDocuments();
	
	public SimpleDocument findSimpleDocumentById(Long Id);

}
