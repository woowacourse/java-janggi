package janggi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static final String CONFIG_PROPERTIES = "config.properties";

    private static DBUtil instance;

    private DBUtil() {
    }

    public static DBUtil getInstance() {
        if (instance == null) {
            instance = new DBUtil();
        }
        return instance;
    }

    public Connection getConnection() {
        final Properties properties = new Properties();
        try (InputStream input = DBUtil.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
            properties.load(input);

            final String server = properties.getProperty("db.server");
            final String database = properties.getProperty("db.name");
            final String options = properties.getProperty("db.options");
            final String username = properties.getProperty("db.username");
            final String password = properties.getProperty("db.password");

            return DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + options, username, password);
        } catch (final SQLException | IOException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
