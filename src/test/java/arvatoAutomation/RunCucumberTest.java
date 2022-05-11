package arvatoAutomation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/arvatoAutomation.features",
        glue = {"arvatoAutomation.stepDefinitions"}
//        plugin = {
//                "json",
//                "json:target/cucumber.json"}
)
public class RunCucumberTest {
}

