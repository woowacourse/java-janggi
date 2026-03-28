package domain.piece;

import common.ErrorMessage;
import domain.BoardStatus;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SangTest {
    @Test
    @DisplayName("목적지와 출발지 사이에 기물이 있으면 갈 수 없어야 한다")
    void isMovable_fail() {
        //given
        Sang testMa = new Sang(new SangMoveStrategy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(5, 4);
        Position obstacle = Position.of(4, 3);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(obstacle, new Sa(new SingleStepMoveStrategy(), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatThrownBy(() -> testMa.check(testBoard, start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.PATH_BLOCKED.getMessage());
    }


    @Test
    @DisplayName("목적지와 출발지 사이에 기물이 없으면 갈 수 있어야 한다")
    void isMovable_success() {
        //given
        Sang testMa = new Sang(new SangMoveStrategy(), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(5, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> testMa.check(testBoard, start, destination));
    }

}