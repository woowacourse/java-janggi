package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.Side;
import janggi.factory.PieceFactory;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("포지션에 해당하는 기물을 반환한다.")
    @Test
    void test1() {
        // given
        Map<Position, PiecePosition> initialize = PieceFactory.initialize();
        PieceBehavior pieceBehavior = PieceFactory.GENERAL1.getPieceBehavior();
        Board board = new Board(initialize);
        Position position = new Position(9, 5);

        // when
        PiecePosition actual = board.getPiecePosition(Side.CHO, position);
        PiecePosition expected = new PiecePosition(position, new Piece(Side.CHO, pieceBehavior));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("해당 포지션에 기물이 존재하지 않으면 예외를 반환한다.")
    @Test
    void test2() {
        Map<Position, PiecePosition> initialize = PieceFactory.initialize();
        Board board = new Board(initialize);
        Position position = new Position(2, 1);

        assertThatThrownBy(() -> board.getPiecePosition(Side.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
    }

    @DisplayName("상대의 기물이라면 예외를 반환한다.")
    @Test
    void test3() {
        Map<Position, PiecePosition> initialize = PieceFactory.initialize();
        Board board = new Board(initialize);
        Position position = new Position(1, 1);

        assertThatThrownBy(() -> board.getPiecePosition(Side.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_SAME_SIDE.getMessage());
    }
}
