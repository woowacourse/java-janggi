//package fixture;
//
//import dao.init.ConnectionGenerator;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class InMemoryH2ConnectionGenerator implements ConnectionGenerator {
//
//    @Override
//    public Connection createConnection() {
//        try {
//            return DriverManager.getConnection("jdbc:h2:mem:janggi;DB_CLOSE_DELAY=-1", "sa", "");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
