package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.common.ErrorMessage;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import janggi.factory.PieceFactory;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("포지션에 해당하는 기물을 반환한다.")
    @Test
    void test1() {
        // given
        Map<Position, PieceState> initialize = PieceFactory.initialize();

        PieceBehavior pieceBehavior = PieceFactory.GENERAL1.getPieceBehavior();
        Board board = new Board(initialize);
        Position position = Position.of(9, 5);

        // when
        PieceState actual = board.getPiecePositionBySameSide(Side.CHO, position);
        PieceState expected = new PieceState(position, new Piece(Side.CHO, pieceBehavior));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("해당 포지션에 기물이 존재하지 않으면 예외를 반환한다.")
    @Test
    void test2() {
        // given
        Map<Position, PieceState> initialize = PieceFactory.initialize();
        Board board = new Board(initialize);
        Position position = Position.of(2, 1);

        // when & then
        assertThatThrownBy(() -> board.getPiecePositionBySameSide(Side.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
    }

    @DisplayName("상대의 기물이라면 예외를 반환한다.")
    @Test
    void test3() {
        // given
        Map<Position, PieceState> initialize = PieceFactory.initialize();

        Board board = new Board(initialize);
        Position position = Position.of(1, 1);

        // when & then
        assertThatThrownBy(() -> board.getPiecePositionBySameSide(Side.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_SAME_SIDE.getMessage());
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void test4() {
        // given
        Position position = Position.of(5, 1);
        PieceState soldier = new PieceState(position, new Piece(Side.CHO, new Soldier()));
        Map<Position, PieceState> map = Map.of(position, soldier);

        Board board = new Board(new HashMap<>(map));

        Position newPosition = Position.of(4, 1);

        // when & then
        assertThatCode(() -> board.move(soldier, newPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("움직일 포지션에 우리 팀의 기물이 존재하면 예외를 발생한다.")
    @Test
    void test5() {
        // given
        Position position = Position.of(5, 1);
        PieceState soldier1 = new PieceState(position, new Piece(Side.CHO, new Soldier()));

        Position newPosition = Position.of(4, 1);
        PieceState soldier2 = new PieceState(newPosition, new Piece(Side.CHO, new Soldier()));
        Map<Position, PieceState> map = Map.of(position, soldier1, newPosition, soldier2);

        Board board = new Board(new HashMap<>(map));

        // when & then
        assertThatThrownBy(() -> board.move(soldier1, newPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
    }

    @DisplayName("상대의 기물이 있는 곳으로 움직일 수 있다.")
    @Test
    void test6() {
        // given
        Position position = Position.of(5, 1);
        PieceState soldier1 = new PieceState(position, new Piece(Side.CHO, new Soldier()));

        Position newPosition = Position.of(4, 1);
        PieceState soldier2 = new PieceState(newPosition, new Piece(Side.HAN, new Soldier()));
        Map<Position, PieceState> map = Map.of(position, soldier1, newPosition, soldier2);

        Board board = new Board(new HashMap<>(map));

        // when & then
        assertThatCode(() -> board.move(soldier1, newPosition))
                .doesNotThrowAnyException();
    }
}
