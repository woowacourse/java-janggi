package domain.piece;

import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PieceTest {

    @Test
    @DisplayName("장기에서 진영과 기물 종류에 맞는 기물을 생성한다.")
    void 장기_진영_기물_종류에_맞는_기물_생성_테스트() {
        // given, when
        Piece piece = Piece.of(Side.CHO, PieceType.CANON);

        // then
        assertThat(piece.getSide()).isEqualTo(Side.CHO);
        assertThat(piece.getPieceType()).isEqualTo(PieceType.CANON);
    }

    @Test
    @DisplayName("졸 기물이 움직임의 여부를 판단할 수 있다.")
    void 졸_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        pieceMap.put(Position.of(4, 1), choPawn);
        pieceMap.put(Position.of(5, 1), hanPawn);

        Position startPosition = Position.of(4, 1);
        Position endPosition = Position.of(5, 1);

        // when, then
        assertThat(choPawn.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }
}
