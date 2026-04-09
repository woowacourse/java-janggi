package domain.piece.strategy;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CanonTest {

    @Test
    @DisplayName("본인 진영의 기물을 띄어넘어 빈칸으로 이동")
    void 이동성공_포_기물_움직임_여부_판단_1() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(3, 8), choCanon);
        pieceMap.put(Position.of(4, 8), choPawn);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(6, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("본인 진영의 기물을 띄어넘어 상대방 말로 이동")
    void 이동성공_포_기물_움직임_여부_판단_2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        Piece hanHorse = Piece.of(Side.HAN, PieceType.PAWN);
        pieceMap.put(Position.of(3, 8), choCanon);
        pieceMap.put(Position.of(4, 8), choPawn);
        pieceMap.put(Position.of(10, 8), hanHorse);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("상대방 진영 기물을 띄어넘어 빈칸으로 이동")
    void 이동성공_포_기물_움직임_여부_판단_3() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        pieceMap.put(Position.of(3, 8), choCanon);
        pieceMap.put(Position.of(5, 8), hanPawn);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("상대방 진영의 기물을 띄어넘어 상대방 말로 이동")
    void 이동성공_포_기물_움직임_여부_판단_4() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        Piece hanHorse = Piece.of(Side.HAN, PieceType.HORSE);
        pieceMap.put(Position.of(3, 8), choCanon);
        pieceMap.put(Position.of(4, 8), hanPawn);
        pieceMap.put(Position.of(10, 8), hanHorse);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("본인 진영의 기물을 띄어넘어 빈칸으로 이동")
    void 이동성공_포_기물_움직임_여부_판단_5() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(3, 4), choCanon);
        pieceMap.put(Position.of(2, 5), choPawn);

        Position startPosition = Position.of(3, 4);
        Position endPosition = Position.of(1, 6);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("직선의 경로가 아닌 경우")
    void 이동실패_포_기물_움직임_여부_판단_1() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        pieceMap.put(Position.of(3, 8), choCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(5, 7);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("가는 경로에 기물이 1개 초과일 경우")
    void 이동실패_포_기물_움직임_여부_판단_2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn1 = Piece.of(Side.CHO, PieceType.PAWN);
        Piece choPawn2 = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(3, 8), choCanon);
        pieceMap.put(Position.of(4, 8), choPawn1);
        pieceMap.put(Position.of(5, 8), choPawn2);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(7, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("가는 경로의 기물이 1개 있는데 해당 기물이 포일 경우")
    void 이동실패_포_기물_움직임_여부_판단_3() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece hanCanon = Piece.of(Side.HAN, PieceType.CANON);
        pieceMap.put(Position.of(3, 8), choCanon);
        pieceMap.put(Position.of(8, 8), hanCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("도착 지점이 상대방 포일 경우")
    void 이동실패_포_기물_움직임_여부_판단_4() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        Piece hanCanon = Piece.of(Side.HAN, PieceType.CANON);
        pieceMap.put(Position.of(3, 8), choCanon);
        pieceMap.put(Position.of(4, 8), choPawn);
        pieceMap.put(Position.of(8, 8), hanCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(8, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("가는 경로에 기물이 1개도 없는 경우")
    void 이동실패_포_기물_움직임_여부_판단_5() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        pieceMap.put(Position.of(3, 8), choCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(4, 8);

        // when, then
        assertThat(choCanon.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }
}
