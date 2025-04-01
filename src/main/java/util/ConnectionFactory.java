package util;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection getConnection();
}
