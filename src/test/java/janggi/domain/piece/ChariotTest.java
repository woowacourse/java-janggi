package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotTest {

    @DisplayName("차는 상하좌우 직선으로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"5,3", "5,7", "2,5", "7,5"})
    void test1(int row, int column) {
        // given
        Position startingPosition = Position.of(5, 5);
        Piece startingPiece = new Chariot(Side.HAN);
        Position endPosition = Position.of(row, column);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("차 앞에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(10, 1);
        Piece startingPiece = new Chariot(Side.HAN);
        Position endPosition = Position.of(10, 5);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                Position.of(10, 2), new Soldier(Side.HAN)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("차 앞에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position startingPosition = Position.of(10, 1);
        Piece startingPiece = new Chariot(Side.HAN);
        Position endPosition = Position.of(7, 1);

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
