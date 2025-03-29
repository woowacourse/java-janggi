package dao.init;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection createConnection();
}
