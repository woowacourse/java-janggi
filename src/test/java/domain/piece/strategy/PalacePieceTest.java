//package domain.piece.strategy;
//
//import domain.piece.Piece;
//import domain.piece.PieceType;
//import domain.piece.Side;
//import domain.position.Position;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//public class GeneralTest {
//
//    @Test
//    @DisplayName("장 기물이 움직임의 여부(UP)를 판단할 수 있다.")
//    void 장_기물_움직임_여부_판단() {
//        // given
//        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
//        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
//        pieceMap.put(Position.of(2,5), choGeneral);
//
//        Position startPosition = Position.of(2, 5);
//        Position endPosition = Position.of(1, 5);
//
//        // when, then
//        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
//    }
//}
