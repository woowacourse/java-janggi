package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Piece;
import domain.piece.Po;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {

    @DisplayName("장가판을 가져온다")
    @Test
    void test1() {
        // given
        Po choPo = new Po(Team.CHO);
        Gung choGung = new Gung(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();

        beforeBoard.put(new Position(8, 2), choPo);
        beforeBoard.put(new Position(8, 5), choGung);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiGame game = new JanggiGame(boardGenerator, List.of("플레이어1", "플레이어2"));

        // when
        Map<Position, Piece> boardState = game.getBoardState();

        // then
        assertThat(boardState).isEqualTo(beforeBoard);
    }

    @DisplayName("장기판의 말을 이동시킨다")
    @Test
    void test2() {
        // given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Gung choGung = new Gung(Team.CHO);
        beforeBoard.put(new Position(1, 1), choGung);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), List.of("플레이어1", "플레이어2"));

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(2, 1), choGung);

        // when
        game.move(List.of(1, 1), List.of(2, 1));
        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }


    @DisplayName("동일한 위치로 움직일 경우 예외를 발생시킨다")
    @Test
    void test3() {
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Cha choCha = new Cha(Team.CHO);
        beforeBoard.put(new Position(1, 1), choCha);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), List.of("플레이어1", "플레이어2"));

        assertThatThrownBy(() -> game.move(List.of(1, 1), List.of(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("말을 움직여 주세요");
    }

    @DisplayName("두 궁이 모두 생존하고 있으면 게임은 진행 중이다")
    @Test
    void test5() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Gung(Team.HAN));
        board.put(new Position(1, 2), new Gung(Team.CHO));
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(board), List.of("플레이어1", "플레이어2"));

        // when
        boolean actual = game.isEnd();

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("하나의 궁이라도 죽었으면 게임은 종료되었다")
    @Test
    void test6() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Gung(Team.HAN));
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(board), List.of("플레이어1", "플레이어2"));

        // when
        boolean actual = game.isEnd();

        // then
        assertThat(actual).isTrue();
    }
}
