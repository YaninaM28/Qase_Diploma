package tests.api;

import api.adapters.CaseAdapter;
import api.models.cases.CaseRq;
import api.models.cases.CaseRs;
import api.models.cases.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CaseAPITest extends BaseAPITest {

    @Test
    public void checkCreateUpdateDeleteCase() {

        Step step = Step.builder()
                .action("action")
                .expected_result("expected")
                .data("today")
                .value("value")
                .build();

        CaseRq rq = CaseRq.builder()
                .steps_type("classic")
                .description("test")
                .preconditions("test")
                .postconditions("test")
                .title("title")
                .severity(1)
                .priority(1)
                .behavior(1)
                .type(1)
                .layer(1)
                .is_flaky(1)
                .isManual(1)
                .status(1)
                .steps(List.of(step))
                .build();

        CaseRs createdCase = CaseAdapter.createCase(projectCode, rq);

        Assert.assertTrue(createdCase.isStatus(), "Case was not created!");
        Assert.assertNotNull(createdCase.getResult(), "Result in NULL");

        int caseId = createdCase.getResult().getId();

        CaseRq updateRq = CaseRq.builder()
                .title("updated_title")
                .description("updated")
                .severity(2)
                .priority(2)
                .behavior(1)
                .type(1)
                .layer(1)
                .is_flaky(0)
                .isManual(1)
                .build();

        CaseAdapter.updateCase(projectCode, caseId, updateRq);

        CaseRs updatedCase = CaseAdapter.getCase(projectCode, caseId);
        Assert.assertEquals(updatedCase.getResult().getId(), caseId);

        CaseAdapter.deleteCase(projectCode, caseId);
    }
}
