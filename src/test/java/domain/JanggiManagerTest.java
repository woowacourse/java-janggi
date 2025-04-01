package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import dao.JanggiGameDao;
import dao.PieceDao;
import dao.fixture.JanggiGameTestFixture;
import domain.game.dto.JanggiGameResponseDto;
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
import java.util.Map.Entry;
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
    private H2ConnectionFactory factory;

    @BeforeEach
    void setup() {
        this.factory = new H2ConnectionFactory();
        factory.initializeTable();
        janggiManager = new JanggiManager(new H2ConnectionFactory());
    }

    @AfterEach
    void clearTable() {
        factory.initializeTable();
    }

    @Test
    @DisplayName("진행중인 게임의 정보들을 반환한다")
    void findInProgressGamesTest() throws SQLException {
        // given
        long savedGameId1 = JanggiGameTestFixture.saveNewJanggiGame(factory);
        long savedGameId2 = JanggiGameTestFixture.saveNewJanggiGame(factory);

        // when
        List<JanggiGameResponseDto> inProgressGames = janggiManager.findInProgressGames();

        // then
        Player choPlayer = new Player(new Username("테스트1"), TeamType.CHO);
        Player hanPlayer = new Player(new Username("테스트2"), TeamType.HAN);
        assertAll(
                () -> assertThat(inProgressGames).contains(
                        new JanggiGameResponseDto(savedGameId1, choPlayer, hanPlayer)),
                () -> assertThat(inProgressGames).contains(
                        new JanggiGameResponseDto(savedGameId2, choPlayer, hanPlayer))
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
        assertThatCode(() -> janggiManager.saveNewGame(players, choStrategy, hanStrategy))
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
    void undoTest(TurnState turnState, TurnState expectedTurnState, boolean expectedInProgress) throws SQLException {
        // given
        Connection connection = factory.getConnection();
        JanggiGameDao janggiGameDao = new JanggiGameDao();
        GameState gameState = GameState.IN_PROGRESS;
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(factory, turnState, gameState);
        connection.commit();

        // when
        janggiManager.undo(gameId);

        // then
        TurnState actualTurnState = janggiGameDao.findTurnStateById(gameId, connection).get();
        boolean actualInProgress = janggiManager.isInProgress(gameId);
        assertAll(
                () -> assertThat(actualTurnState).isEqualTo(expectedTurnState),
                () -> assertThat(actualInProgress).isEqualTo(expectedInProgress)
        );
    }

    @Test
    @DisplayName("기물을 이동하면 이동한 좌표가 저장된다")
    void movePieceTest() throws SQLException {
        // given
        Connection connection = factory.getConnection();
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(factory);
        Position from = Position.of(4, 3);
        Position to = Position.of(5, 5);
        Map<Position, Piece> pieces = Map.of(
                from, new Horse(TeamType.CHO)
        );

        new PieceDao().savePieces(pieces, gameId, connection);
        connection.commit();
        connection.close();

        // when
        janggiManager.movePiece(gameId, from, to);

        // then
        Map<Position, Piece> gamePieces = janggiManager.getGamePieces(gameId);

        assertAll(
                () -> assertThat(gamePieces).containsKey(to),
                () -> assertThat(gamePieces).doesNotContainKey(from)
        );
    }

    @Test
    @DisplayName("기물을 이동하여 잡힌 말은 데이터에서 제거된다")
    void movePieceRemoveTest() throws SQLException {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(factory);
        Position from = Position.of(4, 3);
        Position to = Position.of(5, 5);
        Map<Position, Piece> pieces = Map.of(
                from, new Horse(TeamType.CHO),
                to, new Soldier(TeamType.HAN)
        );

        JanggiGameTestFixture.saveBoardPieces(factory, gameId, pieces);

        // when
        janggiManager.movePiece(gameId, from, to);

        // then
        Map<Position, Piece> gamePieces = janggiManager.getGamePieces(gameId);

        assertAll(
                () -> assertThat(gamePieces).hasSize(1),
                () -> assertThat(gamePieces).containsKey(to)
        );
    }

    @Test
    @DisplayName("현재 턴을 진행하는 플레이어를 반환한다")
    void getCurrentPlayerTest() throws SQLException {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(factory);

        // when
        Player currentPlayer = janggiManager.getCurrentPlayer(gameId);

        // then
        Player expected = new Player(new Username("테스트1"), TeamType.CHO);
        assertThat(currentPlayer).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임의 우승자를 반환한다")
    void findWinnerTest() throws SQLException {
        // given
        TurnState turnState = new TurnState(true, TeamType.HAN);
        GameState gameState = GameState.FINISHED_SCORE;
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(factory, turnState, gameState);
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 4), new Chariot(TeamType.HAN),
                Position.of(1, 2), new Soldier(TeamType.CHO),
                Position.of(6, 5), new Cannon(TeamType.HAN),
                Position.of(8, 4), new King(TeamType.HAN),
                Position.of(1, 4), new King(TeamType.CHO)
        );

        JanggiGameTestFixture.saveBoardPieces(factory, gameId, pieces);

        // when
        Player actual = janggiManager.findWinner(gameId);

        // then
        assertThat(actual.getTeamType()).isEqualTo(TeamType.HAN);
    }

    @Test
    @DisplayName("플레이어들의 점수를 반환한다")
    void calculatePlayerScore() throws SQLException {
        // given
        TurnState turnState = new TurnState(true, TeamType.HAN);
        GameState gameState = GameState.FINISHED_SCORE;
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(factory, turnState, gameState);
        Map<Position, Piece> pieces = Map.of(
                Position.of(3, 4), new Chariot(TeamType.HAN),
                Position.of(1, 2), new Soldier(TeamType.CHO),
                Position.of(6, 5), new Cannon(TeamType.HAN),
                Position.of(8, 4), new King(TeamType.HAN),
                Position.of(4, 2), new Horse(TeamType.CHO),
                Position.of(1, 4), new King(TeamType.CHO)
        );

        JanggiGameTestFixture.saveBoardPieces(factory, gameId, pieces);

        // when
        Map<Player, Double> playerScores = janggiManager.calculatePlayerScore(gameId);
        double choPlayerScore = playerScores.entrySet().stream()
                .filter(entry -> entry.getKey().isSameTeam(TeamType.CHO))
                .map(Entry::getValue)
                .findFirst()
                .get();

        double hanPlayerScore = playerScores.entrySet().stream()
                .filter(entry -> entry.getKey().isSameTeam(TeamType.HAN))
                .map(Entry::getValue)
                .findFirst()
                .get();

        // then
        assertAll(
                () -> assertThat(choPlayerScore).isEqualTo(7.0),
                () -> assertThat(hanPlayerScore).isEqualTo(21.5)
        );
    }
}
