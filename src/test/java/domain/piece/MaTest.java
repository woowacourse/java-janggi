package domain.piece;

import domain.BoardStatus;
import domain.piece.strategy.MaMoveStrategy;
import domain.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaTest {
    @Test
    @DisplayName("목적지와 출발지 사이에 기물이 있으면 갈 수 없어야 한다")
    void isMovable_fail() {
        //given
        Ma testMa = new Ma(new MaMoveStrategy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(4, 3);
        Position obstacle = Position.of(3, 2);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(obstacle, new Ma(new MaMoveStrategy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatThrownBy(() -> testMa.check(testBoard, start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MoveErrorMessage.PATH_BLOCKED.getMessage());
    }


    @Test
    @DisplayName("목적지와 출발지 사이에 기물이 없으면 갈 수 있어야 한다")
    void isMovable_success() {
        //given
        Ma testMa = new Ma(new MaMoveStrategy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(4, 3);
        HashMap<Position, Piece> testPieces = new HashMap<>();

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> testMa.check(testBoard, start, destination));
    }
}
