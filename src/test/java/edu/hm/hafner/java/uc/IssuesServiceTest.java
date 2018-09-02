package edu.hm.hafner.java.uc;

import edu.hm.hafner.analysis.AbstractParser;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.ParsingCanceledException;
import edu.hm.hafner.analysis.ParsingException;
import edu.hm.hafner.java.db.IssuesEntity;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.java.db.EntityService;
import edu.hm.hafner.java.db.IssuesEntityService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link IssuesService}.
 *
 * @author Ullrich Hafner
 */
class IssuesServiceTest {

    @Test
    void shouldCreateCategoryDistribution() {
        // Given
        IssuesService service = createService();

        // When
        IssuePropertyDistribution distribution = service.createDistributionByCategory("dummy-id", "reference");

        // Then
        assertThat(toJson(distribution)).isEqualTo("{\"labels\":[\"Design\",\"Documentation\",\"Best Practices\",\"Performance\",\"Code Style\",\"Error Prone\"],\"datasets\":[{\"data\":[15,3,20,6,53,12]}]}");
    }

    @Test
    void shouldCreateTypeDistribution() {
        // Given
        IssuesService service = createService();

        // When
        IssuePropertyDistribution distribution = service.createDistributionByType("dummy-id", "reference");

        // Then
        assertThat(toJson(distribution)).isEqualTo("{\"labels\":[\"OptimizableToArrayCall\",\"LooseCoupling\",\"MethodArgumentCouldBeFinal\",\"UncommentedEmptyMethodBody\",\"ConfusingTernary\",\"MissingSerialVersionUID\",\"GuardLogStatement\",\"UnusedFormalParameter\",\"LoggerIsNotStaticFinal\",\"AssignmentInOperand\",\"ImmutableField\",\"CompareObjectsWithEquals\",\"UnnecessaryConstructor\",\"CyclomaticComplexity\",\"UnusedPrivateMethod\",\"ConsecutiveLiteralAppends\",\"CallSuperInConstructor\",\"UnusedPrivateField\",\"AppendCharacterWithChar\",\"ExcessivePublicCount\",\"NPathComplexity\",\"ExcessiveImports\",\"AvoidDeeplyNestedIfStmts\",\"AccessorClassGeneration\",\"UncommentedEmptyConstructor\"],\"datasets\":[{\"data\":[1,1,13,2,9,4,8,2,4,1,2,3,13,3,3,4,18,1,1,2,3,4,1,5,1]}]}");
    }

