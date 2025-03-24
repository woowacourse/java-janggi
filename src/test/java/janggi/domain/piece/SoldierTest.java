package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
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

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Soldier(Side.HAN)
        );

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

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Soldier(Side.CHO)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }
}
