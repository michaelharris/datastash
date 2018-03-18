package net.mikeharris.datastash;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.mikeharris.datastash.controller.SimpleDocumentController;
import net.mikeharris.datastash.model.SimpleDocument;
import net.mikeharris.datastash.persistence.SimpleDocumentService;

@ActiveProfiles("embeddedDB")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring-mvc-context.xml",
		"file:src/main/webapp/WEB-INF/spring-core-context.xml" })


public class SimpleDocumentTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	
	@Autowired
	SimpleDocumentService simpleDocumentService;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		

	}

	@Autowired
	private SimpleDocumentController controller;

	@Test
	public void contexLoads() throws Exception {
		Assert.assertNotNull(controller);

	}

	@Test
	public void testSimpleStatus() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.applicationStatus").value("RUNNING")).andReturn();

		Assert.assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
	}

	@Test
	public void testPostAndRetrieveSimpleDocument() throws Exception {
		SimpleDocument simpleDocument = new SimpleDocument();

		simpleDocument.setContent("Test content for a simple document");
		simpleDocument.setDescription("Description for the simple document");
		simpleDocument.setOrigin("Test");
		simpleDocument.setOriginType("Test Case Type");
		simpleDocument.setPublished(new Date());

		MvcResult mvcResult = this.mockMvc
				.perform(post("/documents").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(convertObjectToJsonBytes(simpleDocument)))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		System.out.println(mvcResult.getResponse().toString());

		MvcResult mvcResult2 = this.mockMvc.perform(get("/documents")).andExpect(status().isOk())
				.andExpect(jsonPath("$[3].content").value("Test content for a simple document")).andReturn();

		Assert.assertEquals("application/json;charset=UTF-8", mvcResult2.getResponse().getContentType());

		MvcResult mvcResult3 = this.mockMvc.perform(get("/documents/4")).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Test content for a simple document")).andReturn();

		Assert.assertEquals("application/json;charset=UTF-8", mvcResult3.getResponse().getContentType());
	}
	

	
	@Test
	public void simpleDocumentCreationTest(){
		
		SimpleDocument simpleDocument = new SimpleDocument();
		simpleDocument.setContent("Some test");
		simpleDocument.setOrigin("Test Case");
		simpleDocument.setOriginType("Test Case Type");
		simpleDocument.setDescription("Description for the simple document");
		assertNotNull(simpleDocumentService);
		
		simpleDocumentService.persistSimpleDocument(simpleDocument);
		
		
	}

	private byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
