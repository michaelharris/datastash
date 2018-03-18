package net.mikeharris.datastash.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	/**
	 * Handling GET request to retrieve details from MANIFEST.MF file
	 * 
	 * @return implementation details
	 */
	@RequestMapping(value ="/version", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getBuildNumber(HttpServletRequest request) throws IOException {

		ServletContext context = request.getSession().getServletContext();
		InputStream manifestStream = context.getResourceAsStream("/META-INF/MANIFEST.MF");

		Manifest manifest = new Manifest(manifestStream);

		Map<String, String> response = new HashMap<>();

		response.put("Implementation-Title", manifest.getMainAttributes().getValue("Implementation-Title"));
		response.put("Implementation-Version", manifest.getMainAttributes().getValue("Implementation-Version"));
		response.put("Implementation-Build-Time", manifest.getMainAttributes().getValue("Implementation-Build-Time"));

		return response;

	}
	
	@RequestMapping(value ="/", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getSimpleStatus(HttpServletRequest request) throws IOException {

		Map<String, String> response = new HashMap<>();
		response.put("applicationStatus", "RUNNING");
		
		return response;

	}

}