package janggi.db.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.db.DbConnector;
import janggi.db.dao.GameDao;
import janggi.db.dao.PieceDao;
import janggi.db.entity.GameEntity;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFormation;
import janggi.domain.board.BoardInitiator;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiRepositoryTest {

    JanggiRepository janggiRepository;
    private GameDao gameDao;
    private PieceDao pieceDao;

    @BeforeEach
    void 테스트_시작() {
        DbConnector.initDatabase();
        gameDao = new GameDao();
        pieceDao = new PieceDao();
        janggiRepository = new JanggiRepository(gameDao, pieceDao);
    }

    @AfterEach
    void 테스트_종료() {
        try (Connection connection = DbConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE pieces");
            statement.execute("SET REFERENTIAL_INTEGRITY FALSE");
            statement.execute("TRUNCATE TABLE games");
            statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("새로운 장기 게임을 저장할 수 있다")
    void 새로운_장기_게임_저장() {
        // given
        Board board = new Board();
        BoardInitiator boardInitiator = new BoardInitiator();
        boardInitiator.initializeByFormation(board, BoardFormation.MA_SANG_MA_SANG, Team.CHO);
        boardInitiator.initializeByFormation(board, BoardFormation.SANG_MA_SANG_MA, Team.HAN);

        // when
        Long savedId = janggiRepository.save(null, board, Team.CHO, false);

        // then
        GameEntity savedGame = janggiRepository.findGameById(savedId);
        Board savedBoard = janggiRepository.loadBoard(savedId);
        assertThat(savedGame.getTurn()).isEqualTo(Team.CHO);
        assertThat(savedGame.isFinished()).isFalse();
        assertThat(savedBoard.getBoard().size()).isEqualTo(32);
    }

    @Test
    @DisplayName("진행중인 장기 게임을 업데이트할 수 있다")
    void 진행중인_장기_게임_업데이트() {
        // given
        Board board = new Board();
        BoardInitiator boardInitiator = new BoardInitiator();
        boardInitiator.initializeByFormation(board, BoardFormation.MA_SANG_MA_SANG, Team.CHO);
        boardInitiator.initializeByFormation(board, BoardFormation.SANG_MA_SANG_MA, Team.HAN);
        Long savedId = janggiRepository.save(null, board, Team.CHO, false);
        board.movePiece(new Position(1, 10), new Position(1, 8));

        // when
        Long updatedId = janggiRepository.save(savedId, board, Team.HAN, false);

        // then
        GameEntity updataedGame = janggiRepository.findGameById(updatedId);
        Board savedBoard = janggiRepository.loadBoard(updatedId);
        assertThat(updataedGame.getTurn()).isEqualTo(Team.HAN);
        assertThat(updataedGame.isFinished()).isFalse();
        assertThat(savedBoard.getBoard().size()).isEqualTo(32);
        assertThat(savedBoard.pieceAt(new Position(1, 8))).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 ID의 게임을 불러오려고 하면 예외가 발생한다.")
    void 존재하지_않는_게임_가져오기() {
        assertThatThrownBy(() -> {
            janggiRepository.findGameById(3L);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 ID의 게임이 없습니다.");
    }

    @Test
    @DisplayName("저장한 보드 정보를 불러올 수 있다")
    void 저장되어있는_보드_가져오기() {
        // given
        Board board = new Board();
        BoardInitiator boardInitiator = new BoardInitiator();
        boardInitiator.initializeByFormation(board, BoardFormation.MA_SANG_MA_SANG, Team.CHO);
        boardInitiator.initializeByFormation(board, BoardFormation.SANG_MA_SANG_MA, Team.HAN);
        Long savedId = janggiRepository.save(null, board, Team.CHO, false);

        // when
        Board savedBoard = janggiRepository.loadBoard(savedId);

        // then
        assertThat(savedBoard.getBoard().size()).isEqualTo(32);
    }
}
