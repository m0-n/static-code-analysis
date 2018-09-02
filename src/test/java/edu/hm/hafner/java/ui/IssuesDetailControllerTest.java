package edu.hm.hafner.java.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.java.uc.IssuesTable;
import org.eclipse.collections.impl.factory.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.hm.hafner.java.uc.IssuePropertyDistribution;
import edu.hm.hafner.java.uc.IssuesService;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link IssuesDetailController}.
 *
 * @author Ullrich Hafner
 */
class IssuesDetailControllerTest {
    private static final String LABEL = "label";
    private static final String ORIGIN_CATEGORY = "origin-category";
    private static final String REFERENCE_CATEGORY = "reference-category";
    private static final String ORIGIN_TYPE = "origin-type";
    private static final String REFERENCE_TYPE = "reference-type";

    private static final IssuePropertyDistribution EMPTY_DISTRIBUTION
            = new IssuePropertyDistribution(new HashMap<>());
    private static final IssuePropertyDistribution SINGLETON_DISTRIBUTION
            = new IssuePropertyDistribution(Maps.fixedSize.of(LABEL, 1));

    /**
     * Test for JSON distribution object.
     * @author Lukas Gollwitzer
     *
     **/
    @Test
    void shouldReturnJsonOfPropertiesDistribution() {
        IssuesService issuesService = mock(IssuesService.class);
        IssuesDetailController controller = new IssuesDetailController(issuesService);

        when(issuesService.createDistributionByCategory(ORIGIN_CATEGORY, REFERENCE_CATEGORY))
                .thenReturn(EMPTY_DISTRIBUTION);
        assertThatResponseIsEmpty(controller.getCategories(ORIGIN_CATEGORY, REFERENCE_CATEGORY));

        when(issuesService.createDistributionByType(ORIGIN_TYPE, REFERENCE_TYPE))
                .thenReturn(EMPTY_DISTRIBUTION);
        assertThatResponseIsEmpty(controller.getTypes(ORIGIN_TYPE, REFERENCE_TYPE));

        when(issuesService.createDistributionByFiles(ORIGIN_TYPE, REFERENCE_TYPE))
                .thenReturn(EMPTY_DISTRIBUTION);
        assertThatResponseIsEmpty(controller.getFiles(ORIGIN_TYPE, REFERENCE_TYPE));

        when(issuesService.createDistributionByPackage(ORIGIN_TYPE, REFERENCE_TYPE))
                .thenReturn(EMPTY_DISTRIBUTION);
        assertThatResponseIsEmpty(controller.getPackages(ORIGIN_TYPE, REFERENCE_TYPE));


        when(issuesService.createDistributionByCategory(ORIGIN_CATEGORY, REFERENCE_CATEGORY))
                .thenReturn(SINGLETON_DISTRIBUTION);
        assertThatResponseContainsOneElement(controller.getCategories(ORIGIN_CATEGORY, REFERENCE_CATEGORY));

        when(issuesService.createDistributionByType(ORIGIN_TYPE, REFERENCE_TYPE))
                .thenReturn(SINGLETON_DISTRIBUTION);
        assertThatResponseContainsOneElement(controller.getTypes(ORIGIN_TYPE, REFERENCE_TYPE));

        when(issuesService.createDistributionByPackage(ORIGIN_TYPE, REFERENCE_TYPE))
                .thenReturn(SINGLETON_DISTRIBUTION);
        assertThatResponseContainsOneElement(controller.getPackages(ORIGIN_TYPE, REFERENCE_TYPE));

        when(issuesService.createDistributionByFiles(ORIGIN_TYPE, REFERENCE_TYPE))
                .thenReturn(SINGLETON_DISTRIBUTION);
        assertThatResponseContainsOneElement(controller.getTypes(ORIGIN_TYPE, REFERENCE_TYPE));
    }

    @Test
    void getPriorities() {
        IssuesService issuesService = mock(IssuesService.class);
        IssuesDetailController controller = new IssuesDetailController(issuesService);
        Issues<?> issues = mock(Issues.class);

        String origin = "origin";
        String reference = "reference";

        int[] priorityCount = new int[3];
        priorityCount[0] = issues.getHighPrioritySize();
        priorityCount[1] = issues.getNormalPrioritySize();
        priorityCount[2] = issues.getLowPrioritySize();

        when(issuesService.createDistributionByPriorities(origin, reference)).thenReturn(priorityCount);
        ResponseEntity<?> responseEntity = controller.getPriorities(origin, reference);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("[0,0,0]");
    }

    @Test
    void getIssues() {
        IssuesService issuesService = mock(IssuesService.class);
        IssuesDetailController controller = new IssuesDetailController(issuesService);

        when(issuesService.createIssuesStatistics()).thenReturn(new IssuesTable());
        ResponseEntity<?> responseEntity = controller.getIssues();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("{\"data\":[]}");
    }

    private void assertThatResponseContainsOneElement(final ResponseEntity<?> categories) {
        assertThatResponseIsEqualTo(categories, "{\"labels\":[\"label\"],\"datasets\":[{\"data\":[1]}]}");
    }

    private void assertThatResponseIsEmpty(final ResponseEntity<?> empty) {
        assertThatResponseIsEqualTo(empty, "{\"labels\":[],\"datasets\":[{\"data\":[]}]}");
    }

    private void assertThatResponseIsEqualTo(final ResponseEntity<?> empty, final String s) {
        assertThat(empty.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(empty.getBody()).isEqualTo(s);
    }

    /**
     * Test for getting the origin data.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldGetOriginData() {
        IssuesService issuesService = mock(IssuesService.class);
        IssuesDetailController controller = new IssuesDetailController(issuesService);

        when(issuesService.createIssuesStatistics()).thenReturn(new IssuesTable());
        ResponseEntity<?> responseEntity = controller.getOrigins();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("null");
    }

    /**
     * Verifies http code 200 is sent in response header after a requesting view.
     * @author Stefan Spirkl
     */
    @Test
    void shouldGetPriorityData() {
        IssuesService issuesService = mock(IssuesService.class);
        IssuesDetailController controller = new IssuesDetailController(issuesService);
        when(issuesService.createIssuesStatistics()).thenReturn(new IssuesTable());
        ResponseEntity<?> responseEntity = controller.getPrioritiesByBuild();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("null");
    }

    /**
     * Ensure no blank or null values.
     * @author Stefan Spirkl
     */
    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> new IssuesDetailController((IssuesService) null)).isInstanceOf(NullPointerException.class);
    }
}