    private String toJson(final Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private IssuesService createService() {
        IssuesTestData data = new IssuesTestData(mock(EntityService.class));
        IssuesEntityService entityService = mock(IssuesEntityService.class);
        Issues<Issue> issues = new Issues<>();
        issues.addAll(data.createTestData());

        when(entityService.findByPrimaryKey(anyString(), anyString())).thenReturn(issues);
        return new IssuesService(entityService);
    }

    /**
     * Test of package distribution.
     * @author Stefan Spirkl
     **/
    @Test
    void createDistributionByPackage() {
        IssuesService service = createService();
        IssuePropertyDistribution distribution = service.createDistributionByPackage("origin", "reference");
        assertThat(toJson(distribution)).isNotEqualTo("{\"labels\":[\"OptimizableToArrayCall\",\"LooseCoupling\",\"MethodArgumentCouldBeFinal\",\"UncommentedEmptyMethodBody\",\"ConfusingTernary\",\"MissingSerialVersionUID\",\"GuardLogStatement\",\"UnusedFormalParameter\",\"LoggerIsNotStaticFinal\",\"AssignmentInOperand\",\"ImmutableField\",\"CompareObjectsWithEquals\",\"UnnecessaryConstructor\",\"CyclomaticComplexity\",\"UnusedPrivateMethod\",\"ConsecutiveLiteralAppends\",\"CallSuperInConstructor\",\"UnusedPrivateField\",\"AppendCharacterWithChar\",\"ExcessivePublicCount\",\"NPathComplexity\",\"ExcessiveImports\",\"AvoidDeeplyNestedIfStmts\",\"AccessorClassGeneration\",\"UncommentedEmptyConstructor\"],\"datasets\":[{\"data\":[1,1,13,2,9,4,8,2,4,1,2,3,13,3,3,4,18,1,1,2,3,4,1,5,1]}]}");
        assertThat(toJson(distribution)).isEqualTo("{\"labels\":[\"io.jenkins.plugins.analysis.core.util\",\"io.jenkins.plugins.analysis.core.history\",\"io.jenkins.plugins.analysis.core.graphs\",\"io.jenkins.plugins.analysis.core.model\",\"io.jenkins.plugins.analysis.core.views\",\"io.jenkins.plugins.analysis.core\",\"io.jenkins.plugins.analysis.core.steps\",\"io.jenkins.plugins.analysis.core.quality\"],\"datasets\":[{\"data\":[5,1,29,33,9,1,24,7]}]}");
        assertThat(toJson(distribution)).isNotNull();
    }

    /**
     * Test of file distribution.
     * @author Stefan Spirkl
     **/
    @Test
    void createDistributionByFiles() {
        IssuesService service = createService();
        IssuePropertyDistribution distribution = service.createDistributionByFiles("not", "there");
        assertThat(toJson(distribution)).isEqualTo("{\"labels\":[\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/steps/ScanForIssuesStep.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/quality/QualityGate.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/steps/IssuesPublisher.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/IncludeModule.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/views/DetailFactory.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/steps/PublishIssuesStep.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/test/java/io/jenkins/plugins/analysis/core/graphs/SeriesBuilderTest.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/steps/IssuesRecorder.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/util/LoggerFactory.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/graphs/HealthSeriesBuilder.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/util/AffectedFilesResolver.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/ExcludeCategory.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/quality/HealthDescriptor.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/JenkinsFacade.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/StaticAnalysisTool.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/IncludeType.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/graphs/OriginSeriesBuilder.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/IncludeCategory.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/steps/ToolConfiguration.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/ExcludePackage.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/quality/ThresholdSet.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/AnalysisResult.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/graphs/AnnotationsByUserGraph.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/StaticAnalysisToolSuite.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/history/BuildHistory.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/graphs/GraphConfiguration.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/views/SourceDetail.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/graphs/NumberOnlyBuildLabel.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/graphs/GraphConfigurationView.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/test/java/io/jenkins/plugins/analysis/core/model/StaticAnalysisLabelProviderTest.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/ExcludeModule.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/views/JobAction.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/IncludeFile.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/graphs/OriginGraph.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/util/FilesScanner.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/Summary.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/ExcludeType.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/ExcludeFile.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/IncludePackage.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/test/java/io/jenkins/plugins/analysis/core/quality/QualityGateTest.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/test/java/io/jenkins/plugins/analysis/core/graphs/HealthSeriesBuilderTest.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/steps/IssuesScanner.java\",\"/Users/hafner/Development/jenkins/workspace/Core/src/main/java/io/jenkins/plugins/analysis/core/model/RegexpFilter.java\"],\"datasets\":[{\"data\":[2,4,7,2,2,3,2,5,3,1,1,2,1,1,1,2,2,2,2,2,1,4,2,1,1,11,5,3,3,1,2,2,2,4,1,5,2,2,2,1,1,5,1]}]}");
    }
    /**
     * Test of type distribution.
     * @author Stefan Spirkl
     **/
    @Test
    void createDistributionByType() {
        IssuesService service = createService();
        IssuePropertyDistribution distribution = service.createDistributionByType("1");
        assertThat(toJson(distribution)).isEqualTo("{\"labels\":[\"OptimizableToArrayCall\",\"LooseCoupling\",\"MethodArgumentCouldBeFinal\",\"UncommentedEmptyMethodBody\",\"ConfusingTernary\",\"MissingSerialVersionUID\",\"GuardLogStatement\",\"UnusedFormalParameter\",\"LoggerIsNotStaticFinal\",\"AssignmentInOperand\",\"ImmutableField\",\"CompareObjectsWithEquals\",\"UnnecessaryConstructor\",\"CyclomaticComplexity\",\"UnusedPrivateMethod\",\"ConsecutiveLiteralAppends\",\"CallSuperInConstructor\",\"UnusedPrivateField\",\"AppendCharacterWithChar\",\"ExcessivePublicCount\",\"NPathComplexity\",\"ExcessiveImports\",\"AvoidDeeplyNestedIfStmts\",\"AccessorClassGeneration\",\"UncommentedEmptyConstructor\"],\"datasets\":[{\"data\":[1,1,13,2,9,4,8,2,4,1,2,3,13,3,3,4,18,1,1,2,3,4,1,5,1]}]}");
    }

    /**
     * Test of priority distribution.
     * @author Stefan Spirkl
     **/
    @Test
    void createDistributionByPriorities() {
        IssuesService service = createService();
        int[] array = {12, 97, 0};
        int[] distribution = service.createDistributionByPriorities("origin", "nothing");
        assertThat(distribution).isEqualTo(array);
    }
    /**
     * Test for finding all Tools.
     * @author Stefan Spirkl
     **/
    @Test
    void findAllTools() {
        IssuesService service = createService();
        assertThat(service.findAllTools()).isNotNull();
    }


    /**
     * Test for parse method.
     * @author Stefan Spirkl
     **/
    @Test
    void parse() {
        IssuesService service = createService();

        assertThatThrownBy(() -> service.parse("1", new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        }, "reference")).isInstanceOf(NoSuchElementException.class);
    }
    /**
     * Test number two of parse method.
     * @author Stefan Spirkl
     **/
    @Test
    void parse2() {
        IssuesService service = createService();
        InputStream f = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        assertThatThrownBy(() -> service.parse("1", f, "reference")).isInstanceOf(NoSuchElementException.class);
    }

