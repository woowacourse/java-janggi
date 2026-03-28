package piece.strategy;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CounselorTest {

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 졸_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(1,4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 4);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }
}
