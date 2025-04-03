package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
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
        Piece choCha = new Piece(Team.CHO, PieceType.CHA, new Position(1, 1));
        beforeBoard.add(choCha);
        JanggiBoard board = JanggiBoard.create(beforeBoard);
        JanggiGame game = JanggiGame.init(1L, board);

        List<Piece> afterBoard = new ArrayList<>();
        afterBoard.add(new Piece(Team.CHO, PieceType.CHA, new Position(2, 1)));

        // when
        game.move(List.of(1, 1), List.of(2, 1));
        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }


    @DisplayName("동일한 위치로 움직일 경우 예외를 발생시킨다")
    @Test
    void test3() {
        List<Piece> beforeBoard = new ArrayList<>();
        Piece choCha = new Piece(Team.CHO, PieceType.CHA, new Position(1, 1));
        beforeBoard.add(choCha);
        JanggiBoard board = JanggiBoard.create(beforeBoard);
        JanggiGame game = JanggiGame.init(1L, board);

        assertThatThrownBy(() -> game.move(List.of(1, 1), List.of(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("말을 움직여 주세요");
    }

    @DisplayName("두 궁이 모두 생존하고 있으면 게임은 진행 중이다")
    @Test
    void test5() {
        // given
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Team.HAN, PieceType.GUNG, new Position(1, 1)));
        pieces.add(new Piece(Team.CHO, PieceType.GUNG, new Position(1, 2)));
        JanggiBoard janggiBoard = JanggiBoard.create(pieces);
        JanggiGame game = JanggiGame.init(1L, janggiBoard);

        // when
        boolean actual = game.isEnd();

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("하나의 궁이라도 죽었으면 게임은 종료되었다")
    @Test
    void test6() {
        // given
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Team.HAN, PieceType.GUNG, new Position(1, 1)));
        JanggiBoard janggiBoard = JanggiBoard.create(pieces);
        JanggiGame game = JanggiGame.init(1L, janggiBoard);

        // when
        boolean actual = game.isEnd();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 모든팀의_점수을_계산할_수_있다() {
        // given
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Team.CHO, PieceType.PAWN, new Position(1, 1)));
        pieces.add(new Piece(Team.CHO, PieceType.MA, new Position(1, 2)));
        pieces.add(new Piece(Team.CHO, PieceType.SANG, new Position(1, 3)));
        pieces.add(new Piece(Team.HAN, PieceType.PAWN, new Position(2, 1)));
        pieces.add(new Piece(Team.HAN, PieceType.GUNG, new Position(2, 2)));
        pieces.add(new Piece(Team.HAN, PieceType.SANG, new Position(2, 3)));
        JanggiBoard janggiBoard = JanggiBoard.create(pieces);
        JanggiGame game = JanggiGame.init(1L, janggiBoard);

        // when
        Map<Team, Double> teamDoubleMap = game.calculateScore();

        // then
        Map<Team, Double> expected = Map.of(Team.CHO, 10.0, Team.HAN, 6.5);
        assertThat(teamDoubleMap).isEqualTo(expected);
    }
}
