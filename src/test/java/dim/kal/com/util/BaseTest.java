package dim.kal.com.util;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    public void loadImportSql() throws Exception {
        em.createNativeQuery("DELETE FROM classes_students").executeUpdate();
        em.createNativeQuery("DELETE FROM students").executeUpdate();
        em.createNativeQuery("DELETE FROM classes").executeUpdate();
        em.createNativeQuery("DELETE FROM teachers").executeUpdate();

        em.flush();

        try (InputStream in = getClass().getResourceAsStream("/import.sql");
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            StringBuilder sqlStatement = new StringBuilder();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) continue;
                sqlStatement.append(line).append(" ");
                if (line.endsWith(";")) {
                    String sql = sqlStatement.toString().trim();
                    sql = sql.substring(0, sql.length() - 1); // αφαιρούμε το τελικό ;
                    if (sql.toLowerCase().startsWith("select setval")) {
                        em.createNativeQuery(sql).getSingleResult();
                    } else {
                        em.createNativeQuery(sql).executeUpdate();
                    }
                    sqlStatement.setLength(0);
                }
            }
        }
    }
}
