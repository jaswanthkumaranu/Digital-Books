package com.digitalbooks.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.digitalbooks.BooksServiceApplication;
import com.digitalbooks.model.BookVo;
import com.digitalbooks.response.MessageResponse;
import com.digitalbooks.service.BookService;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes={ BooksServiceApplication.class })
public class BookControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private BookService bookServiceMock;

	@Before
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testCreateBookWithValidRequest() throws Exception {
		BookVo book = new BookVo();
		when(bookServiceMock.createBook((BookVo) any(BookVo.class), "1")).thenReturn(new MessageResponse("Book added successfully!"));

		mockMvc.perform(post("/author/1/books").contentType(MediaType.APPLICATION_JSON)
				.content("\"bookId\": 0,\r\n" + "  \"bookTitle\": \"MAD MAX\",\r\n" + "  \"bookCode\": \"MAD\",\r\n"
						+ "  \"authorId\": \"1\",\r\n" + "  \"price\": 40,\r\n" + "  \"category\": \"Story\",\r\n"
						+ "  \"publisher\": \"MAX\",\r\n" + "  \"logo\": null,\r\n" + "  \"audiourl\": \"\",\r\n"
						+ "  \"bookContent\": \"This book has content\",\r\n" + "  \"isActive\": 1,\r\n"
						+ "  \"updatedDt\": \"2022-12-04T05:22:12.334Z\",\r\n" + "  \"crtUsr\": 1,\r\n"
						+ "  \"createdDt\": \"2022-12-04T05:22:12.334Z\",\r\n" + "  \"block\": 0,\r\n"
						+ "  \"subscriptionCount\": 0\r\n" + "}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.bookTitle").value("MAD MAX")).andExpect(jsonPath("$.price").value(40))
				.andExpect(jsonPath("$.bookCode").value("MAD"));
	}

	@Test
	public void testUpdateBook() {
//		fail("Not yet implemented");
	}

	@Test
	public void testSearchBooks() {
//		fail("Not yet implemented");
	}

	@Test
	public void testSearchBooksNew() {
//		fail("Not yet implemented");
	}

	@Test
	public void testSubscribeBook() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetAllSubscribeBooksByReader() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetSubscribeBookByReaderEmailId() {
//		fail("Not yet implemented");
	}

	@Test
	public void testGetSubscribeBookContentByReaderEmailId() {
//		fail("Not yet implemented");
	}

	@Test
	public void testCancleSubscriptionWithIn24Hours() {
//		fail("Not yet implemented");
	}

	@Test
	public void testBlockOrUnBlockBookByAuthor() {
		fail("Not yet implemented");
	}

}
