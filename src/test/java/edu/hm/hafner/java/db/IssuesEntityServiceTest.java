package edu.hm.hafner.java.db;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Issues;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link IssuesEntityService}.
 *
 * @author Ullrich Hafner
 */
class IssuesEntityServiceTest {
    @Test
    void shouldReturnIssuesOrThrowException() {
        EntityService entityService = mock(EntityService.class);
        IssuesEntityService service = new IssuesEntityService(entityService);
        Issues<Issue> issues = new Issues<>();

        when(entityService.select("origin", "reference")).thenReturn(Optional.of(issues));
        assertThat(service.findByPrimaryKey("origin", "reference")).isSameAs(issues);

        when(entityService.select(anyString(), anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findByPrimaryKey("wrong", "key"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("wrong")
                .hasMessageContaining("key");
    }

    @Test
    void shouldSaveIssues() {
        Issues<Issue> issues = new Issues<>();
        IssueBuilder builder = new IssueBuilder();
        issues.add(builder.build());
        EntityService entityService = mock(EntityService.class);
        when(entityService.insert(issues)).thenReturn(issues);
        IssuesEntityService service = new IssuesEntityService(entityService);

        Issues<Issue> savedIssues = service.save(issues);

        assertThat(savedIssues).isEqualTo(issues);
    }

    @Test
    void shouldThrowExceptionIsSameIssuesIsAlreadyStored() {
        Issues<Issue> issues = new Issues<>();
        IssueBuilder builder = new IssueBuilder();
        Issue issue = builder.build();
        issues.add(issue);
        EntityService entityService = mock(EntityService.class);
        when(entityService.insert(issues)).thenReturn(issues);
        when(entityService.select(any())).thenReturn(Optional.of(issue));

        IssuesEntityService service = new IssuesEntityService(entityService);

        assertThatThrownBy(() -> service.save(issues))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1 issues already stored");
    }

    /**
     * Test for save method.
     * @author Stefan Spirkl
     **/
    @Test
    void save() {
        EntityService entityService = mock(EntityService.class);
        IssuesEntityService myService = new IssuesEntityService(entityService);
        Issues<Issue> myIssue = new Issues<>();
        myService.save(myIssue);

        assertThat(myService); // is not null
    }

    /**
     * Test for finByPrimaryKey method.
     * @author Stefan Spirkl
     **/
    @Test
    void findByPrimaryKey() {
        EntityService entityService = mock(EntityService.class);
        IssuesEntityService myService = new IssuesEntityService(entityService);
        assertThatThrownBy(() -> myService.findByPrimaryKey("notexisting", "atall"))
                .isInstanceOf(NoSuchElementException.class);

    }

    /**
     * Test for findAll method.
     * @author Stefan Spirkl
     **/
    @Test
    void findAll() {
        EntityService entityService = mock(EntityService.class);
        IssuesEntityService myService = new IssuesEntityService(entityService);
        assertThat(myService.findAll()); // is not null
    }

    /**
     * Ensure no blank or null values.
     * @author Lukas Gollwitzer
     */
    @Test
    void shouldThrowException() {
        EntityService entityService = mock(EntityService.class);
        IssuesEntityService issuesEntityService = new IssuesEntityService(entityService);
        assertThatThrownBy(() -> new IssuesEntityService((EntityService)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesEntityService.save((Issues)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesEntityService.findByPrimaryKey(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesEntityService.findByReference(null)).isInstanceOf(NullPointerException.class);
    }
}