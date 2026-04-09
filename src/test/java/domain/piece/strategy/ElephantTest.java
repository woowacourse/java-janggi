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

class ElephantTest {

    @Test
    @DisplayName("초 진영 상 기물의 움직임의 여부(UP, UP_LEFT, UP_LEFT)를 판단할 수 있다.")
    void 이동성공_초_진영_상_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choElephant = Piece.of(Side.CHO, PieceType.ELEPHANT);
        pieceMap.put(Position.of(1, 8), choElephant);

        Position startPosition = Position.of(1, 8);
        Position endPosition = Position.of(4, 6);

        // when, then
        assertThat(choElephant.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("한 진영 상 기물의 움직임의 여부(UP, UP_RIGHT, UP_RIGHT)를 판단할 수 있다.")
    void 이동성공_한_진영_상_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choElephant = Piece.of(Side.CHO, PieceType.ELEPHANT);
        pieceMap.put(Position.of(8, 8), choElephant);

        Position startPosition = Position.of(8, 8);
        Position endPosition = Position.of(5, 6);

        // when, then
        assertThat(choElephant.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("상 기물의 움직임의 여부(UP, UP_RIGHT, UP_RIGHT)를 판단할 수 있다.")
    void 이동성공_상_기물_움직임_여부_판단_() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choElephant = Piece.of(Side.CHO, PieceType.ELEPHANT);
        pieceMap.put(Position.of(3, 6), choElephant);

        Position startPosition = Position.of(3, 6);
        Position endPosition = Position.of(6, 8);

        // when, then
        assertThat(choElephant.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("상 기물은 도착 지점으로 가는 경로 내에 기물이 있다면 움직일 수 없다.")
    void 상_움직임_실패_테스트_기물_막힘() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choElephant = Piece.of(Side.CHO, PieceType.ELEPHANT);
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        pieceMap.put(Position.of(1, 8), choElephant);
        pieceMap.put(Position.of(2, 8), hanPawn);

        Position startPosition = Position.of(1, 8);
        Position endPosition = Position.of(4, 6);

        // when, then
        assertThat(choElephant.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("상 기물은 허용되지 않은 경로로 움직일 수 없다.")
    void 상_움직임_실패_테스트_미허용_경로() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choElephant = Piece.of(Side.CHO, PieceType.ELEPHANT);
        pieceMap.put(Position.of(1, 8), choElephant);

        Position startPosition = Position.of(1, 8);
        Position endPosition = Position.of(3, 8);

        // when, then
        assertThat(choElephant.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }
}
