package ru.kulakov.IKMWebApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IkmWebApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void index() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TestGetAddGalaxyAction() throws Exception {
		mockMvc.perform(get("/add/galaxy"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TestPostAddGalaxyAction() throws Exception {
		mockMvc.perform(post("/add/galaxy")
						.param("id", "0")
						.param("name", ""))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TestGetEditGalaxyAction() throws Exception {
		mockMvc.perform(get("/edit/galaxy/{0}", "0"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TestPostEditGalaxyAction() throws Exception {
		mockMvc.perform(post("/edit/galaxy/{0}", "0")
						.param("id", "0")
						.param("name", ""))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TestGetAddObjectAction() throws Exception {
		mockMvc.perform(get("/add/{0}", ""))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TestPostAddObjectAction() throws Exception {
		mockMvc.perform(post("/add/{0}", "")
						.param("id", "0")
						.param("name", "")
						.param("galaxy", """
								{
								    "id": 0,
								    "name": ""
								}""")
						.param("id", "0")
						.param("name", "")
						.param("planet", """
								{
								    "id": 0,
								    "name": "",
								    "galaxy": {
								        "id": 0,
								        "name": ""
								    }
								}""")
						.param("id", "0")
						.param("name", "")
						.param("continent", """
								{
								    "id": 0,
								    "name": "",
								    "planet": {
								        "id": 0,
								        "name": "",
								        "galaxy": {
								            "id": 0,
								            "name": ""
								        }
								    }
								}""")
						.param("id", "0")
						.param("name", "")
						.param("country", """
								{
								    "id": 0,
								    "name": "",
								    "continent": {
								        "id": 0,
								        "name": "",
								        "planet": {
								            "id": 0,
								            "name": "",
								            "galaxy": {
								                "id": 0,
								                "name": ""
								            }
								        }
								    }
								}"""))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TestGetEditObjectAction() throws Exception {
		mockMvc.perform(get("/edit/{0}/{1}", "", "0"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void testPostEditObjectAction() throws Exception {
		mockMvc.perform(post("/edit/{0}/{1}", "", "0")
						.param("id", "0")
						.param("name", "")
						.param("galaxy", """
								{
								    "id": 0,
								    "name": ""
								}""")
						.param("id", "0")
						.param("name", "")
						.param("planet", """
								{
								    "id": 0,
								    "name": "",
								    "galaxy": {
								        "id": 0,
								        "name": ""
								    }
								}""")
						.param("id", "0")
						.param("name", "")
						.param("continent", """
								{
								    "id": 0,
								    "name": "",
								    "planet": {
								        "id": 0,
								        "name": "",
								        "galaxy": {
								            "id": 0,
								            "name": ""
								        }
								    }
								}""")
						.param("id", "0")
						.param("name", "")
						.param("country", """
								{
								    "id": 0,
								    "name": "",
								    "continent": {
								        "id": 0,
								        "name": "",
								        "planet": {
								            "id": 0,
								            "name": "",
								            "galaxy": {
								                "id": 0,
								                "name": ""
								            }
								        }
								    }
								}"""))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TectDeleteObjectAction() throws Exception {
		mockMvc.perform(get("/delete/{0}/{1}", "", "0"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TectViewObjectAction() throws Exception {
		mockMvc.perform(get("/view/{0}/{1}", "", "0"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void TectConfirmAction() throws Exception {
		mockMvc.perform(get("/confirm"))
				.andExpect(status().isOk())
				.andDo(print());
	}
}
