package edu.hm.hafner.java.ui;

import edu.hm.hafner.java.uc.*;
import edu.hm.hafner.util.Ensure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import edu.hm.hafner.analysis.Issues;

/**
 * Provides detail information for a specific set of {@link Issues}.
 *
 * @author Ullrich Hafner
 */
@Controller
public class IssuesDetailController {
    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private final IssuesService issuesService;

    /**
     * Creates a new instance of {@link IssuesDetailController}.
     * @author Lukas Gollwitzer
     * @param issuesService
     *         service to access the service layer
     */
    @Autowired
    public IssuesDetailController(final IssuesService issuesService) {
        Ensure.that(issuesService).isNotNull();
        this.issuesService = issuesService;
    }

    /**
     * Ajax entry point: returns a table with statistics of the uploaded reports (as JSON object). The returned JSON
     * object is in the expected format for the {@code data} property of a DataTable.
     * @author Stefan Spirkl
     * @return issues statistics of all uploaded reports
     */
    @RequestMapping(path = "/ajax/issues", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused")
    // called by issues.js
    ResponseEntity<?> getIssues() {
        IssuesTable model = issuesService.createIssuesStatistics();

        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }

    /**
     * Ajax entry point: returns the number of issues per category (as JSON object). The returned JSON object is in the
     * expected format for the {@code data} property of a bar chart.
     * <p>
     * Example:
     * <pre>
     *     { "labels" : ["Design","Documentation","Best Practices","Performance","Code Style","Error Prone"],
     *      "datasets" : [
     *          {"data" : [15,3,20,6,53,12]}
     *      ]
     *      }
     * </pre>
     *
     * @param origin
     *         the origin of the issues instance to show the details for
     * @param reference
     *         the reference of the issues instance to show the details for
     * @author Stefan Spirkl
     * @return the number of issues per category
     */
    @RequestMapping(path = "/ajax/categories", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused")
    // called by details.js
    ResponseEntity<?> getCategories(@RequestParam("origin") final String origin,
            @RequestParam("reference") final String reference) {
        IssuePropertyDistribution model = issuesService.createDistributionByCategory(origin, reference);

        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }

    /**
     * Ajax entry point: returns the number of issues per type (as JSON object). The returned JSON object is in the
     * expected format for the {@code data} property of a bar chart.
     *
     * @param origin
     *         the origin of the issues instance to show the details for
     * @param reference
     *         the reference of the issues instance to show the details for
     * @author Stefan Spirkl
     * @return the number of issues per type
     */
    @RequestMapping(path = "/ajax/types", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused")
    // called by details.js
    ResponseEntity<?> getTypes(@RequestParam("origin") final String origin,
            @RequestParam("reference") final String reference) {
        IssuePropertyDistribution model = issuesService.createDistributionByType(origin, reference);

        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }

    /*
    * @param id
    *         the ID of the issues instance to show the details for
    *
    * @return the number of issues per category, e.g. [10, 20, 70]
    * @author Stefan Spirkl
    * Stefans part below (distribution by package)
    *
    */
    @RequestMapping(path = "/ajax/packages", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused") // called by details.js
    ResponseEntity<?> getPackages(@RequestParam("origin") final String origin,
                                  @RequestParam("reference") final String reference) {
        IssuePropertyDistribution model = issuesService.createDistributionByPackage(origin, reference);
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }
    /**
     * Ajax entry point: returns the number of issues per category (as JSON object). The returned JSON object
     * is in the expected format for the {@code data} property of a bar chart.
     *
     * Example:
     * <pre>
     *     { "labels" : ["Design","Documentation","Best Practices","Performance","Code Style","Error Prone"],
     *      "datasets" : [
     *          {"data" : [15,3,20,6,53,12]}
     *      ]
     *      }
     * </pre>
     *
     * @param origin of the issues instance to show the details for
     * @author Lukas Gollwitzer
     * @return the number of issues per category, e.g. [10, 20, 70]
     */
    @RequestMapping(path = "/ajax/files", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused") // called by details.js
    ResponseEntity<?> getFiles(@RequestParam("origin") final String origin,
            @RequestParam("reference") final String reference) {
        IssuePropertyDistribution model = issuesService.createDistributionByFiles(origin, reference);
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }

    /** @author Lukas Gollwitzer */
    // priorities chart
    @RequestMapping(path = "/ajax/priorities", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused") // called by details.js
    ResponseEntity<?> getPriorities(@RequestParam("origin") final String origin,
                               @RequestParam("reference") final String reference) {
        int[] model = issuesService.createDistributionByPriorities(origin, reference);
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }

    /** Warnings by Origin Chart
     * @author Lukas Gollwitzer
     * @return
     */
    @RequestMapping(path = "/ajax/origins", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused") // called by issues.js
    ResponseEntity<?> getOrigins() {
        OriginDistribution model = issuesService.createDistributionByOrigins();
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }

    /** Pulls a PrioritiyDistribution model from the controller and returns it as json.
     * @author Stefan Spirkl
     * @return
     */
    @RequestMapping(path = "/ajax/prioritiesbybuild", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    @SuppressWarnings("unused") // called by issues.js
    ResponseEntity<?> getPrioritiesByBuild() {
        PriorityDistribution model = issuesService.createDistributionByPriority();
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(model));
    }
}
