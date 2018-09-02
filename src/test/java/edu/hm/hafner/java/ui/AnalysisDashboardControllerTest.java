package edu.hm.hafner.java.ui;

import edu.hm.hafner.java.uc.IssuesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for AnalysisDashboardController.
 * @author Lukas Gollwitzer
 *
 **/
class AnalysisDashboardControllerTest {
    @Test
    void index() {
        IssuesService issuesService = mock(IssuesService.class);
        AnalysisDashboardController anDaCo = new AnalysisDashboardController(issuesService);
        String result = anDaCo.index();
        assertEquals(result, "index");
    }

    @Test
    void spirkl() {
        IssuesService issuesService = mock(IssuesService.class);
        AnalysisDashboardController anDaCo = new AnalysisDashboardController(issuesService);
        String result = anDaCo.spirkl();
        assertEquals(result, "spirkl");
    }

    @Test
    void gollwitzer() {
        IssuesService issuesService = mock(IssuesService.class);
        AnalysisDashboardController anDaCo = new AnalysisDashboardController(issuesService);
        String result = anDaCo.gollwitzer();
        assertEquals(result, "gollwitzer");
    }

    @Test
    void createDetails() {
        IssuesService issuesService = mock(IssuesService.class);
        AnalysisDashboardController anDaCo = new AnalysisDashboardController(issuesService);
        ConcurrentModel model = new ConcurrentModel();

        String html = anDaCo.createDetails("origin-value", "reference-value", model);

        assertThat(model).containsEntry("origin", "origin-value");
        assertThat(model).containsEntry("reference", "reference-value");
        assertThat(html).isEqualTo("details");
    }

    @Test
    void createIssues() {
        IssuesService issuesService = mock(IssuesService.class);
        AnalysisDashboardController anDaCo = new AnalysisDashboardController(issuesService);
        String result = anDaCo.createIssues();
        assertEquals(result, "issues");
    }

    @Test
    void createUpload() {
        IssuesService issuesService = mock(IssuesService.class);
        AnalysisDashboardController anDaCo = new AnalysisDashboardController(issuesService);
        when(issuesService.findAllTools()).thenReturn(Collections.emptyList());
        ConcurrentModel model = new ConcurrentModel();
        anDaCo.createUpload(model);
        assertThat(model).containsEntry("tools", Collections.emptyList());
    }

    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new AnalysisDashboardController((IssuesService) null)).isInstanceOf(NullPointerException.class);
    }
}