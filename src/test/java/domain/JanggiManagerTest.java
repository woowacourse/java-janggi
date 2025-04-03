package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import dao.JanggiGameDao;
import dao.PieceDao;
import dao.fixture.JanggiGameTestFixture;
import domain.game.dto.JanggiGameDto;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Soldier;
import domain.piece.strategy.LeftElephantStrategy;
import domain.piece.strategy.RightElephantStrategy;
import domain.player.Player;
import domain.player.Players;
import domain.player.Username;
import domain.player.Usernames;
import domain.position.Position;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.H2ConnectionFactory;

class JanggiManagerTest {

    private JanggiManager janggiManager;
    private Connection connection;

    @BeforeEach
    void setup() throws SQLException {
        H2ConnectionFactory factory = new H2ConnectionFactory();
        factory.initializeTable();
        connection = factory.getConnection();
        connection.setAutoCommit(false);
        janggiManager = new JanggiManager();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("진행중인 게임의 정보들을 반환한다")
    void findInProgressGamesTest() {
        // given
        long savedGameId1 = JanggiGameTestFixture.saveNewJanggiGame(connection);
        long savedGameId2 = JanggiGameTestFixture.saveNewJanggiGame(connection);

        // when
        List<JanggiGameDto> inProgressGames = janggiManager.findInProgressGames(connection);

        // then
        String choPlayerName = "테스트1";
        String hanPlayerName = "테스트2";
        assertAll(
                () -> assertThat(inProgressGames).contains(
                        new JanggiGameDto(savedGameId1, choPlayerName, hanPlayerName)),
                () -> assertThat(inProgressGames).contains(
                        new JanggiGameDto(savedGameId2, choPlayerName, hanPlayerName))
        );
    }

    @Test
    @DisplayName("새로운 게임을 생성하여 저장한다")
    void saveNewGameTest() {
        // given
        Username choPlayerName = new Username("루키");
        Username hanPlayerName = new Username("피케이");
        Usernames usernames = new Usernames(choPlayerName, hanPlayerName);
        Players players = Players.createFrom(usernames, choPlayerName);
        LeftElephantStrategy choStrategy = new LeftElephantStrategy();
        RightElephantStrategy hanStrategy = new RightElephantStrategy();

        // when & then
        assertThatCode(() -> janggiManager.saveNewGame(players, choStrategy, hanStrategy, connection))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> undoTest() {
        return Stream.of(
                Arguments.of(new TurnState(true, TeamType.HAN), new TurnState(true, TeamType.HAN), false),
                Arguments.of(new TurnState(false, TeamType.CHO), new TurnState(true, TeamType.HAN), true)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("정상적으로 무르기가 실행되면 변경된 정보가 저장된다")
    void undoTest(TurnState turnState, TurnState expectedTurnState, boolean expectedInProgress) {
        // given
        JanggiGameDao janggiGameDao = new JanggiGameDao();
        GameState gameState = GameState.IN_PROGRESS;
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(turnState, gameState, connection);

        // when
        janggiManager.undo(gameId, connection);

        // then
        TurnState actualTurnState = janggiGameDao.findTurnStateById(gameId, connection).get();
        boolean actualInProgress = janggiManager.isInProgress(gameId, connection);
        assertAll(
                () -> assertThat(actualTurnState).isEqualTo(expectedTurnState),
                () -> assertThat(actualInProgress).isEqualTo(expectedInProgress)
        );
    }

    @Test
    @DisplayName("기물을 이동하면 이동한 좌표가 저장된다")
    void movePieceTest() {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Position from = Position.of(4, 3);
        Position to = Position.of(5, 5);
        Map<Position, Piece> pieces = Map.of(
                from, new Horse(TeamType.CHO)
        );

        new PieceDao().savePieces(pieces, gameId, connection);

        // when
        janggiManager.movePiece(gameId, from, to, connection);

        // then
        Map<Position, Piece> gamePieces = janggiManager.getGamePieces(gameId, connection);

        assertAll(
                () -> assertThat(gamePieces).containsKey(to),
                () -> assertThat(gamePieces).doesNotContainKey(from)
        );
    }

    @Test
    @DisplayName("기물을 이동하여 잡힌 말은 데이터에서 제거된다")
    void movePieceRemoveTest() {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Position from = Position.of(4, 3);
        Position to = Position.of(5, 5);
        Map<Position, Piece> pieces = Map.of(
                from, new Horse(TeamType.CHO),
                to, new Soldier(TeamType.HAN)
        );

        JanggiGameTestFixture.saveBoardPieces(gameId, pieces, connection);

        // when
        janggiManager.movePiece(gameId, from, to, connection);

        // then
        Map<Position, Piece> gamePieces = janggiManager.getGamePieces(gameId, connection);

        assertAll(
                () -> assertThat(gamePieces).hasSize(1),
                () -> assertThat(gamePieces).containsKey(to)
        );
    }

    @Test
    @DisplayName("현재 턴을 진행하는 플레이어를 반환한다")
    void getCurrentPlayerTest() {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);

        // when
        Player currentPlayer = janggiManager.getCurrentPlayer(gameId, connection);

        // then
        Player expected = new Player(new Username("테스트1"), TeamType.CHO);
        assertThat(currentPlayer).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임의 우승자를 반환한다")
    void findWinnerTest() {
        // given
        TurnState turnState = new TurnState(true, TeamType.HAN);
        GameState gameState = GameState.FINISHED_SCORE;
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(turnState, gameState, connection);
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 4), new Chariot(TeamType.HAN),
                Position.of(1, 2), new Soldier(TeamType.CHO),
                Position.of(6, 5), new Cannon(TeamType.HAN),
                Position.of(8, 4), new King(TeamType.HAN),
                Position.of(1, 4), new King(TeamType.CHO)
        );

        JanggiGameTestFixture.saveBoardPieces(gameId, pieces, connection);

        // when
        Player actual = janggiManager.findWinner(gameId, connection);

        // then
        assertThat(actual.getTeamType()).isEqualTo(TeamType.HAN);
    }

    @Test
    @DisplayName("플레이어들의 점수를 반환한다")
    void calculatePlayerScore() {
        // given
        TurnState turnState = new TurnState(true, TeamType.HAN);
        GameState gameState = GameState.FINISHED_SCORE;
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(turnState, gameState, connection);
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 4), new Chariot(TeamType.HAN),
                Position.of(1, 2), new Soldier(TeamType.CHO),
                Position.of(6, 5), new Cannon(TeamType.HAN),
                Position.of(8, 4), new King(TeamType.HAN),
                Position.of(4, 2), new Horse(TeamType.CHO),
                Position.of(1, 4), new King(TeamType.CHO)
        );

        JanggiGameTestFixture.saveBoardPieces(gameId, pieces, connection);

        // when
        Map<String, Double> playerScores = janggiManager.calculatePlayerScore(gameId, connection);
        double choPlayerScore = playerScores.get("테스트1");
        double hanPlayerScore = playerScores.get("테스트2");

        // then
        assertAll(
                () -> assertThat(choPlayerScore).isEqualTo(7.0),
                () -> assertThat(hanPlayerScore).isEqualTo(21.5)
        );
    }
}
