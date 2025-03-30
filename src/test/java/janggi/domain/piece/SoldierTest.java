package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {

    @DisplayName("병은 전진하거나 좌우로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"3,2", "4,3", "3,4"})
    void test1(int row, int column) {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Soldier(Side.HAN);
        Position endPosition = Position.of(row, column);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("병은 후진할 수 없다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Soldier(Side.HAN);
        Position endPosition = Position.of(2, 3);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("병은 팀의 기물이 있는 곳으로 이동할 수 없다.")
    @Test
    void test3() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Soldier(Side.HAN);
        Position endPosition = Position.of(3, 2);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, new Soldier(Side.HAN));

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("병의 이동 경로에 상대 팀 기물이 있으면 이동할 수 있다.")
    @Test
    void test4() {
        // given
        Position startingPosition = Position.of(3, 3);
        Piece startingPiece = new Soldier(Side.HAN);
        Position endPosition = Position.of(3, 2);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, new Soldier(Side.CHO));

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("병은 궁성 영역 안에서 대각선으로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"3,4,2,5", "3,6,2,5"})
    void test5(int startingRow, int startingColumn, int endRow, int endColumn) {
        // given
        Position startingPosition = Position.of(startingRow, startingColumn);
        Piece startingPiece = new Soldier(Side.CHO);
        Position endPosition = Position.of(endRow, endColumn);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("병은 궁성 영역 안에서 후퇴할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"1,4,2,5,CHO", "1,6,2,5,CHO", "10,4,9,5,HAN", "10,6,9,5,HAN"})
    void test6(int startingRow, int startingColumn, int endRow, int endColumn, Side side) {
        // given
        Position startingPosition = Position.of(startingRow, startingColumn);
        Piece startingPiece = new Soldier(side);
        Position endPosition = Position.of(endRow, endColumn);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("병은 2점으로 계산한다.")
    @Test
    void test7() {
        // given
        Side side = Side.CHO;
        Piece piece = new Soldier(side);

        // when
        double actual = piece.getPoints();
        double expected = 2;

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
