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
public class OriginDataSet {
    private final String label;
    private final List<Integer> data;
    private final String backgroundColor;
    private final String borderColor;
    private final String fill;

    /**
     * Prepares the data for the JSON String.
     *
     * @author Lukas Gollwitzer
     * @param label the label of the chart
     * @param fill fill option for the graph
     * @param data data for the graph
     * @param backgroundColor color of the graph
     * @param borderColor bordercolor for the graph
     */
    public OriginDataSet(final String label, final String fill, final String backgroundColor, final String borderColor, final List<Integer> data) {
        Ensure.that(label).isNotBlank();
        Ensure.that(fill).isNotBlank();
        Ensure.that(backgroundColor).isNotBlank();
        Ensure.that(borderColor).isNotBlank();
        Ensure.that(data).isNotNull();
        this.label = label;
        this.fill = fill;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.data = new ArrayList<>(data);
    }
}