package edu.hm.hafner.java.uc;

import edu.hm.hafner.util.Ensure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stefan Spirkl
 * Provides dataset for the JS view
 */

public class PriorityDistribution {
    private final List<String> labels;
    private final List<DatasetByPriorities> datasets;

    /**
     * Constructs a new PriorityDistribution Object
     * @author Stefan Spirkl
     *
     * @param labels name of the chart
     * @param datasets data input for the chart
     */

    public PriorityDistribution(final List<String> labels, final List<DatasetByPriorities> datasets) {
        Ensure.that(labels).isNotNull();
        Ensure.that(datasets).isNotNull();
        this.labels = new ArrayList<>(labels);
        this.datasets = new ArrayList<>(datasets);
    }


}
