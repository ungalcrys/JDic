
package db;

import java.util.HashMap;

import junit.framework.TestCase;

import org.junit.Test;

public class DbOperationsTest extends TestCase {
    @Test
    public void testInsertAndDelete() throws Exception {
        int maxId = Database.getMaxId();
        String[] colValues = new String[] {
                "a1", "a2"
        };
        Database.insert(colValues);
        assertTrue(maxId < Database.getMaxId());
        Database.delete(colValues);
        assertEquals(maxId, Database.getMaxId());
    }

    @Test
    public void testSearch1() throws Exception {
        String[] colValues = new String[] {
                "1234567890", "abcdefghijklmnopqrstuvxyz"
        };
        Database.insert(colValues);
        HashMap<String, String> translations = Database.getTranslations(new String[] {
                "", "%hijkl%"
        });
        System.out.println(translations.size());
        assertEquals("abcdefghijklmnopqrstuvxyz", translations.get("1234567890"));
        Database.delete(colValues);
    }

    @Test
    public void testSearch2() throws Exception {
        String[] colValues = new String[] {
                "123", "a_c"
        };
        Database.insert(colValues);
        HashMap<String, String> translations = Database.getTranslations(new String[] {
                "", "?_?"
        });
        System.out.println(translations.size());
        assertEquals("a_c", translations.get("123"));
        Database.delete(colValues);
    }
}
