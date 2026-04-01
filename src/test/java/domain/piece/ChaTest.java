package domain.piece;


import domain.BoardStatus;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {
    @Test
    @DisplayName("목적지와 출발지 사이에 기물이 있으면 갈 수 없어야 한다")
    void isMovable_success() {
        //given
        Cha testCha = new Cha(new SlidingMoveStrategy(new PalaceMoveRule()), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);
        Position obstacle = Position.of(2, 3);

        HashMap<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(obstacle, new Sa(new SingleStepMoveStrategy(new PalaceMoveRule()), Team.CHO));

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatThrownBy(() -> testCha.check(testBoard, start, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PieceErrorMessage.PATH_BLOCKED.getMessage());
    }

    @Test
    @DisplayName("목적지와 출발지 사이에 기물이 없다면 갈 수 있어야 한다")
    void isMovable_fail() {
        //given
        Cha testCha = new Cha(new SlidingMoveStrategy(new PalaceMoveRule()), Team.CHO);
        Position start = Position.of(2, 2);
        Position destination = Position.of(2, 4);

        HashMap<Position, Piece> testPieces = new HashMap<>();

        BoardStatus testBoard = BoardStatus.from(testPieces);

        //when
        Assertions.assertThatNoException().isThrownBy(() -> testCha.check(testBoard, start, destination));
    }

}
