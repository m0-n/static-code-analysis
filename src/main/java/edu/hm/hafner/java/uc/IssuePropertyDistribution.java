package edu.hm.hafner.java.uc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.util.Ensure;

/**
 * Model that provides the sizes of a set of {@link Issues}.
 *
 * @author Ullrich Hafner
 * @see <a href="http://www.chartjs.org/docs/latest/charts/bar.html#dataset-properties">Bar Chart Dataset</a>
 */
@SuppressWarnings({"FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"}) // Will be converted to Json
public class IssuePropertyDistribution {
    private final List<String> labels = new ArrayList<>();
    private final List<IssuesSizeList> datasets = new ArrayList<>();

    /**
     * Creates a new instance of {@link IssuePropertyDistribution}.
     * @author Lukas Gollwitzer
     * @param counts
     *         a mapping of properties to number of issues
     */
    public IssuePropertyDistribution(final Map<String, Integer> counts) {
        Ensure.that(counts).isNotNull();
        List<Integer> values = new ArrayList<>();
        for (Entry<String, Integer> entry : counts.entrySet()) {
            labels.add(entry.getKey());
            values.add(entry.getValue());
        }
        datasets.add(new IssuesSizeList(values));
    }
}
