package janggi;

import janggi.db.DBConnection;
import janggi.db.DBInitializer;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.sql.Connection;
import java.sql.SQLException;

public class JanggiApplication {

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            DBInitializer initializer = new DBInitializer();
            initializer.initialize(conn);

            JanggiGame game = new JanggiGame();
            JanggiController controller = new JanggiController(new InputView(), new OutputView(), game);
            controller.run();
        } catch (SQLException e) {
            System.out.println("DB 연결에 실패했습니다: " + e.getMessage());
        }
    }
}
