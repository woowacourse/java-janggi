package janggi.piece;

import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolderChoTest {
    @DisplayName("졸이 이동 가능한 position 목록 반환 확인")
    @Test
    void solderChoPositionTest() {
        Piece solderCho = new SoldierCho(new Position(7,3));
        List<Position> possibleMoves = solderCho.checkPossibleMoves();
        List<Position> expectedPossibleMoves = List.of(
                new Position(8, 3),
                new Position(7, 4),
                new Position(6, 3)
        );
        assertThat(possibleMoves).containsAll(expectedPossibleMoves);
    }
}
