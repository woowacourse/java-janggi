package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.dao.BoardDAO;
import janggi.dao.FakeBoardDAO;
import janggi.dao.FakeGameRoomDAO;
import janggi.dao.GameRoomDAO;
import janggi.domain.Board;
import janggi.domain.GameRoom;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.dto.TeamHorseElephantPositionDto;
import janggi.util.BoardFixture;
import janggi.view.HorseElephantPosition;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JanggiServiceTest {

    GameRoomDAO gameRoomDAO;
    BoardDAO boardDAO;
    JanggiService janggiService;

    private static Stream<Arguments> positionArguments() {
        return Stream.of(
                Arguments.of(Position.of(5, 1), Position.of(4, 1)),
                Arguments.of(Position.of(6, 1), Position.of(7, 1)),
                Arguments.of(Position.of(1, 1), Position.of(2, 1))
        );
    }

    @BeforeEach
    void init() {
        gameRoomDAO = new FakeGameRoomDAO();
        boardDAO = new FakeBoardDAO();
        janggiService = new JanggiService(gameRoomDAO, boardDAO);
    }

    @DisplayName("해당 방이 존재하면 예외를 발생한다.")
    @Test
    void test1(){
        // given
        String gameRoomName = "꾹";
        gameRoomDAO.create(gameRoomName);

        // when & then
        assertThatThrownBy(() -> janggiService.validateNewGameRoomName(gameRoomName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모든 방 이름들을 가져온다.")
    @Test
    void test2() {
        // given
        List<String> nameList = List.of("꾹", "줄리", "히로", "새로이");
        for(String name : nameList){
            gameRoomDAO.create(name);
        }

        List<String> result = janggiService.getAllGameRoomName();

        assertThat(result).containsExactlyInAnyOrderElementsOf(nameList);
    }

    @DisplayName("방을 저장한다.")
    @Test
    void test3() {
        // given
        String gameRoomName = "꾹";
        gameRoomDAO.create(gameRoomName);

        // when
        janggiService.saveGameRoom(gameRoomName, Team.HAN);

        // then
        Team result = gameRoomDAO.findTurn(gameRoomName);
        assertThat(result).isEqualTo(Team.HAN);
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void test4() {
        // given
        String gameRoomName = "꾹";
        Board board = BoardFixture.sangMaSangMa();
        GameRoom gameRoom = new GameRoom(gameRoomName, board, Team.CHO);
        boardDAO.save(gameRoomName, board);

        // when & then
        assertThatCode(() -> janggiService.movePiece(gameRoom, Position.of(7,1), Position.of(6, 1)))
                .doesNotThrowAnyException();
    }

    @DisplayName("불가능한 움직임을 처리할 시 예외가 발생된다")
    @ParameterizedTest
    @MethodSource("positionArguments")
    void test5(Position currentPosition, Position targetPosition) {
        // given
        String gameRoomName = "꾹";
        Board board = BoardFixture.sangMaSangMa();
        GameRoom gameRoom = new GameRoom(gameRoomName, board, Team.CHO);

        // when & then
        assertThatThrownBy(() -> janggiService.movePiece(gameRoom, currentPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class existRoom {

        @DisplayName("방이 존재하지 않는다면 예외를 발생한다.")
        @Test
        void test1() {
            assertThatThrownBy(() -> janggiService.checkExistRoom())
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("방이 존재하면 예외가 발생하지 않는다.")
        @Test
        void test2() {
            // given
            gameRoomDAO.create("꾹");

            // when & then
            assertThatCode(() -> janggiService.checkExistRoom())
                    .doesNotThrowAnyException();
        }
    }

    @DisplayName("새로운 방 생성")
    @Nested
    class create {

        @DisplayName("생성된 방은 초나라여야 한다.")
        @Test
        void test1() {
            // given
            String gameRoomName = "꾹";
            TeamHorseElephantPositionDto horseElephantPositionByCho = new TeamHorseElephantPositionDto(Team.CHO,
                    HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE);

            TeamHorseElephantPositionDto horseElephantPositionByHan = new TeamHorseElephantPositionDto(Team.HAN,
                    HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE);

            // when
            GameRoom result = janggiService.newGameRoom(gameRoomName, horseElephantPositionByCho,
                    horseElephantPositionByHan);

            assertThat(result.turn()).isEqualTo(Team.CHO);
        }

        @DisplayName("이미 방이 존재하면 예외를 발생한다.")
        @Test
        void test2() {
            // given
            String gameRoomName = "꾹";
            TeamHorseElephantPositionDto teamMaSangPositionByCho = new TeamHorseElephantPositionDto(Team.CHO,
                    HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE);

            TeamHorseElephantPositionDto teamMaSangPositionByHan = new TeamHorseElephantPositionDto(Team.HAN,
                    HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE);

            gameRoomDAO.create(gameRoomName);

            assertThatThrownBy(
                    () -> janggiService.newGameRoom(gameRoomName, teamMaSangPositionByCho, teamMaSangPositionByHan))
                    .isInstanceOf(IllegalArgumentException.class);

        }

        @DisplayName("정해진 나라로 값을 받아와야 한다")
        @Test
        void test3() {
            String gameRoomName = "꾹";
            TeamHorseElephantPositionDto teamMaSangPositionByCho = new TeamHorseElephantPositionDto(Team.CHO,
                    HorseElephantPosition.ELEPHANT_HORSE_ELEPHANT_HORSE);

            assertThatThrownBy(
                    () -> janggiService.newGameRoom(gameRoomName, teamMaSangPositionByCho, teamMaSangPositionByCho))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @DisplayName("기존의 방을 가져온다.")
    @Nested
    class load {

        @DisplayName("기존의 방을 불러온다")
        @Test
        void test1() {
            // given
            String gameRoomName = "꾹";
            Board board = BoardFixture.sangMaSangMa();
            boardDAO.save(gameRoomName, board);
            gameRoomDAO.create(gameRoomName);

            // when
            GameRoom gameRoom = janggiService.loadGameRoom(gameRoomName);

            assertAll(() -> assertThat(gameRoom.turn()).isEqualTo(Team.CHO),
                    () -> assertThat(gameRoom.name()).isEqualTo(gameRoomName),
                    () -> assertThat(gameRoom.board()).isEqualTo(board)
            );
        }

        @DisplayName("기존의 방이 존재하지 않으면 예외를 발생한다.")
        @Test
        void test2() {
            String gameRoomName = "꾹";

            assertThatThrownBy(() -> janggiService.loadGameRoom(gameRoomName))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("게임 끝났다면 방을 삭제한다.")
    @Nested
    class delete {

        String gameRoomName;
        GameRoom gameRoom;

        @BeforeEach
        void init() {
            gameRoomName = "꾹";
            gameRoomDAO.create(gameRoomName);
            Board board = BoardFixture.sangMaSangMa();

            gameRoom = new GameRoom(gameRoomName, board, Team.CHO);
        }

        @DisplayName("게임이 끝나지 않았다면 삭제하지 않는다.")
        @Test
        void test1() {

            // when
            janggiService.deleteGameRoomIfEnd(gameRoom, true);

            // then
            assertThat(gameRoomDAO.exist(gameRoomName)).isTrue();
        }

        @DisplayName("상대의 왕이 죽었다면 게임을 삭제한다.")
        @Test
        void test3() {
            // given
            Board board = new Board(Map.of());
            gameRoom = new GameRoom(gameRoomName, board, Team.CHO);

            // when
            janggiService.deleteGameRoomIfEnd(gameRoom, true);

            assertThat(gameRoomDAO.exist(gameRoomName)).isFalse();
        }
    }
}
