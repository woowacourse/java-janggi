package janggi.repository;

import janggi.dao.InMemoryBoardDao;
import janggi.dao.InMemoryJanggiDao;
import janggi.dao.InMemoryPieceDao;
import janggi.domain.Board;
import janggi.domain.GameStatus;
import janggi.domain.JanggiGame;
import janggi.domain.Player;
import janggi.domain.Team;
import janggi.entity.JanggiEntity;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiRepositoryTest {

    @Test
    @DisplayName("JanggiGame 도메인을 저장한다")
    void save() {
        //given
        InMemoryJanggiDao janggiDao = new InMemoryJanggiDao();
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        JanggiRepository janggiRepository = new JanggiRepository(janggiDao, boardDao, pieceDao);
        JanggiGame janggiGame = new JanggiGame(new Board(Map.of()),
                new Player("test1", Team.RED, 0),
                new Player("test2", Team.GREEN, 0),
                Team.GREEN,
                GameStatus.DRAW);

        //when
        janggiRepository.save(janggiGame);

        //then
        JanggiEntity expected = new JanggiEntity(1L,
                "test1",
                "test2",
                0,
                0,
                GameStatus.DRAW.name(),
                Team.GREEN.name());
        JanggiEntity actual = janggiDao.getIdToJanggi().get(1L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("플레이어 이름을 기반으로 데이터가 존재하는지 여부를 확인한다 - 존재하지 않는 경우")
    void notExistsByRedAndGreenPlayerNameAndGameStatus() {
        //given
        InMemoryJanggiDao janggiDao = new InMemoryJanggiDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        JanggiRepository janggiRepository = new JanggiRepository(janggiDao, boardDao, pieceDao);

        //when
        boolean actual = janggiRepository.existsByRedAndGreenPlayerNameAndGameStatus("test1",
                "test2",
                GameStatus.CONTINUE);

        //then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("플레이어 이름을 기반으로 데이터가 존재하는지 여부를 확인한다 - 존재하는 경우")
    void existsByRedAndGreenPlayerNameAndGameStatus() {
        //given
        InMemoryJanggiDao janggiDao = new InMemoryJanggiDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        JanggiRepository janggiRepository = new JanggiRepository(janggiDao, boardDao, pieceDao);
        JanggiGame janggiGame = new JanggiGame(new Board(Map.of()),
                new Player("test1", Team.RED, 0),
                new Player("test2", Team.GREEN, 0),
                Team.GREEN,
                GameStatus.DRAW);
        janggiRepository.save(janggiGame);

        //when
        boolean actual = janggiRepository.existsByRedAndGreenPlayerNameAndGameStatus("test1",
                "test2",
                GameStatus.DRAW);

        //then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("플레이어 이름과 게임 상태를 기반으로 데이터를 찾아서 반환한다")
    void findByRedAndGreenPlayerNameAndGameStatus() {
        //given
        InMemoryJanggiDao janggiDao = new InMemoryJanggiDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        JanggiRepository janggiRepository = new JanggiRepository(janggiDao, boardDao, pieceDao);
        Player red = new Player("test1", Team.RED, 0);
        Player green = new Player("test2", Team.GREEN, 0);
        JanggiGame janggiGame = new JanggiGame(new Board(Map.of()),
                red,
                green,
                Team.GREEN,
                GameStatus.DRAW);
        janggiRepository.save(janggiGame);

        //when
        Optional<JanggiGame> janggiGameOptional = janggiRepository.findByRedAndGreenPlayerNameAndGameStatus(
                "test1",
                "test2",
                GameStatus.DRAW);

        //then
        assertAll(() -> {
            assertThat(janggiGameOptional).isPresent();
            assertThat(janggiGameOptional.get().getRedPlayer().getName()).isEqualTo(red.getName());
            assertThat(janggiGameOptional.get().getGreenPlayer().getName()).isEqualTo(green.getName());
            assertThat(janggiGameOptional.get().getGameStatus()).isEqualTo(GameStatus.DRAW);
            assertThat(janggiGameOptional.get().getTurn()).isEqualTo(Team.GREEN);
        });
    }

    @Test
    @DisplayName("플레이어 이름과 게임 상태를 기반으로 id를 찾아서 반환한다")
    void findJanggiIdByRedAndGreenPlayerNameAndGameStatus() {
        //given
        InMemoryJanggiDao janggiDao = new InMemoryJanggiDao();
        InMemoryBoardDao boardDao = new InMemoryBoardDao();
        InMemoryPieceDao pieceDao = new InMemoryPieceDao();
        JanggiRepository janggiRepository = new JanggiRepository(janggiDao, boardDao, pieceDao);
        Player red = new Player("test1", Team.RED, 0);
        Player green = new Player("test2", Team.GREEN, 0);
        JanggiGame janggiGame = new JanggiGame(new Board(Map.of()),
                red,
                green,
                Team.GREEN,
                GameStatus.DRAW);
        janggiRepository.save(janggiGame);

        //when
        Optional<Long> janggiIdOptional = janggiRepository.findJanggiIdByRedAndGreenPlayerNameAndGameStatus(
                "test1",
                "test2",
                GameStatus.DRAW);

        //then
        assertAll(() -> {
            assertThat(janggiIdOptional).isPresent();
            assertThat(janggiIdOptional.get()).isEqualTo(1L);
        });
    }
}
