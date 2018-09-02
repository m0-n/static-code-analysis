package edu.hm.hafner.java.uc;

import edu.hm.hafner.analysis.AbstractParser;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.util.Ensure;

/**
 * Static analysis tool that can report {@link Issues}.
 *
 * @author Ullrich Hafner
 */
public class AnalysisTool {
    private final String id;
    private final String name;
    private final AbstractParser<?> parser;

    /**
     * Creates a new instance of {@link AnalysisTool}.
     * @author Stefan Spirkl
     * @param id
     *         the ID of this tool
     * @param name
     *         the human readable name of this tool
     * @param parser
     *         the parser to be used to scan files of this tool
     */
    public AnalysisTool(final String id, final String name, final AbstractParser<?> parser) {
        Ensure.that(id).isNotBlank();
        Ensure.that(name).isNotBlank();
        Ensure.that(parser).isNotNull();
        this.id = id;
        this.name = name;
        this.parser = parser;
    }

    /**
     * @author Stefan Spirkl
     *
     * @return id
     **/
    public String getId() {
        return id;
    }

    /**
     * @author Stefan Spirkl
     *
     * @return name
     **/
    public String getName() {
        return name;
    }

    /**
     * @author Stefan Spirkl
     *
     * @return parser
     **/
    public AbstractParser<?> getParser() {
        return parser;
    }
}
