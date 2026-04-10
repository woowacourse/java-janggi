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

class ChariotTest {

    @Test
    @DisplayName("차 기물이 움직임의 여부를 판단할 수 있다.")
    void 이동성공_차_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        pieceMap.put(Position.of(1, 9), choChariot);

        Position startPosition = Position.of(1, 9);
        Position endPosition = Position.of(10, 9);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("초 진영의 차 기물은 뒤로 움직일 수 있다.")
    void 이동성공_차_기물_움직임_여부_판단_2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        pieceMap.put(Position.of(4, 9), choChariot);

        Position startPosition = Position.of(4, 9);
        Position endPosition = Position.of(2, 9);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("차 기물은 도착 지점으로 가는 경로 내에 기물이 있다면 움직일 수 없다.")
    void 이동실패_차_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(1, 9), choChariot);
        pieceMap.put(Position.of(4, 9), choPawn);

        Position startPosition = Position.of(1, 9);
        Position endPosition = Position.of(10, 9);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("차 기물은 대각선으로 이동할 수 없다.")
    void 이동실패_차_기물_움직임_여부_판단_2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        pieceMap.put(Position.of(1, 9), choChariot);

        Position startPosition = Position.of(1, 9);
        Position endPosition = Position.of(2, 8);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("차 기물이 궁성영역에서 움직임의 여부를 판단할 수 있다.")
    void 이동성공_궁성_영역_차_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        pieceMap.put(Position.of(3, 4), choChariot);

        Position startPosition = Position.of(3, 4);
        Position endPosition = Position.of(1, 6);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("차 기물이 궁성영역에서 움직임의 여부를 판단할 수 있다.")
    void 이동성공_궁성_영역_차_기물_움직임_여부_판단2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        pieceMap.put(Position.of(3, 4), choChariot);

        Position startPosition = Position.of(3, 4);
        Position endPosition = Position.of(2, 5);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("차 기물이 궁성영역에서 움직임의 여부를 판단할 수 있다.")
    void 이동성공_궁성_영역_차_기물_움직임_여부_판단3() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        pieceMap.put(Position.of(2, 5), choChariot);

        Position startPosition = Position.of(2, 5);
        Position endPosition = Position.of(3, 6);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("차 기물은 궁성영역에서 경로가 없는 부분은 움직일 수 없다.")
    void 이동실패_궁성_영역_차_기물_움직임_여부_판단_4() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        pieceMap.put(Position.of(2, 4), choChariot);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(3, 5);

        // when, then
        assertThat(choChariot.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }
}
