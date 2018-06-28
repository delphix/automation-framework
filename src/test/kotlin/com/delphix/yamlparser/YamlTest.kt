package yamlparser.tests

import yamlparser.Yaml
import yamlparser.Environment
import kotlin.test.assertEquals
import org.junit.Test

class YamlTest {
    private val environment = Environment("develop", "branch", "datapod", "ami")
    private val yaml = Yaml("template", "api_key", "ami", listOf(environment));

    @Test fun testAssert() : Unit {
        assertEquals("template", yaml.template)
        assertEquals("api_key", yaml.api_key)
        assertEquals("ami", yaml.ami)
        assertEquals("branch", yaml.environments.get(0).branch);
    }
}
