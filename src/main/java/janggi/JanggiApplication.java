package janggi;

import janggi.controller.JanggiController;
import janggi.db.DBConnection;
import janggi.db.DBInitializer;
import janggi.repository.JdbcGameRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.sql.Connection;
import java.sql.SQLException;

public class JanggiApplication {

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            DBInitializer.initialize(conn);
            JanggiService service = new JanggiService(new JdbcGameRepository(conn));
            JanggiController controller = new JanggiController(new InputView(), new OutputView(), service);
            controller.run();
        } catch (SQLException e) {
            System.out.println("DB 연결에 실패했습니다: " + e.getMessage());
        }
    }
}
