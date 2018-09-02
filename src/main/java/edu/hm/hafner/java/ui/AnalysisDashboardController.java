package edu.hm.hafner.java.ui;

import edu.hm.hafner.util.Ensure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.hm.hafner.java.uc.IssuesService;

import java.util.Map;


/**
 * Entry point for all direct web requests. Refer to {@link IssuesDetailController} in order to see the Ajax entry
 * points.
 *
 * @author Ullrich Hafner
 */
@Controller
public class AnalysisDashboardController {
    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private final IssuesService issuesService;

    /**
     * Creates a new instance of {@link AnalysisDashboardController}.
     *
     * @param issuesService
     *         service to access the service layer
     * @author Lukas Gollwitzer
     */
    @Autowired
    public AnalysisDashboardController(final IssuesService issuesService) {
        Ensure.that(issuesService).isNotNull();
        this.issuesService = issuesService;
    }

    /**
     * Returns the main page, served as "index.html".

     /**
     * Creates a set of issues. Reads the issues from the specified PMD file.
     * @author Lukas Gollwitzer
     * @author Stefan Spirkl
     * @return the main page
     */
    @RequestMapping("/")
    String index() {
        return "index";
    }

    @RequestMapping("/spirkl")
    String spirkl() {
        return "spirkl";
    }
    
    @RequestMapping("/gollwitzer")
    String gollwitzer() {
        return "gollwitzer";
    }


    /**
     * Shows the details for one static analysis run.
     * @author Stefan Spirkl
     * @return the URL for the details page
     */
    @RequestMapping("/details")
    String createDetails(@RequestParam("origin") final String origin,
            @RequestParam("reference") final String reference, final Model model) {
        model.addAttribute("origin", origin);
        model.addAttribute("reference", reference);

        return "details";
    }

    /**
     * Shows a table with the uploaded reports.
     * @author Stefan Spirkl
     * @return the URL for the reports statistics page
     */
    @RequestMapping("/issues")
    String createIssues() {
        return "issues";
    }

    /**
     * Shows a form to upload a new report.
     * @author Stefan Spirkl
     * @return the URL for the upload page
     */
    @RequestMapping("/upload")
    String createUpload(final Model model) {
        model.addAttribute("tools", issuesService.findAllTools());
        return "upload";
    }
}

