package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.movement.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class GuardTest {

    @DisplayName("사는 팀의 기물이 있는 곳으로 이동할 수 없다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(1, 5);
        Piece startingPiece = new Guard(Side.HAN);
        Position endPosition = Position.of(1, 4);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, new Soldier(Side.HAN));

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("사의 이동 경로에 상대 팀 기물이 있으면 이동할 수 있다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(1, 5);
        Piece startingPiece = new Guard(Side.HAN);
        Position endPosition = Position.of(1, 4);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece, endPosition, new Soldier(Side.CHO));

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("사는 궁성 영역 밖으로 이동할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"1,4,1,3", "2,4,2,3", "3,4,4,4", "3,5,4,5", "3,6,4,6", "2,6,2,7", "1,6,1,7"})
    void test3(int startingRow, int startingColumn, int endRow, int endColumn) {
        // given
        Position startingPosition = Position.of(startingRow, startingColumn);
        Piece startingPiece = new Guard(Side.HAN);
        Position endPosition = Position.of(endRow, endColumn);

        Map<Position, Piece> startingPieces = Map.of(startingPosition, startingPiece);

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("사는 3점으로 계산한다.")
    @Test
    void test4() {
        // given
        Side side = Side.CHO;
        Piece piece = new Guard(side);

        // when
        double actual = piece.getPoints();
        double expected = 3;

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
