package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.movement.Position;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @DisplayName("현재 차례인 팀의 기물을 선택하면 움직일 수 있다.")
    @Test
    void test1() {
        // given
        Board board = new Board(PieceFactory.initialize());
        Side turn = Side.CHO;
        Position selectedPosition = Position.of(7, 1);
        Position targetPosition = Position.of(6, 1);

        // when & then
        assertThatCode(() -> board.makeMove(turn, selectedPosition, targetPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("해당 포지션에 기물이 존재하지 않으면 예외를 반환한다.")
    @Test
    void test2() {
        // given
        Board board = new Board(PieceFactory.initialize());
        Side turn = Side.CHO;
        Position selectedPosition = Position.of(2, 1);
        Position targetPosition = Position.of(3, 1);

        // when & then
        assertThatThrownBy(() -> board.makeMove(turn, selectedPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
    }

    @DisplayName("상대의 기물을 움직이려고 하면 예외를 반환한다.")
    @Test
    void test3() {
        // given
        Board board = new Board(PieceFactory.initialize());
        Side turn = Side.HAN;
        Position selectedPosition = Position.of(7, 1);
        Position targetPosition = Position.of(6, 1);

        // when & then
        assertThatThrownBy(() -> board.makeMove(turn, selectedPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.IS_NOT_SAME_SIDE.getMessage());
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void test4() {
        // given
        Side turn = Side.CHO;
        Position startingPosition = Position.of(5, 1);
        Piece startingPiece = new Soldier(Side.CHO);
        Position endPosition = Position.of(4, 1);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);
        Board board = new Board(startingPieces);

        // when
        board.makeMove(turn, startingPosition, endPosition);
        Piece actual = board.getPiece(endPosition);

        // when & then
        assertThat(actual).isEqualTo(startingPiece);
    }

    @DisplayName("움직일 포지션에 우리 팀의 기물이 존재하면 예외를 발생한다.")
    @Test
    void test5() {
        // given
        Side turn = Side.CHO;
        Position startingPosition = Position.of(5, 1);
        Piece startingPiece = new Soldier(Side.CHO);
        Position endPosition = Position.of(4, 1);
        Piece endPiece = new Soldier(Side.CHO);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, endPiece);
        Board board = new Board(startingPieces);

        // when & then
        assertThatThrownBy(() -> board.makeMove(turn, startingPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
    }

    @DisplayName("상대의 기물이 있는 곳으로 움직일 수 있다.")
    @Test
    void test6() {
        // given
        Side turn = Side.CHO;
        Position startingPosition = Position.of(5, 1);
        Piece startingPiece = new Soldier(Side.CHO);
        Position endPosition = Position.of(4, 1);
        Piece endPiece = new Soldier(Side.HAN);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, endPiece);
        Board board = new Board(startingPieces);

        // when & then
        assertThatCode(() -> board.makeMove(turn, startingPosition, endPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("보드에 General이 둘 다 있다면 true를 반환한다.")
    @Test
    void test7() {
        // given
        Board board = new Board(PieceFactory.initialize());

        // when
        boolean actual = board.hasBothGenerals();

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("보드에 General이 둘 미만 있다면 false를 반환한다.")
    @Test
    void test8() {
        // given
        Board board = new Board(new HashMap<>());

        // when
        boolean actual = board.hasBothGenerals();

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("장기판에 남아있는 기물의 점수 합을 구한다.")
    @Test
    void test9() {
        // given
        Side side = Side.HAN;
        Map<Position, Piece> startingPieces = Map.of(
                Position.of(1, 1), new Soldier(side),
                Position.of(1, 2), new Guard(side),
                Position.of(1, 3), new Elephant(side),
                Position.of(1, 4), new Horse(side),
                Position.of(1, 5), new Cannon(side),
                Position.of(1, 6), new Chariot(side)
        );
        Board board = new Board(startingPieces);

        // when
        double actual = board.getTotalPoints(side);
        double expected = 33;

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
