package net.mikeharris.datastash.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.mikeharris.datastash.model.SimpleDocument;
import net.mikeharris.datastash.persistence.SimpleDocumentService;

@RestController
public class SimpleDocumentController {

	@Autowired
	SimpleDocumentService simpleDocumentService;

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public List<SimpleDocument> getDocuments() {
		List<SimpleDocument> results = new ArrayList<>();
		simpleDocumentService.listDocuments().forEach(results::add);
		return results;
	}

	@RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
	public SimpleDocument getSingleDocument(@PathVariable Long id) {

		return simpleDocumentService.findSimpleDocumentById(id);

	}

	@RequestMapping(value = "/documents", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Map<String, Object> postDocument(@RequestBody SimpleDocument simpleDocument) {

		Map<String, Object> response = new HashMap<>();
		response.put("created",true );
		response.put("id", simpleDocumentService.persistSimpleDocument(simpleDocument).getId() );
		
		return response;
				
	}

}
