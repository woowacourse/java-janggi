package db.util;

import org.assertj.db.type.AssertDbConnection;
import org.assertj.db.type.AssertDbConnectionFactory;

public final class AssertDbConnector {

    private AssertDbConnector() {}

    public static AssertDbConnection getAssertDbConnection() {
        return AssertDbConnectionFactory.of(
                TestDatabaseConstants.JDBC_URL,
                TestDatabaseConstants.JDBC_USER,
                TestDatabaseConstants.JDBC_PASSWORD
        ).create();
    }
}
