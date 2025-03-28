package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Ma;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Sang;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {

    @DisplayName("장기판의 말을 이동시킨다")
    @Test
    void test2() {
        // given
        List<Piece> beforeBoard = new ArrayList<>();
        Cha choCha = new Cha(Team.CHO, new Position(1, 1));
        beforeBoard.add(choCha);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard));

        List<Piece> afterBoard = new ArrayList<>();
        afterBoard.add(new Cha(Team.CHO, new Position(2, 1)));

        // when
        game.move(List.of(1, 1), List.of(2, 1));
        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }


    @DisplayName("동일한 위치로 움직일 경우 예외를 발생시킨다")
    @Test
    void test3() {
        List<Piece> beforeBoard = new ArrayList<>();
        Cha choCha = new Cha(Team.CHO, new Position(1, 1));
        beforeBoard.add(choCha);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard));

        assertThatThrownBy(() -> game.move(List.of(1, 1), List.of(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("말을 움직여 주세요");
    }

    @DisplayName("두 궁이 모두 생존하고 있으면 게임은 진행 중이다")
    @Test
    void test5() {
        // given
        List<Piece> board = new ArrayList<>();
        board.add(new Gung(Team.HAN, new Position(1, 1)));
        board.add(new Gung(Team.CHO, new Position(1, 2)));
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(board));

        // when
        boolean actual = game.isEnd();

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("하나의 궁이라도 죽었으면 게임은 종료되었다")
    @Test
    void test6() {
        // given
        List<Piece> board = new ArrayList<>();
        board.add(new Gung(Team.HAN, new Position(1, 1)));
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(board));

        // when
        boolean actual = game.isEnd();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 모든팀의_점수을_계산할_수_있다() {
        // given
        List<Piece> board = new ArrayList<>();
        board.add(new Pawn(Team.CHO, new Position(1, 1)));
        board.add(new Ma(Team.CHO, new Position(1, 2)));
        board.add(new Sang(Team.CHO, new Position(1, 3)));
        board.add(new Pawn(Team.HAN, new Position(2, 1)));
        board.add(new Gung(Team.HAN, new Position(2, 2)));
        board.add(new Sang(Team.HAN, new Position(2, 3)));
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(board));

        // when
        Map<Team, Double> teamDoubleMap = game.calculateScore();

        // then
        Map<Team, Double> expected = Map.of(Team.CHO, 10.0, Team.HAN, 6.5);
        assertThat(teamDoubleMap).isEqualTo(expected);
    }
}
