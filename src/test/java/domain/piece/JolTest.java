package domain.piece;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.BoardStatus;
import domain.piece.strategy.JolMoveStrategy;
import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JolTest {

    @Test
    @DisplayName("목적지와 출발지 사이에 상대 기물이 존재할 수 없으므로 잘 간다고 판단한다")
    void isMovable_success() {
        //given
        Jol testJol = new Jol(new JolMoveStrategy(new PalaceMoveRule()), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 3);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        assertDoesNotThrow(
                () -> testJol.check(testBoard, start, destination)
        );
    }
}