    /**
     * Test for IssuesStatistics.
     * @author Stefan Spirkl
     **/
    @Test
    void createIssuesStatistics() {
        createService();
        assert true; //TODO:Stefan Spirkl
    }

    /**
     * Verifies the OriginDataChart.
     * @author Lukas Gollwitzer
     **/
    @Test
    void shouldcreateOriginChartData() {
        IssuesService service = createService();
        OriginDistribution test = service.createDistributionByOrigins();
        assertThat(toJson(test)).isEqualTo("{\"labels\":[],\"datasets\":[{\"label\":\"PMD\",\"data\":[],\"backgroundColor\":\"#f6b5bb\",\"borderColor\":\"#f6b5bb\",\"fill\":\"false\"},{\"label\":\"CheckStyle\",\"data\":[],\"backgroundColor\":\"#f7dd9e\",\"borderColor\":\"#f7dd9e\",\"fill\":\"false\"}]}");
    }

    /**
     * Verifies chart object exists and correct color for each priority is used.
     * @author Stefan Spirkl
     *
     * */
    @Test
    void shouldcreatePrioritiesChartData() {
        IssuesService service = createService();
        PriorityDistribution test = service.createDistributionByPriority();
        assertThat(toJson(test)).isEqualTo(
                "{\"labels\":[],\"datasets\":[{\"label\":\"Low\",\"data\":[],\"borderColor\":\"#a4d2fc\",\"backgroundColor\":\"#a4d2fc\"},"
                        + "{\"label\":\"Normal\",\"data\":[],\"borderColor\":\"#f7dd9e\",\"backgroundColor\":\"#f7dd9e\"},"
                        + "{\"label\":\"High\",\"data\":[],\"borderColor\":\"#f6b5bb\",\"backgroundColor\":\"#f6b5bb\"}]}");
    }

    @Test
    void shouldThrowException() {
        IssuesEntityService issuesEntityService = mock(IssuesEntityService.class);
        IssuesService issuesService = new IssuesService(issuesEntityService);

        assertThatThrownBy(() -> new IssuesService((IssuesEntityService)null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesService.createDistributionByCategory(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesService.createDistributionByType(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesService.createDistributionByType(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesService.createDistributionByPackage(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesService.createDistributionByFiles(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesService.createDistributionByPriorities(null, null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> issuesService.parse(null, (InputStream)null, null)).isInstanceOf(NullPointerException.class);






    }

}

