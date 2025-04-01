package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.PieceEntity;
import janggi.dao.entity.Status;
import janggi.domain.board.ChuBoardSetUp;
import janggi.domain.board.HanBoardSetUp;
import janggi.domain.board.JanggiBoard;
import janggi.domain.gamestatus.GameEnded;
import janggi.domain.gamestatus.GameRunned;
import janggi.domain.gamestatus.GameStatus;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.General;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Point;
import janggi.fake.FakeGameDao;
import janggi.fake.FakePieceDao;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiServiceTest {

    @DisplayName("현재 실행중인 게임을 찾는다.")
    @Test
    void findRunningGame() {
        //given
        GameEntity gameEntity = new GameEntity(1L, "abc", Status.RUN, Dynasty.HAN);
        JanggiService janggiService = new JanggiService(new FakeGameDao(gameEntity), new FakePieceDao());

        //when
        GameEntity runningGame = janggiService.getRunningGameByName("abc");

        //then
        assertThat(runningGame).isEqualTo(gameEntity);
    }

    @DisplayName("게임 생성시 이미 존재하는 이름의 게임이라면 예외가 발생한다.")
    @Test
    void createGame_isAlreadyExistName_throwException() {
        //given
        GameDao gameDao = new FakeGameDao(new GameEntity(1L, "abc", Status.END, Dynasty.HAN));
        PieceDao pieceDao = new FakePieceDao();
        JanggiService janggiService = new JanggiService(gameDao, pieceDao);

        //when & then
        Assertions.assertThatThrownBy(
                        () -> janggiService.createGame("abc", HanBoardSetUp.INNER_ELEPHANT, ChuBoardSetUp.INNER_ELEPHANT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 게임 이름입니다.");
    }

    @DisplayName("게임을 만든다.")
    @Test
    void createGame() {
        //given
        GameDao gameDao = new FakeGameDao();
        PieceDao pieceDao = new FakePieceDao();
        JanggiService janggiService = new JanggiService(gameDao, pieceDao);

        //when
        janggiService.createGame("abc", HanBoardSetUp.INNER_ELEPHANT, ChuBoardSetUp.INNER_ELEPHANT);

        //then
        assertThat(gameDao.findById(1L)).hasValue(new GameEntity(1L, "abc", Status.RUN, Dynasty.CHU));
        assertThat(pieceDao.findAllByGameId(1L))
                .extracting("point", "dynasty", "pieceType", "gameId")
                .containsExactlyInAnyOrder(
                        tuple(new Point(8, 8), Dynasty.CHU, PieceType.CANNON, 1L),
                        tuple(new Point(4, 5), Dynasty.HAN, PieceType.SOLDIER, 1L),
                        tuple(new Point(2, 5), Dynasty.HAN, PieceType.GENERAL, 1L),
                        tuple(new Point(4, 7), Dynasty.HAN, PieceType.SOLDIER, 1L),
                        tuple(new Point(4, 9), Dynasty.HAN, PieceType.SOLDIER, 1L),
                        tuple(new Point(7, 1), Dynasty.CHU, PieceType.SOLDIER, 1L),
                        tuple(new Point(9, 5), Dynasty.CHU, PieceType.GENERAL, 1L),
                        tuple(new Point(7, 3), Dynasty.CHU, PieceType.SOLDIER, 1L),
                        tuple(new Point(7, 5), Dynasty.CHU, PieceType.SOLDIER, 1L),
                        tuple(new Point(3, 2), Dynasty.HAN, PieceType.CANNON, 1L),
                        tuple(new Point(1, 1), Dynasty.HAN, PieceType.CHARIOT, 1L),
                        tuple(new Point(7, 7), Dynasty.CHU, PieceType.SOLDIER, 1L),
                        tuple(new Point(1, 2), Dynasty.HAN, PieceType.HORSE, 1L),
                        tuple(new Point(1, 3), Dynasty.HAN, PieceType.ELEPHANT, 1L),
                        tuple(new Point(7, 9), Dynasty.CHU, PieceType.SOLDIER, 1L),
                        tuple(new Point(1, 4), Dynasty.HAN, PieceType.GUARD, 1L),
                        tuple(new Point(1, 6), Dynasty.HAN, PieceType.GUARD, 1L),
                        tuple(new Point(3, 8), Dynasty.HAN, PieceType.CANNON, 1L),
                        tuple(new Point(1, 7), Dynasty.HAN, PieceType.ELEPHANT, 1L),
                        tuple(new Point(1, 8), Dynasty.HAN, PieceType.HORSE, 1L),
                        tuple(new Point(1, 9), Dynasty.HAN, PieceType.CHARIOT, 1L),
                        tuple(new Point(10, 1), Dynasty.CHU, PieceType.CHARIOT, 1L),
                        tuple(new Point(10, 2), Dynasty.CHU, PieceType.HORSE, 1L),
                        tuple(new Point(10, 3), Dynasty.CHU, PieceType.ELEPHANT, 1L),
                        tuple(new Point(10, 4), Dynasty.CHU, PieceType.GUARD, 1L),
                        tuple(new Point(8, 2), Dynasty.CHU, PieceType.CANNON, 1L),
                        tuple(new Point(10, 6), Dynasty.CHU, PieceType.GUARD, 1L),
                        tuple(new Point(4, 1), Dynasty.HAN, PieceType.SOLDIER, 1L),
                        tuple(new Point(10, 7), Dynasty.CHU, PieceType.ELEPHANT, 1L),
                        tuple(new Point(10, 8), Dynasty.CHU, PieceType.HORSE, 1L),
                        tuple(new Point(4, 3), Dynasty.HAN, PieceType.SOLDIER, 1L),
                        tuple(new Point(10, 9), Dynasty.CHU, PieceType.CHARIOT, 1L)
                );
    }

    @DisplayName("게임 id로 JaggiStatus를 가져올때 게임 id에 해당하는 게임이 없다면 예외를 발생시킨다.")
    @Test
    void findJaggiStatusByGameId_whenNotFoundGameId() {
        //given
        JanggiService janggiService = new JanggiService(new FakeGameDao(), new FakePieceDao());

        //when & then
        assertThatThrownBy(() -> janggiService.findJanggiStatusByGameId(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id에 해당하는 게임이 존재하지 않습니다.");

    }

    @DisplayName("게임 id로 JaggiStatus를 가져올수 있다.")
    @Test
    void findJanggiStatusByGameId() {
        //given
        GameDao gameDao = new FakeGameDao(
                new GameEntity(1L, "abc", Status.RUN, Dynasty.HAN)
        );
        PieceDao pieceDao = new FakePieceDao(
                new PieceEntity(1L, new Point(1, 1), Dynasty.HAN, PieceType.GENERAL, 1L),
                new PieceEntity(2L, new Point(4, 1), Dynasty.CHU, PieceType.GENERAL, 1L)
        );
        JanggiService janggiService = new JanggiService(gameDao, pieceDao);

        //when
        GameStatus gameStatus = janggiService.findJanggiStatusByGameId(1L);

        //then
        assertThat(gameStatus).isEqualTo(new GameRunned(Dynasty.HAN, new JanggiBoard(Map.of(
                new Point(1, 1), new General(Dynasty.HAN),
                new Point(4, 1), new General(Dynasty.CHU)
        ))));
    }

    @DisplayName("기물을 움직일 수 있다.")
    @Test
    void move() {
        //given
        GameDao gameDao = new FakeGameDao(
                new GameEntity(1L, "abc", Status.RUN, Dynasty.HAN)
        );
        PieceDao pieceDao = new FakePieceDao(
                new PieceEntity(1L, new Point(1, 4), Dynasty.HAN, PieceType.GENERAL, 1L),
                new PieceEntity(2L, new Point(10, 4), Dynasty.CHU, PieceType.GENERAL, 1L)
        );
        JanggiService janggiService = new JanggiService(gameDao, pieceDao);

        //when
        GameStatus result = janggiService.move(1L, new Point(1, 4), new Point(1, 5));

        //then
        assertThat(result).isEqualTo(new GameRunned(Dynasty.CHU, new JanggiBoard(Map.of(
                new Point(1, 5), new General(Dynasty.HAN),
                new Point(10, 4), new General(Dynasty.CHU)
        ))));
        assertThat(pieceDao.findAllByGameId(1L)).isEqualTo(List.of(
                new PieceEntity(1L, new Point(1, 5), Dynasty.HAN, PieceType.GENERAL, 1L),
                new PieceEntity(2L, new Point(10, 4), Dynasty.CHU, PieceType.GENERAL, 1L)
        ));
        assertThat(gameDao.findById(1L)).hasValue(new GameEntity(1L, "abc", Status.RUN, Dynasty.CHU));
    }

    @DisplayName("기물을 움직이고 상대편 말이 죽었다면 게임이 끝난다.")
    @Test
    void move_gameEnd() {
        //given
        GameDao gameDao = new FakeGameDao(
                new GameEntity(1L, "abc", Status.RUN, Dynasty.CHU)
        );
        PieceDao pieceDao = new FakePieceDao(
                new PieceEntity(1L, new Point(1, 4), Dynasty.HAN, PieceType.GENERAL, 1L),
                new PieceEntity(2L, new Point(10, 4), Dynasty.CHU, PieceType.GENERAL, 1L),
                new PieceEntity(3L, new Point(1, 3), Dynasty.CHU, PieceType.CHARIOT, 1L)
        );
        JanggiService janggiService = new JanggiService(gameDao, pieceDao);

        //when
        GameStatus result = janggiService.move(1L, new Point(1, 3), new Point(1, 4));

        //then
        assertThat(result).isEqualTo(new GameEnded(Dynasty.CHU, new JanggiBoard(Map.of(
                new Point(1, 4), new Chariot(Dynasty.CHU),
                new Point(10, 4), new General(Dynasty.CHU)
        ))));
        assertThat(pieceDao.findAllByGameId(1L)).isEqualTo(List.of(
                new PieceEntity(2L, new Point(10, 4), Dynasty.CHU, PieceType.GENERAL, 1L),
                new PieceEntity(3L, new Point(1, 4), Dynasty.CHU, PieceType.CHARIOT, 1L)
        ));
        assertThat(gameDao.findById(1L)).hasValue(new GameEntity(1L, "abc", Status.END, Dynasty.CHU));
    }

    @DisplayName("게임 id에 해당하는 장기판을 가져온다.")
    @Test
    void findJanggiBoardByGameId() {
        //given
        GameDao gameDao = new FakeGameDao(
                new GameEntity(1L, "abc", Status.RUN, Dynasty.CHU)
        );
        PieceDao pieceDao = new FakePieceDao(
                new PieceEntity(1L, new Point(1, 4), Dynasty.HAN, PieceType.GENERAL, 1L),
                new PieceEntity(2L, new Point(10, 4), Dynasty.CHU, PieceType.GENERAL, 1L)
        );
        JanggiService janggiService = new JanggiService(gameDao, pieceDao);

        //when
        JanggiBoard janggiBoard = janggiService.findJanggiBoardByGameId(1L);

        //then
        assertThat(janggiBoard).isEqualTo(new JanggiBoard(Map.of(
                new Point(1, 4), new General(Dynasty.HAN),
                new Point(10, 4), new General(Dynasty.CHU))));
    }
}