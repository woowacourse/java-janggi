package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import janggi.factory.PieceFactory;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("움직일 수 있는 기물인지 확인한다.")
    @Test
    void test1() {
        // given
        Map<Position, Piece> initialize = PieceFactory.initialize();
        Board board = new Board(initialize);
        Position position = Position.of(7, 1);

        // when & then
        assertThatCode(() -> board.checkMoveablePiece(Side.CHO, position))
                .doesNotThrowAnyException();
    }

    @DisplayName("해당 포지션에 기물이 존재하지 않으면 예외를 반환한다.")
    @Test
    void test2() {
        // given
        Map<Position, Piece> initialize = PieceFactory.initialize();
        Board board = new Board(initialize);
        Position position = Position.of(2, 1);

        // when & then
        assertThatThrownBy(() -> board.checkMoveablePiece(Side.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
    }

    @DisplayName("상대의 기물이라면 예외를 반환한다.")
    @Test
    void test3() {
        // given
        Map<Position, Piece> initialize = PieceFactory.initialize();

        Board board = new Board(initialize);
        Position position = Position.of(1, 1);

        // when & then
        assertThatThrownBy(() -> board.checkMoveablePiece(Side.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_SAME_SIDE.getMessage());
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void test4() {
        // given
        Position position = Position.of(5, 1);
        Piece soldier = new Piece(Side.CHO, new Soldier());
        Map<Position, Piece> map = Map.of(position, soldier);

        Board board = new Board(new HashMap<>(map));

        Position newPosition = Position.of(4, 1);

        // when & then
        assertThatCode(() -> board.move(position, newPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("움직일 포지션에 우리 팀의 기물이 존재하면 예외를 발생한다.")
    @Test
    void test5() {
        // given
        Position position = Position.of(5, 1);
        Piece soldier1 = new Piece(Side.CHO, new Soldier());

        Position newPosition = Position.of(4, 1);
        Piece soldier2 = new Piece(Side.CHO, new Soldier());
        Map<Position, Piece> map = Map.of(position, soldier1, newPosition, soldier2);

        Board board = new Board(new HashMap<>(map));

        // when & then
        assertThatThrownBy(() -> board.move(position, newPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
    }

    @DisplayName("상대의 기물이 있는 곳으로 움직일 수 있다.")
    @Test
    void test6() {
        // given
        Position position = Position.of(5, 1);
        Piece soldier1 = new Piece(Side.CHO, new Soldier());

        Position newPosition = Position.of(4, 1);
        Piece soldier2 = new Piece(Side.HAN, new Soldier());
        Map<Position, Piece> map = Map.of(position, soldier1, newPosition, soldier2);

        Board board = new Board(new HashMap<>(map));

        // when & then
        assertThatCode(() -> board.move(position, newPosition))
                .doesNotThrowAnyException();
    }
}
