package janggi;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.persistence.DatabaseProvider;
import janggi.util.SideDisplayNameMapper;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {

    public static void main(String[] args) {
        testDatabaseOnboarding();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameManager gameManager = generateManager(inputView, outputView);
        Runner runner = new Runner(inputView, outputView, gameManager);
        runner.run();
        inputView.close();
    }

    private static GameManager generateManager(InputView inputView, OutputView outputView) {
        Board board = Board.initialize();
        Turn initiativeTurn = Turn.init();
        Players players = initialPlayers(inputView, outputView);
        return new GameManager(players, board, initiativeTurn);
    }

    private static Players initialPlayers(InputView inputView, OutputView outputView) {
        String choPlayerName = readPlayerName(inputView, outputView, Side.CHO);
        String hanPlayerName = readPlayerName(inputView, outputView, Side.HAN);
        return Players.from(choPlayerName, hanPlayerName);
    }

    private static String readPlayerName(InputView inputView, OutputView outputView, Side side) {
        String sideName = SideDisplayNameMapper.toDisplayName(side);
        outputView.printPlayerNameNotice(sideName);
        return inputView.readPlayerName();
    }

    private static void testDatabaseOnboarding() {
        System.out.println("====== [DB 온보딩 테스트 시작] ======");
        try (Connection conn = DatabaseProvider.getConnection();
             Statement stmt = conn.createStatement()) {

            String createSql = """
                        CREATE TABLE IF NOT EXISTS GAME (
                            game_id IDENTITY PRIMARY KEY,
                            current_turn VARCHAR(10) NOT NULL,
                            is_finished BOOLEAN DEFAULT FALSE,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        )
                    """;
            stmt.execute(createSql);
            System.out.println("1. GAME 테이블 생성 완료");

            String insertSql = "INSERT INTO GAME (current_turn) VALUES ('CHO')";
            stmt.executeUpdate(insertSql);
            System.out.println("2. 더미 게임 데이터(CHO 턴) 삽입 완료");

            String selectSql = "SELECT * FROM GAME ORDER BY game_id DESC LIMIT 1";
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                if (rs.next()) {
                    long id = rs.getLong("game_id");
                    String turn = rs.getString("current_turn");
                    System.out.printf("3. 조회 성공! [ID: %d, 턴: %s]\n", id, turn);
                }
            }
        } catch (SQLException e) {
            System.err.println("[오류] DB 연결 또는 쿼리 실행 실패!");
            e.printStackTrace();
        }
        System.out.println("====== [DB 온보딩 테스트 종료] ======\n");
    }
}
