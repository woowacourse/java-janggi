package janggi.service;

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
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JanggiServiceTest {
    private GameRepository gameRepository;
    private PieceRepository pieceRepository;
    private JanggiService service;

    @BeforeEach
    void setUp() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        try (java.sql.Connection conn = dataSource.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE piece");
            stmt.execute("DELETE FROM game");
        }

        gameRepository = new JdbcGameRepository(dataSource);
        pieceRepository = new JdbcPieceRepository(dataSource);
    }

    @Test
    void 새_게임_시작_테스트() {
        // when
        service = JanggiService.startGame(gameRepository, pieceRepository);

        // then
        assertThat(service).isNotNull();
        assertThat(service.isResumed()).isFalse();
        assertThat(service.getBoard().size()).isEqualTo(32);
    }

    @Test
    void 기존_게임_재시작_테스트() {
        // given
        Long id = gameRepository.save(new FinishStatus(false), Team.HAN);
        pieceRepository.updateALL(new BoardSnapshot(id, new HashMap<>() {{
            put(new Position(1, 4), new King(Team.HAN));
            put(new Position(8, 4), new King(Team.CHO));
        }}));

        // when
        service = JanggiService.startGame(gameRepository, pieceRepository);

        // then
        assertThat(service).isNotNull();
        assertThat(service.getCurrentTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 턴_정상_플레이_테스트() {
        // given
        Long id = gameRepository.save(new FinishStatus(false), Team.CHO);
        Position oldPosition = new Position(6, 2);

        pieceRepository.updateALL(new BoardSnapshot(id, new HashMap<>() {{
            put(new Position(1, 4), new King(Team.HAN));
            put(new Position(8, 4), new King(Team.CHO));
            put(oldPosition, new Soldier(Team.CHO));
        }}));

        service = JanggiService.startGame(gameRepository, pieceRepository);
        MoveCommand command = MoveCommand.from(new int[]{6, 2, 5, 2});

        // when
        service.playTurn(command);

        // then
        Position target = new Position(5, 2);
        Map<Position, Piece> result = pieceRepository.findAll(id);
        assertThat(result.getOrDefault(oldPosition, EmptyPiece.getInstance())).isEqualTo(EmptyPiece.getInstance());
        assertThat(result.get(target).pieceType()).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    void 잘못된_이동_요청시_DB가_업데이트되지_않는다() {
        // given
        Long id = gameRepository.save(new FinishStatus(false), Team.CHO);
        Position position = new Position(6, 2);
        pieceRepository.updateALL(new BoardSnapshot(id, new HashMap<>() {{
            put(new Position(1, 4), new King(Team.HAN));
            put(new Position(8, 4), new King(Team.CHO));
            put(position, new Soldier(Team.CHO));
        }}));

        service = JanggiService.startGame(gameRepository, pieceRepository);
        MoveCommand invalidCommand = MoveCommand.from(new int[]{6, 2, 8, 8});

        // when, then
        assertThatThrownBy(() -> service.playTurn(invalidCommand))
                .isInstanceOf(RuntimeException.class);

        Piece expectedPiece = pieceRepository.findAll(id).get(position);
        assertThat(expectedPiece.pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(expectedPiece.getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 턴_skip_테스트() {
        // given
        service = JanggiService.startGame(gameRepository, pieceRepository);

        // when
        service.skipTurn();

        // then
        assertThat(service.getCurrentTeam()).isEqualTo(Team.HAN);
        assertThat(gameRepository.findLatestGame().get().currentTurn()).isEqualTo(Team.HAN);
    }

    @Test
    void 연속_skip_게임_종료_테스트() {
        // Given
        service = JanggiService.startGame(gameRepository, pieceRepository);

        // When
        service.skipTurn();
        service.skipTurn();

        // Then
        assertThat(gameRepository.findLatestGame().get().isFinished()).isTrue();
    }

    @Test
    void 기권_결과_업데이트_테스트() {
        // given
        service = JanggiService.startGame(gameRepository, pieceRepository);

        // when
        service.resign();

        // Then
        assertThat(gameRepository.findLatestGame().get().isFinished()).isTrue();
    }
}