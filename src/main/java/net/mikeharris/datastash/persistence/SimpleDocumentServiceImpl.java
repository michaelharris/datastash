package net.mikeharris.datastash.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.mikeharris.datastash.model.SimpleDocument;

@Service
public class SimpleDocumentServiceImpl implements SimpleDocumentService {

	@Autowired
	SimpleDocumentRepository repository;

	@Override
	public SimpleDocument persistSimpleDocument(SimpleDocument simpleDocument) {

		return repository.save(simpleDocument);
	}

	@Override
	public Iterable<SimpleDocument> listDocuments() {

		return repository.findAll();
	}

	@Override
	public SimpleDocument findSimpleDocumentById(Long id) {
		return repository.findOne(id);
	}

}
