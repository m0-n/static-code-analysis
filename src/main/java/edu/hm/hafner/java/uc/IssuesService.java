package edu.hm.hafner.java.uc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import edu.hm.hafner.util.Ensure;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import edu.hm.hafner.analysis.AbstractParser;
import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.ParsingException;
import edu.hm.hafner.analysis.parser.checkstyle.CheckStyleParser;
import edu.hm.hafner.analysis.parser.pmd.PmdParser;
import edu.hm.hafner.java.db.IssuesEntityService;
import edu.hm.hafner.java.db.IssuesTableGateway;
import edu.hm.hafner.util.NoSuchElementException;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.TreeMap;

/**
 * Provides services for {@link Issues}.
 *
 * @author Ullrich Hafner
 */
@Service
public class IssuesService {
    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private final IssuesEntityService issuesEntityService;

    /**
     * Creates a new instance of {@link IssuesService}.
     *
     * @param issuesEntityService
     *         service to access the DB layer
     */
    @Autowired
    public IssuesService(final IssuesEntityService issuesEntityService) {
        Ensure.that(issuesEntityService).isNotNull();
        this.issuesEntityService = issuesEntityService;
    }

    /**
     * Returns the number of issues per category.
     *
     * @param origin
     *         the origin of the issues instance to show the details for
     * @param reference
     *         the reference of the issues instance to show the details for
     * @author Stefan Spirkl
     * @return number of issues per category
     */
    public IssuePropertyDistribution createDistributionByCategory(final String origin, final String reference) {
        Ensure.that(origin).isNotBlank();
        Ensure.that(reference).isNotBlank();
        Issues<?> issues = issuesEntityService.findByPrimaryKey(origin, reference);
        Map<String, Integer> counts = issues.getPropertyCount(Issue::getCategory);

        return new IssuePropertyDistribution(counts);
    }

    /* get number of issues by type
     * @author Stefan Spirkl */

    public IssuePropertyDistribution createDistributionByType(final String id) {
        Ensure.that(id).isNotBlank();
        Issues<Issue> issues = IssuesTableGateway.findByPrimaryKey(id);
        Map<String, Integer> counts = issues.getPropertyCount(Issue::getType);
        return new IssuePropertyDistribution(counts);
    }


        /**
         * Returns the number of issues per type.
         *
         * @param origin
         *         the origin of the issues instance to show the details for
         * @param reference
         *         the reference of the issues instance to show the details for
         * @author Stefan Spirkl
         * @return number of issues per type
        **/
    public IssuePropertyDistribution createDistributionByPackage(final String origin, final String reference) {
        Ensure.that(origin).isNotBlank();
        Ensure.that(reference).isNotBlank();
        Issues<Issue> issues = issuesEntityService.findByPrimaryKey(origin, reference);
        Map<String, Integer> counts = issues.getPropertyCount(Issue::getPackageName);

        return new IssuePropertyDistribution(counts);
    }

    /**
     * Returns the number of issues per type.
     *
     * @param origin
     *         the origin of the issues instance to show the details for
     * @param reference
     *         the reference of the issues instance to show the details for
     * @author Stefan Spirkl
     * @return number of issues per type
     */
    public IssuePropertyDistribution createDistributionByType(final String origin, final String reference) {
        Ensure.that(origin).isNotBlank();
        Ensure.that(reference).isNotBlank();
        Issues<?> issues = issuesEntityService.findByPrimaryKey(origin, reference);
        Map<String, Integer> counts = issues.getPropertyCount(Issue::getType);


        return new IssuePropertyDistribution(counts);
    }

    /**
     * @author Stefan Spirkl
     *
     * @param origin origin of the issue
     * @param reference  reference of the issue
     *
     * @return issues by filename
     **/
    public IssuePropertyDistribution createDistributionByFiles(final String origin, final String reference) {
        Ensure.that(origin).isNotBlank();
        Ensure.that(reference).isNotBlank();
        Issues<Issue> issues = issuesEntityService.findByPrimaryKey(origin, reference);
        Map<String, Integer> counts = issues.getPropertyCount(Issue::getFileName);

        return new IssuePropertyDistribution(counts);
    }

    // priorities chart
    /**
     * @author Stefan Spirkl
     *
     * @param origin origin of the issue
     * @param reference  reference of the issue
     *
     * @return array with issues ordered by priority
     **/
    public int[] createDistributionByPriorities(final String origin, final String reference) {
        Ensure.that(origin).isNotBlank();
        Ensure.that(reference).isNotBlank();
        Issues<Issue> issues = issuesEntityService.findByPrimaryKey(origin, reference);
        int[] numberOfPrioritiesByType = new int[] {
                issues.getHighPrioritySize(),
                issues.getNormalPrioritySize(),
                issues.getLowPrioritySize()
        };
        return numberOfPrioritiesByType;
    }

