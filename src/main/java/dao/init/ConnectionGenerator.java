package dao.init;

import java.sql.Connection;

public interface ConnectionGenerator {

    Connection createConnection();
}
