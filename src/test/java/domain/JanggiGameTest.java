package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.player.Player;
import domain.player.Players;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {

    private Players players;

    @BeforeEach
    void setUp() {
        players = new Players(List.of(new Player("플레이어1", Team.BLUE), new Player("플레이어2", Team.RED)));
    }

    @DisplayName("장가판을 가져온다")
    @Test
    void test1() {

        // given
        Cannon blueCannon = new Cannon(Team.BLUE);
        King blueKing = new King(Team.BLUE);

        Map<Position, Piece> beforeBoard = new HashMap<>();

        beforeBoard.put(new Position(8, 2), blueCannon);
        beforeBoard.put(new Position(8, 5), blueKing);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiGame game = new JanggiGame(boardGenerator, players);
        // when
        Map<Position, Piece> boardState = game.getBoardState();

        // then
        assertThat(boardState).isEqualTo(beforeBoard);
    }

    @DisplayName("장기판의 말을 이동시킨다 - 궁")
    @Test
    void test2() {
        // given
        King blueKing = new King(Team.BLUE);
        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(1, 1), blueKing);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), players);

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(2, 1), blueKing);

        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(2, 1);
        // when
        game.move(startPosition, targetPosition);
        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @DisplayName("장기판의 말을 이동시킨다 - 차")
    @Test
    void test24() {
        // given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Chariot blueChariot = new Chariot(Team.BLUE);
        beforeBoard.put(new Position(1, 1), blueChariot);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), players);

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(2, 1), blueChariot);

        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(2, 1);
        // when
        game.move(startPosition, targetPosition);
        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @DisplayName("장기판의 말을 이동시킨다 - 마")
    @Test
    void test25() {
        // given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Horse blueHorse = new Horse(Team.BLUE);
        beforeBoard.put(new Position(1, 1), blueHorse);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), players);

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(3, 2), blueHorse);

        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(3, 2);

        // when
        game.move(startPosition, targetPosition);

        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }


    @DisplayName("동일한 위치로 움직일 경우 예외를 발생시킨다")
    @Test
    void test3() {
        //given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Chariot blueChariot = new Chariot(Team.BLUE);
        beforeBoard.put(new Position(1, 1), blueChariot);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), players);

        //when & then
        assertThatThrownBy(() -> game.move(new Position(1, 1), new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("말을 움직여 주세요");
    }

    @DisplayName("장기말 선택시 그 자리에 장기말이 없으면 예외를 발생시킨다")
    @Test
    void test4() {

        //given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Chariot blueChariot = new Chariot(Team.BLUE);
        beforeBoard.put(new Position(1, 1), blueChariot);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), players);

        //when & then
        assertThatThrownBy(() -> game.move(new Position(1, 2), new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 자리에는 말이 없습니다.");
    }

    @DisplayName("장기말 선택시 같은 팀의 말이 아니면 예외를 발생시킨다")
    @Test
    void test5() {
        //given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Chariot blueChariot = new Chariot(Team.RED);
        beforeBoard.put(new Position(1, 1), blueChariot);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), players);

        //when & then
        assertThatThrownBy(() -> game.move(new Position(1, 1), new Position(1, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 말만 움직일 수 있습니다.");
    }

    @DisplayName("장기판의 왕의 생존여부를 확인한다.")
    @Test
    void test6() {
        //given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        King redKing = new King(Team.RED);
        beforeBoard.put(new Position(2, 5), redKing);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), players);

        //when & then
        boolean isKingDead = game.checkKingIsDead();
        Assertions.assertThat(isKingDead).isTrue();
    }

}
