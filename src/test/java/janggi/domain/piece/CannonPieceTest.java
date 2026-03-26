package janggi.domain.piece;

import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CannonPieceTest {

    @ParameterizedTest
    @DisplayName("포는 상하좌우 직전으로 칸 수 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 5, 3",
            "8, 3, 5, 3",
            "2, 3, 2, 8",
            "8, 3, 8, 1",
    })
    void testMoveCannon(int preX, int preY, int nextX, int nextY) {
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        assertThat(cannonPiece.canMove(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @ParameterizedTest
    @DisplayName("포는 상하좌우 직전이면 칸 수에 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 6, 4",
            "8, 3, 9, 1",
            "2, 3, 3, 6",
            "8, 3, 2, 4",
    })
    void testNotMovableCannon(int preX, int preY, int nextX, int nextY) {
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        assertThat(cannonPiece.canMove(new Position(preX, preY), new Position(nextX, nextY)))
                .isFalse();
    }

    @Test
    @DisplayName("포는 상하좌우 직전이면 칸 수에 상관없이 이동 가능하다.")
    void testFindDestinationPath() {
        Position from = new Position(2, 3);
        Position to = new Position(5, 3);
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        List<Position> result = cannonPiece.findPath(from, to);
        assertThat(result).containsExactly(new Position(3, 3), new Position(4, 3), new Position(5, 3));
    }
}
