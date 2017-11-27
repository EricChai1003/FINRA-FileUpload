package com.example.demo;

import com.example.demo.dao.FileUploadDAO;
import com.example.demo.entity.FileData;
import com.example.demo.service.FileUploadServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinraDemoApplicationTests {

	@Mock
	FileUploadDAO uploadDAO;

	@InjectMocks
	FileUploadServiceImpl fileUploadService;

	private FileData file = new FileData();
	private String[] paths = new String[3];

	private Integer fileId = 1;
	private String fileName = "test.txt";
	private String filePath = "testPath";
	private Long fileSize = 100000L;

	@Before
	public void init() {
		file.setFileId(fileId);
		file.setFileName(fileName);
		file.setFileName(filePath);
		file.setFileSize(fileSize);

		paths[0] = "test1.txt";
		paths[1] = "test2.txt";
		paths[2] = "test3.txt";
	}

	@Test
	public void testGetFileDetails() {
		when(uploadDAO.findByFileName(fileName)).thenReturn(file);
		assertEquals(uploadDAO.findByFileName(fileName).toString(), file.toString());
	}

	@Test
	public void testLoadAll() {
		FileUploadServiceImpl mock = Mockito.mock(FileUploadServiceImpl.class);
		when(mock.loadAll()).thenReturn(Arrays.asList(paths));
		assertThat(mock.loadAll(), hasItems("test1.txt", "test2.txt", "test3.txt"));
	}

	@Test(expected = RuntimeException.class)
	public void testUploadService() throws RuntimeException {
		MockMultipartFile mockMultipartFile = new MockMultipartFile("newFile", new byte[0]);
		fileUploadService.save(mockMultipartFile);
	}

}
