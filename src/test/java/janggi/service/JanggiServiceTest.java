package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.dao.PieceEntity;
import janggi.domain.Board;
import janggi.domain.GameSession;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JanggiServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private GameDao gameDao;

    @Mock
    private PieceDao pieceDao;

    @Mock
    private Connection connection;

    @Mock
    private Board board;

    @InjectMocks
    private JanggiService janggiService;

    @Test
    void 새_게임_생성시_game저장_후_piece저장() {
        when(gameDao.save(any())).thenReturn(1L);

        GameSession session = janggiService.createNewGame();

        verify(gameDao).save(Team.initialTeam());
        verify(pieceDao).saveAll(any());
        assertThat(session.gameId()).isEqualTo(1L);
        assertThat(session.turn()).isEqualTo(Team.initialTeam());
        assertThat(session.board()).isNotNull();
    }

    @Test
    void 과거_게임_호출시_검증후_board_turn_조회() {
        long gameId = 1L;
        List<PieceEntity> pieceEntities = List.of(
            new PieceEntity(gameId, 4, 1, Team.CHO, PieceType.KING),
            new PieceEntity(gameId, 4, 8, Team.HAN, PieceType.KING)
        );

        when(gameDao.existsById(gameId)).thenReturn(true);
        when(pieceDao.findByGameId(gameId)).thenReturn(pieceEntities);
        when(gameDao.findTurnByGameId(gameId)).thenReturn(Team.HAN);

        GameSession session = janggiService.loadPastGame(gameId);

        verify(gameDao).existsById(gameId);
        verify(pieceDao).findByGameId(gameId);
        verify(gameDao).findTurnByGameId(gameId);
        assertThat(session.gameId()).isEqualTo(gameId);
        assertThat(session.turn()).isEqualTo(Team.HAN);
        assertThat(session.board()).isNotNull();
    }

    @Test
    void 존재하지_않는_게임_불러오면_예외() {
        long gameId = 999L;

        when(gameDao.existsById(gameId)).thenReturn(false);

        assertThatThrownBy(() -> janggiService.loadPastGame(gameId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 존재하지 않는 게임입니다.");
        verify(gameDao).existsById(gameId);
        verify(pieceDao, never()).findByGameId(anyLong());
        verify(gameDao, never()).findTurnByGameId(anyLong());
    }

    @Test
    void 기물이동_중_예외_발생시_롤백() throws Exception {
        long gameId = 1L;
        Position from = new Position(0, 3);
        Position to = new Position(0, 4);

        when(gameDao.existsById(gameId)).thenReturn(true);
        when(gameDao.findTurnByGameId(gameId)).thenReturn(Team.CHO);
        when(dataSource.getConnection()).thenReturn(connection);
        doThrow(new RuntimeException())
            .when(pieceDao).move(connection, gameId, from, to);

        assertThatThrownBy(() -> janggiService.move(gameId, board, from, to))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("[ERROR] 이동 저장 중 오류가 발생했습니다.");

        verify(connection).rollback();
    }
}
