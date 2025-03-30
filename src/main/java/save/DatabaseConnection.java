package save;

import java.sql.Connection;

public interface DatabaseConnection {

    Connection getConnection();
}
