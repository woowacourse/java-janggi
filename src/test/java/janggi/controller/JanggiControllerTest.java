package janggi.controller;

import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.*;
import janggi.domain.vo.FinishStatus;
import janggi.domain.vo.position.Position;
import janggi.repositiory.game.GameRepository;
import janggi.repositiory.game.JdbcGameRepository;
import janggi.repositiory.piece.BoardSnapshot;
import janggi.repositiory.piece.JdbcPieceRepository;
import janggi.repositiory.piece.PieceRepository;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiControllerTest {
    private JdbcDataSource dataSource;
    private GameRepository gameRepository;
    private PieceRepository pieceRepository;
    private JanggiController controller;

    @BeforeEach
    void setUp() {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb_" + UUID.randomUUID() + ";MODE=MySQL;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        gameRepository = new JdbcGameRepository(dataSource);
        pieceRepository = new JdbcPieceRepository(dataSource);
    }

    @Test
    @DisplayName("새 게임 시작 시 트랜잭션이 성공하여 게임과 기물이 모두 저장되어야 한다")
    void startJanggi_NewGame_Success() throws SQLException{
        // when
        controller = JanggiController.startJanggi(dataSource);
        Connection conn = dataSource.getConnection();

        // then
        assertThat(controller).isNotNull();
        Long gameId = gameRepository.findLatestGame(conn).get().gameId();

        assertThat(gameRepository.findLatestGame(conn)).isPresent();
        assertThat(pieceRepository.findAll(conn, gameId)).hasSize(32);
    }

    @Test
    @DisplayName("기존 진행 중인 게임이 있으면 해당 데이터를 로드하여 재개한다")
    void startJanggi_ResumeGame() throws SQLException {
        // given
        Connection conn = dataSource.getConnection();

        Long id = gameRepository.save(conn, new FinishStatus(false), Team.HAN);
        pieceRepository.updateALL(conn, new BoardSnapshot(id, new HashMap<>() {{
            put(new Position(1, 4), new King(Team.HAN));
            put(new Position(8, 4), new King(Team.CHO));
        }}));

        // when
        controller = JanggiController.startJanggi(dataSource);

        // then
        assertThat(controller.janggiGame.isResumed()).isTrue();
        assertThat(controller.janggiGame.getCurrentTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("이동(playTurn) 수행 시 기물 위치와 턴 정보가 DB에 업데이트된다")
    void playTurn_UpdatesPersistence() throws SQLException{
        // given
        controller = JanggiController.startJanggi(dataSource);
        Long gameId = controller.janggiGame.snapshot().gameId();
        Connection conn = dataSource.getConnection();

        // 초나라 졸 이동 (6,2 -> 5,2)
        MoveCommand command = MoveCommand.from(new int[]{6, 2, 5, 2});

        // when
        controller.playTurn(command);

        // then
        Map<Position, Piece> pieces = pieceRepository.findAll(conn, gameId);
        assertThat(pieces.get(new Position(5, 2)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(gameRepository.findLatestGame(conn).get().currentTurn()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("연속으로 스킵하면 게임이 종료되고 DB 상태도 변경된다")
    void consecutiveSkip_FinishesGame() throws SQLException{
        // given
        controller = JanggiController.startJanggi(dataSource);
        Connection conn = dataSource.getConnection();

        // when
        controller.skipTurn(); // CHO 스킵
        controller.skipTurn(); // HAN 스킵

        // then
        assertThat(controller.janggiGame.getFinishStatus().isFinished()).isTrue();
        assertThat(gameRepository.findLatestGame(conn).get().isFinished()).isTrue();
    }

    @Test
    @DisplayName("기권 시 게임이 종료되며 승리자가 결정된다")
    void resign_UpdatesDatabase() throws SQLException{
        // given
        controller = JanggiController.startJanggi(dataSource);
        Connection conn = dataSource.getConnection();

        // when
        controller.resignTurn(); // CHO 기권 -> HAN 승리

        // then
        assertThat(gameRepository.findLatestGame(conn).get().isFinished()).isTrue();
        assertThat(controller.janggiGame.decideWinner()).isEqualTo(Team.HAN);
    }
}