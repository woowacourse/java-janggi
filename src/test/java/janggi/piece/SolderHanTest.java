package janggi.piece;

import janggi.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolderHanTest {
    @DisplayName("병이 이동 가능한 position 목록 반환 확인")
    @Test
    void solderHanPositionTest() {
        Piece soldierHan = new SoldierHan(new Position(4, 3));
        List<Position> possibleMoves = soldierHan.checkPossibleMoves();
        List<Position> expectedPossibleMoves = List.of(
                new Position(5, 3),
                new Position(4, 2),
                new Position(3, 3)
        );
        assertThat(possibleMoves).containsAll(expectedPossibleMoves);
    }
}
