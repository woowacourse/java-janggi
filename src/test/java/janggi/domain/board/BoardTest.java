package janggi.domain.board;

import static janggi.domain.board.HorseElephantPosition.HEHE;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    public void 특정_위치에_있는_기물이_움직일_수_있는_위치들을_올바르게_반환한다() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = Board.from(policy);

        // when & then
        assertThatCode(() -> board.placeablePositions(Position.from(4, 5), CHO))
                .doesNotThrowAnyException();
    }

    @Test
    public void 상대_팀의_기물을_움직이려하는_경우에는_오류를_일으킨다() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = Board.from(policy);

        // when & then
        assertThatThrownBy(() -> board.placeablePositions(Position.from(4, 5), HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치의 기물은 상대 팀의 기물입니다.");
    }

    @Test
    public void 해당_위치에_기물이_없는_경우에는_오류를_일으킨다() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = Board.from(policy);

        // when & then
        assertThatThrownBy(() -> board.placeablePositions(Position.from(5, 5), CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치에 기물이 존재하지 않습니다.");
    }

    @Test
    public void 특정_위치에_있는_기물을_다른_위치로_옮긴다() {
        // given
        Position from = Position.from(5, 5);
        Piece fromPiece = new Piece(HAN, PieceType.CHARIOT);
        Position toCanEat = Position.from(3, 5);
        Position toCannotEat = Position.from(3, 3);

        BoardDesignPolicy policy = () -> new HashMap<>(Map.of(
                from, fromPiece,
                toCanEat, new Piece(CHO, PieceType.CHARIOT)
        ));
        Board board = Board.from(policy);

        // when
        board.movePiece(from, toCanEat, HAN);
        board.movePiece(toCanEat, toCannotEat, HAN);

        // then
        assertThat(board.pieces())
                .doesNotContainKeys(from, toCanEat)
                .containsEntry(toCannotEat, fromPiece);
    }

    @Test
    public void 특정_기물을_해당_위치로_움직일_수_없으면_에러가_발생한다() {
        // given
        Position from = Position.from(5, 5);
        Piece fromPiece = new Piece(HAN, PieceType.CHARIOT);

        BoardDesignPolicy policy = () -> new HashMap<>(Map.of(
                from, fromPiece
        ));
        Board board = Board.from(policy);
        Position to = Position.from(4, 4);

        // when & then
        assertThatThrownBy(() -> board.movePiece(from, to, HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치에 해당 기물을 옮길 수 없습니다.");
    }

    @Test
    public void 나라_별로_기물의_점수를_계산할_수_있다() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = Board.from(policy);

        // when
        int pointsOfCho = board.sumPointsOf(CHO);
        int pointsOfHan = board.sumPointsOf(HAN);

        // then
        assertThat(pointsOfCho).isEqualTo(72);
        assertThat(pointsOfHan).isEqualTo(72);
    }

}
