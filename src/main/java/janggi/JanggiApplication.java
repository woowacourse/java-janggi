package janggi;

import janggi.controller.JanggiController;
import janggi.dao.PiecesDao;
import janggi.dao.TurnDao;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import service.JanggiService;

public class JanggiApplication {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root";

    public static void main(final String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView();
        try (Connection connection = getConnection()) {
            if (connection == null) {
                throw new SQLException();
            }
            PiecesDao piecesDao = new PiecesDao(connection);
            TurnDao turnDao = new TurnDao(connection);
            JanggiService janggiService = new JanggiService(piecesDao, turnDao);
            JanggiController janggiController = new JanggiController(
                inputView,
                outputView,
                janggiService
            );
            janggiController.startJanggi();
        } catch (SQLException e) {
            System.out.println("데이터베이스 오류");
        }
    }

    public static Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
