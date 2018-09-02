package edu.hm.hafner.java.uc;

import edu.hm.hafner.util.Ensure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukas Gollwitzer
 */
public class OriginDistribution {
    private final List<String> labels;
    private final List<OriginDataSet> datasets;

    /**
     * @author Lukas Gollwitzer
     *
     * @param datasets data input for distribution graph
     * @param labels labels of the chart
     */
    public OriginDistribution(final List<String> labels, final List<OriginDataSet> datasets) {
        Ensure.that(labels).isNotNull();
        Ensure.that(datasets).isNotNull();
        this.labels = new ArrayList<>(labels);
        this.datasets = new ArrayList<>(datasets);
    }
}
