package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.GameState;
import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.PieceEntity;
import janggi.fake.FakeGameDao;
import janggi.fake.FakePieceDao;
import janggi.piece.Team;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    @DisplayName("진행중인 게임을 찾는다.")
    @Test
    void findInProgressGame() {
        //given
        final GameEntity gameEntity = new GameEntity(1L, Team.CHU, GameState.IN_PROGRESS, 0, 0);
        final JanggiService janggiService = new JanggiService(new FakeGameDao(gameEntity), new FakePieceDao());

        //when
        final GameEntity actual = janggiService.findInProgressGame(GameState.IN_PROGRESS);

        //then
        assertThat(actual).isEqualTo(gameEntity);
    }

    @DisplayName("게임을 생성할 수 있다.")
    @Test
    void creatGame() {
        //given
        final FakeGameDao gameDao = new FakeGameDao();
        final FakePieceDao pieceDao = new FakePieceDao();
        final JanggiService janggiService = new JanggiService(gameDao, pieceDao);
        final Board board = new BoardGenerator().generate();

        //when
        final GameEntity gameEntity = janggiService.creatGame(board, GameState.IN_PROGRESS);
        final List<PieceEntity> pieceEntities = pieceDao.findPiecesById(gameEntity.getId());

        //then
        assertThat(gameEntity.getId()).isEqualTo(1L);
        assertThat(gameEntity.getStatus()).isEqualTo(GameState.IN_PROGRESS);
        assertThat(gameEntity.getCurrentTeam()).isEqualTo(Team.CHU);
        assertThat(gameEntity.getCurrentTeam()).isEqualTo(Team.CHU);
        assertThat(gameEntity.getChuScore()).isEqualTo(72);
        assertThat(gameEntity.getHanScore()).isEqualTo(73.5);

        assertThat(pieceEntities).hasSize(32);
    }

    @DisplayName("장기판을 가져온다.")
    @Test
    void findBoardById() {
        //given
        final FakeGameDao gameDao = new FakeGameDao();
        final FakePieceDao pieceDao = new FakePieceDao();
        final JanggiService janggiService = new JanggiService(gameDao, pieceDao);
        final Board board = new BoardGenerator().generate();

        final GameEntity gameEntity = janggiService.creatGame(board, GameState.IN_PROGRESS);

        //when
        final Board actual = janggiService.findBoardById(gameEntity.getId());

        //then
        assertThat(actual.getJanggiBoard()).hasSize(32);
    }

}
