package edu.hm.hafner.java.db;

import java.util.List;
import java.util.Set;

import edu.hm.hafner.util.Ensure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.util.NoSuchElementException;

/**
 * Service to interact with a database to save and find {@link Issues}.
 *
 * @author Ullrich Hafner
 */
@Service
public class IssuesEntityService {
    private final EntityService entityService;

    /**
     * Creates a new instance of {@link IssuesEntityService}.
     * @author Lukas Gollwitzer
     * @param entityService
     *         the entity service to use to interact with the data base
     */
    @Autowired
    public IssuesEntityService(final EntityService entityService) {
        Ensure.that(entityService).isNotNull();
        this.entityService = entityService;
    }

    /**
     * Saves the specified set of issues in the database. If there is already an instance with the specified ID then an
     * exception is thrown. Inserts all {@link Issue} entities if they are not present in the database. Returns a new
     * object with the values of the database.
     * @author Stefan Spirkl
     * @param issues
     *         to insert into the database
     *
     * @return new instance of the issues with the values of the database
     * @throws IllegalArgumentException
     *         if the ID of one of the issues already has been saved in the database
     */
    public Issues<Issue> save(final Issues<?> issues) {
        Ensure.that(issues).isNotNull();
        long duplicates = issues.stream()
                .map(Issue::getId)
                .filter(id -> entityService.select(id).isPresent())
                .count();
        if (duplicates > 0) {
            throw new IllegalArgumentException(String.format("There are %d issues already stored in the date base.",
                    duplicates));
        }
        return entityService.insert(issues);
    }

    /**
     * Finds the set of issues with the specified ID.
     *
     * @param origin
     *         of the desired issues
     * @param reference
     *         of the desired issues
     * @author Stefan Spirkl
     * @return the issues
     * @throws NoSuchElementException
     *         if the set of issues with the specified ID has not been found
     */
    public Issues<Issue> findByPrimaryKey(final String origin, final String reference) {
        Ensure.that(origin).isNotBlank();
        Ensure.that(reference).isNotBlank();

        return entityService.select(origin, reference)
                .orElseThrow(() -> new NoSuchElementException(
                        "No issues with origin %s and reference %s found.", origin, reference));
    }

    /**
     * Finds all {@link Issues} instances that are stored in the database.
     * @author Stefan Spirkl
     * @return the set of issues
     */
    public Set<Issues<Issue>> findAll() {
        return entityService.selectAllIssues();
    }

    /**
     * Returns a list of the references of all persisted reports.
     * @author Lukas Gollwitzer
     * @return list of references
     */
    public List<String> findAllReferences() {
        return entityService.findAllReferences();
    }

    /**
     * Selects all issues with the specified reference. The matching issues will be ordered by origin.
     *
     * @param reference
     *         reference of the desired issues
     * @author Stefan Spirkl
     * @return the matching ordered list of issues
     */
    public List<Issues<Issue>> findByReference(final String reference) {
        Ensure.that(reference).isNotBlank();
        return entityService.selectByReference(reference);
    }
}