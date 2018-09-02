package edu.hm.hafner.java.ui;

import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.ParsingException;
import edu.hm.hafner.java.uc.IssuesService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.*;

import org.springframework.mock.web.MockMultipartFile;

/**
 * Test class for UploadController.
 * @author Lukas Gollwitzer
 *
 **/
class UploadControllerTest {


    @Test
    void upload() throws IOException {
        IssuesService issuesService = mock(IssuesService.class, RETURNS_DEEP_STUBS);
        UploadController uploadController = new UploadController(issuesService);
        MultipartFile file = mock(MultipartFile.class);

        when(issuesService.parse("checkstyle", new MockMultipartFile("file", null, null, (byte[]) null).getInputStream(), "reference")).thenReturn(new Issues<>());

        assertThat(uploadController.upload(file, "PMD", "refernce", mock(ConcurrentModel.class))).isEqualTo("details");
    }

    @Test
    void shouldThrowException() throws IOException {
        IssuesService issuesService = mock(IssuesService.class);
        MultipartFile file = mock(MultipartFile.class);

        UploadController controller = new UploadController(issuesService);

        IOException ie = new IOException();

        when(file.getInputStream()).thenThrow(ie);
        assertThatThrownBy(() -> controller.upload(file, "PMD", "reference", new ConcurrentModel())).isInstanceOf(ParsingException.class);

    }

    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowNullPointerException() {
        assertThatThrownBy(() -> new UploadController((IssuesService) null)).isInstanceOf(NullPointerException.class);
    }
}