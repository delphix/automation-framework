package yamlparser.tests

import yamlparser.Yaml
import kotlin.test.assertEquals
import org.junit.Test

class Yamlest {
    private val yaml = Yaml("template", "api_key", "ami");

    @Test fun testAssert() : Unit {
        assertEquals("template", yaml.template)
        assertEquals("api_key", yaml.api_key)
        assertEquals("ami", yaml.ami)
    }
}
