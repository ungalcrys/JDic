
package config;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

public class ConfigurationTest extends TestCase {
    @Test
    public void testDbPath() throws Exception {
        String dbPath = Configuration.getDbPath();
        assertTrue(new File(dbPath).exists());
    }
}
