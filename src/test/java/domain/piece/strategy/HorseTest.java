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

class HorseTest {
    @Test
    @DisplayName("마 기물의 움직임의 여부를 판단할 수 있다.")
    void 이동성공_마_기물_움직임_여부_판단_1() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choHorse = Piece.of(Side.CHO, PieceType.HORSE);
        pieceMap.put(Position.of(1, 7), choHorse);

        Position startPosition = Position.of(1, 7);
        Position endPosition = Position.of(3, 6);

        // when, then
        assertThat(choHorse.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }


    @Test
    @DisplayName("마 기물의 움직임의 여부를 판단할 수 있다.")
    void 이동성공_마_기물_움직임_여부_판단_2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choHorse = Piece.of(Side.CHO, PieceType.HORSE);
        pieceMap.put(Position.of(3, 6), choHorse);

        Position startPosition = Position.of(3, 6);
        Position endPosition = Position.of(4, 4);

        // when, then
        assertThat(choHorse.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("마 기물은 도착 지점으로 가는 경로 내에 기물이 있다면 움직일 수 없다.")
    void 마_움직임_실패_테스트_기물_막힘() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choHorse = Piece.of(Side.CHO, PieceType.HORSE);
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        pieceMap.put(Position.of(1, 7), choHorse);
        pieceMap.put(Position.of(2, 7), hanPawn);

        Position startPosition = Position.of(1, 7);
        Position endPosition = Position.of(3, 6);

        // when, then
        assertThat(choHorse.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("마 기물은 허용되지 않은 경로로 움직일 수 없다.")
    void 마_움직임_실패_테스트_미허용_경로() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choHorse = Piece.of(Side.CHO, PieceType.HORSE);
        pieceMap.put(Position.of(1, 7), choHorse);

        Position startPosition = Position.of(1, 7);
        Position endPosition = Position.of(3, 7);

        // when, then
        assertThat(choHorse.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }
}
