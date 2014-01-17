
package stress;

import db.Database;
import junit.framework.TestCase;

public class StressTest extends TestCase {
    public void testInsertDelete() {
        long currentTimeMillis = System.currentTimeMillis();
        int count = 300;
        for (int i = 0; i < count; i += 1) {
            String[] colValues = new String[] {
                    "a" + i, "b" + i
            };
            Database.insert(colValues);
        }
        for (int i = 0; i < count; i += 1) {
            String[] colValues = new String[] {
                    "a" + i, "b" + i
            };
            Database.delete(colValues);
        }
        System.out.println(currentTimeMillis);
        System.out.println(System.currentTimeMillis());
        assertTrue(currentTimeMillis + 60000 > System.currentTimeMillis());
    }
}
