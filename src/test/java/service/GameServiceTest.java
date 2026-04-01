package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import config.H2ConnectionManager;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import entity.GameStateEntity;
import factory.BoardFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.BoardRepositoryImpl;
import repository.impl.GameRoomRepositoryImpl;
import repository.impl.GameStateRepositoryImpl;

public class GameServiceTest {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private GameService gameService;

    @BeforeEach
    void setUp() throws SQLException {
        H2ConnectionManager connectionManager = new H2ConnectionManager(URL, USER, PASSWORD);
        TestDatabaseInitializer testDatabaseInitializer = new TestDatabaseInitializer(connectionManager);
        testDatabaseInitializer.init();

        gameService = new GameService(new BoardRepositoryImpl(),
                new GameRoomRepositoryImpl(),
                new GameStateRepositoryImpl(), connectionManager);
    }

    @Test
    @DisplayName("게임 저장 시 게임룸, 보드, 게임 상태가 함께 저장된다")
    void saveGame_shouldPersistRoomBoardAndState() {
        // given
        Map<Position, Place> board = BoardFactory.setUpEmpty();
        String name = "testName";
        Side side = Side.CHO;
        // when & then
        assertThatCode(() -> gameService.saveGame(board, name, side))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("게임룸 ID로 보드를 조회할 수 있다")
    void findBoardByRoomId_shouldReturnBoard() {
        // given
        int roomId = 1;

        // when
        Map<Position, Place> board = gameService.findBoardByRoomId(roomId);

        //then
        assertThat(board).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 게임룸 ID로 게임 보드 조회 시 예외가 발생한다")
    void findBoardByRoomId_shouldThrowException_whenRoomNotFound() {
        // given
        int roomId = 2;

        // when & then
        assertThatThrownBy(() -> gameService.findBoardByRoomId(roomId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 없는 방 번호입니다.");

    }

    @Test
    @DisplayName("게임룸 ID로 게임 상태를 조회할 수 있다")
    void findGameStateByRoomId_shouldReturnGameState() {
        // given
        int roomId = 1;

        // when
        GameStateEntity gameStateEntity = gameService.findGameStateByRoomId(roomId);

        // then
        assertThat(gameStateEntity.currentSide()).isEqualTo(Side.CHO);
    }

    @Test
    @DisplayName("존재하지 않는 게임룸 ID 조회 시 게임 상태 조회에서 예외가 발생한다")
    void findGameStateByRoomId_shouldThrowException_whenRoomNotFound() {
        // given
        int roomId = 2;

        // when & then
        assertThatThrownBy(() -> gameService.findGameStateByRoomId(roomId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 없는 방 번호입니다.");
    }

    @Test
    @DisplayName("전체 게임룸 목록을 조회할 수 있다")
    void findGameRoomAll_shouldReturnAllRooms() {
        // given & when & then
        assertThat(gameService.findGameRoomAll().size()).isEqualTo(1);
    }

}
