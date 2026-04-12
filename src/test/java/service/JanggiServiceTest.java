package service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.Position;
import domain.board.Board;
import domain.board.TableSetting;
import domain.country.CountryType;
import dto.GameInfo;
import infrastructure.JdbcConnectionManager;
import infrastructure.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.GameInfoRepository;
import repository.GameInfoTestRepository;
import repository.PositionHistoryRepository;
import repository.PositionHistoryTestRepository;
import repository.PositionStateExceptionTestRepository;
import repository.PositionStateRepository;
import repository.PositionStateTestRepository;
import repository.TurnHistoryRepository;
import repository.TurnHistoryTestRepository;

public class JanggiServiceTest {
    private JanggiService janggiService;
    private JanggiService janggiServiceWithException;

    private GameInfoRepository gameInfoRepository;
    private PositionStateRepository positionStateRepository;
    private PositionHistoryRepository positionHistoryRepository;
    private TurnHistoryRepository turnHistoryRepository;

    private Connection mockConnection;
    private PositionStateRepository positionStateExceptionRepository;

    @BeforeEach
    void setUp() throws Exception {
        gameInfoRepository = new GameInfoTestRepository();
        positionStateRepository = new PositionStateTestRepository();
        positionHistoryRepository = new PositionHistoryTestRepository();
        turnHistoryRepository = new TurnHistoryTestRepository();

        positionStateExceptionRepository = new PositionStateExceptionTestRepository();

        JdbcConnectionManager mockConnectionManager = mock(JdbcConnectionManager.class);
        mockConnection = mock(Connection.class);

        when(mockConnectionManager.getConnection()).thenReturn(mockConnection);

        janggiService = new JanggiService(
                gameInfoRepository,
                positionStateRepository,
                positionHistoryRepository,
                turnHistoryRepository,
                new TransactionManager(mockConnectionManager));

        janggiServiceWithException = new JanggiService(
                gameInfoRepository,
                positionStateExceptionRepository,
                positionHistoryRepository,
                turnHistoryRepository,
                new TransactionManager(mockConnectionManager));
    }

    @Test
    @DisplayName("GameInfoRepository에 GameInfo가 잘 들어갔는지 확인한다.")
    void saveGameInfoTest() {
        int id = janggiService.makeBoard(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        GameInfo gameInfo = gameInfoRepository.findGameInfoById(id, null);
        assertThat(gameInfo.id()).isEqualTo(id);
    }

    @Test
    @DisplayName("모든 GameInfo ID를 정상적으로 가져오는지 확인한다.")
    void readAllGameInfoIdsTest() {
        int id1 = gameInfoRepository.saveGameInfo(null);
        int id2 = gameInfoRepository.saveGameInfo(null);
        gameInfoRepository.deleteGameInfo(id2, null);
        int id3 = gameInfoRepository.saveGameInfo(null);

        List<Integer> expectedIds = List.of(id1, id3);

        assertThat(janggiService.readAllGameInfoIds()).isEqualTo(expectedIds);
    }

    @Test
    @DisplayName("입력 값으로 TableSetting을 생성한다.")
    void makeTableSettingTest() {
        String input = "상마상마";
        assertThat(janggiService.makeTableSetting(input)).isEqualTo(TableSetting.LEFT_TABLE);
    }

    @Test
    @DisplayName("입력 값으로 Position을 생성한다.")
    void makePositionTest() {
        String input = "0, 5";
        assertThat(janggiService.makePosition(input)).isEqualTo(new Position(0, 5));
    }

    @Test
    @DisplayName("GameInfo를 정상적으로 업데이트한다.")
    void updateGameInfoTest() {
        int id = gameInfoRepository.saveGameInfo(null);
        gameInfoRepository.updateGameInfo(CountryType.HAN, id, null);

        GameInfo gameInfo = gameInfoRepository.findGameInfoById(id, null);
        assertThat(gameInfo.turn()).isEqualTo(CountryType.HAN.name());
    }

    @Test
    @DisplayName("저장된 GameInfo가 하나도 없으면 예외가 발생한다.")
    void nothingExistGameInfoExceptionTest() {
        assertThatThrownBy(() -> janggiService.readAllGameInfoIds())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 저장된 보드가 없습니다.");
    }

    @Test
    @DisplayName("입력 받은 ID의 GameInfo가 없으면 예외가 발생한다.")
    void notExistGameInfoByIdExceptionTest() {
        String input = "100";

        gameInfoRepository.saveGameInfo(null);

        assertThatThrownBy(() -> janggiService.findBoardId(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 번호의 board가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("보드 생성 중 예외가 발생하면 rollback이 호출된다.")
    void makeBoardTransactionRollbackTest() throws SQLException {
        assertThatThrownBy(
                () -> janggiServiceWithException.makeBoard(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 트랜잭션에 실패했습니다.");

        verify(mockConnection, times(1)).rollback();
    }

    @Test
    @DisplayName("보드를 불러오는 중 예외가 발생하면 rollback이 호출된다.")
    void readBoardTransactionRollbackTest() throws SQLException {
        int id = gameInfoRepository.saveGameInfo(null);

        assertThatThrownBy(
                () -> janggiServiceWithException.readBoard(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 트랜잭션에 실패했습니다.");

        verify(mockConnection, times(1)).rollback();
    }

    @Test
    @DisplayName("기물 이동 중 예외가 발생하면 rollback이 호출된다.")
    void movePieceTransactionRollbackTest() throws SQLException {
        int id = gameInfoRepository.saveGameInfo(null);
        Board mockBoard = mock(Board.class);
        Position mockPosition = mock(Position.class);

        assertThatThrownBy(
                () -> janggiServiceWithException.movePiece(mockBoard, mockPosition, mockPosition, id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 트랜잭션에 실패했습니다.");

        verify(mockConnection, times(1)).rollback();
    }

    @Test
    @DisplayName("보드 관련 모든 행 삭제 중 예외가 발생하면 rollback이 호출된다.")
    void deleteTransactionRollbackTest() throws SQLException {
        int id = gameInfoRepository.saveGameInfo(null);

        assertThatThrownBy(
                () -> janggiServiceWithException.deleteAllByGameInfoId(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 트랜잭션에 실패했습니다.");

        verify(mockConnection, times(1)).rollback();
    }
}
