package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.factory.PieceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @DisplayName("움직일 수 있는 기물인지 확인한다.")
    @Test
    void test1() {
        // given
        Board board = new Board(PieceFactory.initialize());
        Position position = Position.of(7, 1);

        // when & then
        assertThatCode(() -> board.checkMoveablePiece(Side.CHO, position))
                .doesNotThrowAnyException();
    }

    @DisplayName("해당 포지션에 기물이 존재하지 않으면 예외를 반환한다.")
    @Test
    void test2() {
        // given
        Board board = new Board(PieceFactory.initialize());
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
        Board board = new Board(PieceFactory.initialize());
        Position position = Position.of(1, 1);

        // when & then
        assertThatThrownBy(() -> board.checkMoveablePiece(Side.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.IS_NOT_SAME_SIDE.getMessage());
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void test4() {
        // given
        Position startingPosition = Position.of(5, 1);
        Piece startingPiece = new Soldier(Side.CHO);
        Position endPosition = Position.of(4, 1);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);
        Board board = new Board(startingPieces);

        // when & then
        assertThatCode(() -> board.movePiece(startingPosition, endPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("움직일 포지션에 우리 팀의 기물이 존재하면 예외를 발생한다.")
    @Test
    void test5() {
        // given
        Position startingPosition = Position.of(5, 1);
        Piece startingPiece = new Soldier(Side.CHO);
        Position endPosition = Position.of(4, 1);
        Piece endPiece = new Soldier(Side.CHO);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, endPiece);
        Board board = new Board(startingPieces);

        // when & then
        assertThatThrownBy(() -> board.movePiece(startingPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
    }

    @DisplayName("상대의 기물이 있는 곳으로 움직일 수 있다.")
    @Test
    void test6() {
        // given
        Position startingPosition = Position.of(5, 1);
        Piece startingPiece = new Soldier(Side.CHO);
        Position endPosition = Position.of(4, 1);
        Piece endPiece = new Soldier(Side.HAN);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, endPiece);
        Board board = new Board(startingPieces);

        // when & then
        assertThatCode(() -> board.movePiece(startingPosition, endPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("보드의 General이 있다면 true를 반환한다.")
    @Test
    void test7() {
        // given
        Board board = new Board(PieceFactory.initialize());

        // when
        boolean actual = board.hasGeneral();

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("보드의 General이 없다면 false를 반환한다.")
    @Test
    void test8() {
        // given
        Board board = new Board(new HashMap<>());

        // when
        boolean actual = board.hasGeneral();

        // then
        assertThat(actual).isFalse();
    }
}
