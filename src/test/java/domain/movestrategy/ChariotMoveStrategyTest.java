package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveStrategyTest {

    private final ChariotMoveStrategy strategy = new ChariotMoveStrategy();

    @Test
    @DisplayName("장애물이 없으면 끝까지 이동한다")
    void move_withoutObstacle() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).contains(
                Position.of(5, 6), Position.of(5, 7), Position.of(5, 8), Position.of(5, 9),
                Position.of(5, 4), Position.of(5, 3), Position.of(5, 2), Position.of(5, 1),
                Position.of(6, 5), Position.of(7, 5), Position.of(8, 5), Position.of(9, 5), Position.of(10, 5),
                Position.of(4, 5), Position.of(3, 5), Position.of(2, 5), Position.of(1, 5)
        );
    }

    @Test
    @DisplayName("장애물을 만나면 그 위치까지만 이동한다")
    void stop_atObstacle() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(7, 5), Piece.choPieceOf(PieceType.SOLDIER));

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).contains(
                Position.of(6, 5),
                Position.of(7, 5) // 장애물 포함
        );

        assertThat(result).doesNotContain(Position.of(8, 5));
    }

    @Test
    @DisplayName("적 기물은 포함하고 멈춘다")
    void capture_enemy() {
        // given
        Position from = Position.of(5, 5);
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(Position.of(7, 5), Piece.hanPieceOf(PieceType.SOLDIER));

        // when
        List<Position> result = strategy.calculateMovablePositions(from, pieces);

        // then
        assertThat(result).contains(
                Position.of(6, 5),
                Position.of(7, 5)
        );
    }

//    // TODO: 일단 공통 처리하고 싶어서 얘는 구현 안 했는데, 나중에 추가해야함. isOpposite 같은 걸로
//    @Test
//    @DisplayName("아군 기물은 포함하지 않는다")
//    void cannot_move_to_ally() {
//        // given
//        Position from = Position.of(5, 5);
//        Map<Position, Piece> pieces = new HashMap<>();
//
//        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));
//        pieces.put(Position.of(7, 5), Piece.choPieceOf(PieceType.SOLDIER));
//
//        // when
//        List<Position> result = strategy.calculateMovablePositions(from, pieces);
//
//        // then
//        assertThat(result).contains(Position.of(6, 5));
//        assertThat(result).doesNotContain(Position.of(7, 5)); // 아군은 못감
//    }
}
