package edu.hm.hafner.java.uc;

import edu.hm.hafner.util.Ensure;

import java.util.ArrayList;
import java.util.List;

    /**
     * Dataset that contains data about the priorities, as well as style information.
     */

public class DatasetByPriorities {
    private final String label;
    private final List<Integer> data;
    private final String borderColor;
    private final String backgroundColor;

    /**
     * @param label either low, medium or high
     * @param data point values
     * @param color hex color value
     * @author Stefan Spirkl
     */
    public DatasetByPriorities(final String label, final List<Integer> data, final String color) {
        Ensure.that(label).isNotBlank();
        Ensure.that(data).isNotNull();
        Ensure.that(color).isNotBlank();
        this.label = label;
        this.data = new ArrayList<>(data);
        this.borderColor = color;
        this.backgroundColor = color;
    }

    public List<Integer> getData() {
        return data;
    }

}