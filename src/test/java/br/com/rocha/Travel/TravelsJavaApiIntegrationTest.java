package br.com.rocha.Travel;

import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.h2.store.fs.FilePath.get;
import static org.h2.store.fs.FileUtils.delete;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TravelsJavaApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
	@Order(1)
	public void contextLoad() {
		assertNotNull(mockMvc);
	}

	@Test
	@Order(2)
	public void shouldReturnCreateTravel() throws Exception {

		JSONObject mapToCreate = setObjectToCreate();
		this.mockMvc.perform(post("/api-travels/travels").contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(new ObjectMapper().writeValueAsString(mapToCreate))).andExpect(status().isCreated());
	}

	@Test
	@Order(3)
	public void shouldReturnUpdateTravel() throws Exception {

		JSONObject mapToUpdate = setObjectToUpdate();
		this.mockMvc.perform(put("/api-travels/travels/1").contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(new ObjectMapper().writeValueAsString(mapToUpdate))).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void shouldReturnGetAllTravels() throws Exception {
		this.mockMvc.perform(get("/api-travels/travels")).andExpect(status().isOk());
	}

	@Test
	@Order(5)
	public void shouldReturnRemoveAllTravels() throws Exception {
		this.mockMvc.perform(delete("/api-travels/travels")).andExpect(status().isNoContent());
	}

	@SuppressWarnings("unchecked")
	private JSONObject setObjectToCreate() {

		String startDate = "2019-11-21T09:59:51.312Z";
		String endDate = "2019-12-01T21:08:45.202Z";

		JSONObject map = new JSONObject();
		map.put("id", 1);
		map.put("orderNumber", "220788");
		map.put("amount", "22.88");
		map.put("type", TravelTypeEnum.RETURN.getValue());
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return map;
	}

	@SuppressWarnings("unchecked")
	private JSONObject setObjectToUpdate() {

		String startDate = "2019-11-21T09:59:51.312Z";

		JSONObject map = new JSONObject();
		map.put("id", 1L);
		map.put("orderNumber", "220788");
		map.put("amount", "22.88");
		map.put("type", TravelTypeEnum.ONE_WAY.getValue());
		map.put("startDate", startDate);

		return map;
	}

}