    /** Warnings by Origins Chart
     *
     * @author Lukas Gollwitzer
     * @return  Dataset with origins
     */
    public OriginDistribution createDistributionByOrigins() {
        Set<Issues<Issue>> reports = issuesEntityService.findAll();
        List<String> labels = new ArrayList<>();
        List<OriginDataSet> warningsByOrigin = new ArrayList<>();
        Map<String, Map<String, Integer>> entries = new TreeMap<>();
        List<Integer> pmdCount = new ArrayList<>();
        List<Integer> csCount = new ArrayList<>();

        for(Issues report : reports) {
            if(!entries.containsKey(report.getReference())) {
                entries.put(report.getReference(), new TreeMap<>());
            }
            entries.get(report.getReference()).put(report.getOrigin(), report.size());
        }

        for (Map.Entry<String, Map<String, Integer>> entry : entries.entrySet()) {
            labels.add("#" + entry.getKey());
            pmdCount.add(entry.getValue().get("pmd"));
            csCount.add(entry.getValue().get("checkstyle"));
        }

        warningsByOrigin.add(new OriginDataSet("PMD", "false","#f6b5bb", "#f6b5bb", pmdCount));
        warningsByOrigin.add(new OriginDataSet("CheckStyle", "false", "#f7dd9e", "#f7dd9e", csCount ));

        return new OriginDistribution(labels, warningsByOrigin);
    }


    /** Warnings by Priorities Chart
     *
     * @author Stefan Spirkl
     * @return  Dataset with priorities
     */
    public PriorityDistribution createDistributionByPriority(){
        Set<Issues<Issue>> reports = issuesEntityService.findAll();
        List<String> labels = new ArrayList<>();
        List<DatasetByPriorities> listOfAllPriorities = new ArrayList<>();
        Map<String, Map<String, Integer>> elements = new TreeMap<>();
        List<Integer> low = new ArrayList<>();
        List<Integer> normal = new ArrayList<>();
        List<Integer> high = new ArrayList<>();

        for (Issues report : reports) {
            if (!elements.containsKey(report.getReference())) {
                elements.put(report.getReference(), new TreeMap<>());
            }
            elements.get(report.getReference()).put("Low", report.getLowPrioritySize());
            elements.get(report.getReference()).put("Normal", report.getNormalPrioritySize());
            elements.get(report.getReference()).put("High", report.getHighPrioritySize());
        }
        for (Entry<String, Map<String, Integer>> element : elements.entrySet()) {
            labels.add("#" + element.getKey());
            low.add(element.getValue().get("Low"));
            normal.add(element.getValue().get("Normal"));
            high.add(element.getValue().get("High"));
        }

        listOfAllPriorities.add(new DatasetByPriorities("Low", low, "#a4d2fc"));
        listOfAllPriorities.add(new DatasetByPriorities("Normal", normal, "#f7dd9e"));
        listOfAllPriorities.add(new DatasetByPriorities("High", high, "#f6b5bb"));

        return new PriorityDistribution(labels, listOfAllPriorities);
    }


    /**
     * Returns the available static analysis tools.
     * @author Stefan Spirkl
     * @return all tools
     */
    public List<AnalysisTool> findAllTools() {
        List<AnalysisTool> tools = new ArrayList<>();
        tools.add(new AnalysisTool("checkstyle", "CheckStyle", new CheckStyleParser()));
        tools.add(new AnalysisTool("pmd", "PMD", new PmdParser()));
        return tools;
    }

    /**
     * Parses the specified file with the parser with the given ID.
     *
     * @param id
     *         id of the static analysis tool
     * @param file
     *         the file to parse
     * @param reference
     *         the reference for this report
     * @author Stefan Spirkl
     * @return the issues of the specified report
     */
    public Issues<Issue> parse(final String id, final InputStream file, final String reference) {
        Ensure.that(id).isNotBlank();
        Ensure.that(file).isNotNull();
        Ensure.that(reference).isNotBlank();

        Optional<AnalysisTool> analysisTool = findAllTools().stream()
                .filter(tool -> tool.getId().equals(id))
                .findFirst();
        try (InputStreamReader stream = asStream(file)) {
            if (analysisTool.isPresent()) {
                AnalysisTool tool = analysisTool.get();
                Issues<?> issues = tool.getParser().parse(stream);
                issues.setOrigin(tool.getId());
                issues.setReference(reference);
                return issuesEntityService.save(issues);
            }
            else {
                throw new NoSuchElementException("No such tool found with id %s.", id);
            }
        }
        catch (IOException e) {
            throw new ParsingException(e);
        }
    }

    /** @author Lukas Gollwitzer */

    private InputStreamReader asStream(final InputStream file) {
        return new InputStreamReader(new BOMInputStream(file), StandardCharsets.UTF_8);
    }

    /**
     * Creates a table with the statistics of all issues. Each row shows the statistics of one uploaded report.
     * @author Stefan Spirkl
     * @return a statistics table
     */
    public IssuesTable createIssuesStatistics() {
        Set<Issues<Issue>> reports = issuesEntityService.findAll();
        IssuesTable statistics = new IssuesTable();
        for (Issues<Issue> report : reports) {
            statistics.addRow(report);
        }
        return statistics;
    }
}
