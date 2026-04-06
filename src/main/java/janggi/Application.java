package janggi;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.dto.GameSessionDTO;
import janggi.persistence.DatabaseProvider;
import janggi.persistence.JanggiGameRepository;
import janggi.service.JanggiService;
import janggi.util.SideDisplayNameMapper;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.UserCommand;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Application {

    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseProvider.getConnection();
        testDatabaseOnboarding(connection);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameManager gameManager = generateOrLoadManager(inputView, outputView, connection);
        Runner runner = new Runner(inputView, outputView, gameManager);
        runner.run();
        inputView.close();
    }

    private static GameManager generateOrLoadManager(InputView inputView, OutputView outputView,
                                                     Connection connection) {
        outputView.printSelectGameData();
        UserCommand userCommand = UserCommand.from(inputView.readUserCommand());
        if (userCommand.confirmed()) {
            return loadManager(inputView, outputView, connection);
        }
        return generateDefaultGameManager(inputView, outputView);
    }

    private static GameManager generateDefaultGameManager(InputView inputView, OutputView outputView) {
        Board board = Board.initialize();
        Turn initiativeTurn = Turn.init();
        Players players = initialPlayers(inputView, outputView);
        return new GameManager(players, board, initiativeTurn);
    }

    private static GameManager loadManager(InputView inputView, OutputView outputView, Connection connection) {
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository();
        JanggiService janggiService = new JanggiService(janggiGameRepository);
        try {
            List<GameSessionDTO> activeGames = janggiService.activeGames(connection);
            long selectedGameId = selectActiveGameId(inputView, outputView, activeGames);
            return janggiService.loadManagerByGameId(connection, selectedGameId);
        } catch (SQLException exception) {
            outputView.printLine("[ERROR] 진행 중인 게임 정보 조회에 실패했습니다." + "\n" + exception.getMessage());
            return generateDefaultGameManager(inputView, outputView);
        }
    }

    private static long selectActiveGameId(InputView inputView, OutputView outputView,
                                           List<GameSessionDTO> activeGames) {
        activeGames.forEach(outputView::printActiveGameInfo);
        return inputView.readGameId();
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

    private static void testDatabaseOnboarding(Connection connection) {
        System.out.println("====== [DB 온보딩 테스트 시작] ======");
        try (Statement stmt = connection.createStatement()) {

            executeSchemaCreation(stmt);
            executeDummyDataInsertion(stmt);
            verifyInsertedData(stmt);

        } catch (SQLException e) {
            System.err.println("[오류] DB 연결 또는 쿼리 실행 실패!");
            e.printStackTrace();
        }
        System.out.println("====== [DB 온보딩 테스트 종료] ======\n");
    }

    private static void executeSchemaCreation(Statement stmt) throws SQLException {
        String createSql = """
                    CREATE TABLE IF NOT EXISTS GAME (
                        game_id IDENTITY PRIMARY KEY,
                        cho_player_name VARCHAR(50) NOT NULL,
                        han_player_name VARCHAR(50) NOT NULL,
                        current_turn VARCHAR(10) NOT NULL,
                        is_finished BOOLEAN DEFAULT FALSE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
        stmt.execute(createSql);
        System.out.println("1. GAME 테이블 스키마 동기화 완료");
    }

    private static void executeDummyDataInsertion(Statement stmt) throws SQLException {
        String insertSql = "INSERT INTO GAME (cho_player_name, han_player_name, current_turn, is_finished) " +
                "VALUES ('테스트초', '테스트한', 'CHO', FALSE)";
        stmt.executeUpdate(insertSql);
        System.out.println("2. 더미 게임 데이터(테스트초 vs 테스트한, CHO 턴) 삽입 완료");
    }

    private static void verifyInsertedData(Statement stmt) throws SQLException {
        String selectSql = "SELECT game_id, cho_player_name, han_player_name, current_turn " +
                "FROM GAME ORDER BY game_id DESC LIMIT 1";
        try (ResultSet rs = stmt.executeQuery(selectSql)) {
            printVerificationResult(rs);
        }
    }

    private static void printVerificationResult(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            System.out.println("3. [실패] 데이터가 조회되지 않습니다.");
            return;
        }

        long id = rs.getLong("game_id");
        String choName = rs.getString("cho_player_name");
        String hanName = rs.getString("han_player_name");
        String turn = rs.getString("current_turn");

        System.out.printf("3. [성공] 최신 게임 정보 - ID: %d, 초: %s, 한: %s, 턴: %s\n",
                id, choName, hanName, turn);
    }
}
