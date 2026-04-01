package domain.movestrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.board.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicMoveStrategyTest {

    BasicMoveStrategy mockMoveStrategy = new BasicMoveStrategy() {
        @Override
        public List<Position> calculateMovablePositions(Position from, Map<Position, Piece> pieces) {
            return null; // do nothing
        }
    };

    @Test
    @DisplayName("기물이 보드 안에 위치하면 true")
    void isInsideBoardTest() {
        // given
        Position outside = Position.of(0, 1);
        Position inside = Position.of(10, 9);

        // when & then
        assertThat(mockMoveStrategy.isInsideBoard(outside)).isFalse();
        assertThat(mockMoveStrategy.isInsideBoard(inside)).isTrue();
    }

    @Test
    @DisplayName("해당 칸이 비어있거나 적 기물이 존재하면 true")
    void isEmptyOrOppositeTest() {
        // given
        Position from = Position.of(1, 1);
        Position to1 = Position.of(2, 1); // 비어 있음
        Position to2 = Position.of(3, 1); // 적 기물
        Position to3 = Position.of(4, 1); // 아군 기물

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(from, Piece.choPieceOf(PieceType.CHARIOT));
        pieces.put(to2, Piece.hanPieceOf(PieceType.CHARIOT));
        pieces.put(to3, Piece.choPieceOf(PieceType.CHARIOT));

        // then & then
        assertSoftly(softly -> {
            softly.assertThat(mockMoveStrategy.isEmptyOrOpposite(from, to1, pieces)).isTrue();
            softly.assertThat(mockMoveStrategy.isEmptyOrOpposite(from, to2, pieces)).isTrue();
            softly.assertThat(mockMoveStrategy.isEmptyOrOpposite(from, to3, pieces)).isFalse();
        });
    }
}