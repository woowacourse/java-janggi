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

public class GeneralTest {

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.UP")
    void 궁_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(1, 4), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 4);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.UP_RIGHT")
    void 궁_기물_움직임_여부_판단2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(1, 4), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 5);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.RIGHT")
    void 궁_기물_움직임_여부_판단3() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(1, 4), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(1, 5);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.DOWN")
    void 궁_기물_움직임_여부_판단4() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(2, 4), choGeneral);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(1, 4);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.UP")
    void 궁_기물_움직임_여부_판단5() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(2, 4), choGeneral);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(3, 4);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.RIGHT")
    void 궁_기물_움직임_여부_판단6() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(2, 4), choGeneral);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(2, 5);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.LEFT")
    void 궁_기물_움직임_여부_판단7() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(2, 5), choGeneral);

        Position startPosition = Position.of(2, 5);
        Position endPosition = Position.of(2, 4);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 움직임의 여부를 판단할 수 있다.DOWN_LEFT")
    void 궁_기물_움직임_여부_판단8() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(2, 5), choGeneral);

        Position startPosition = Position.of(2, 5);
        Position endPosition = Position.of(1, 4);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("궁 기물이 궁내에서 경로가 없는 경로로 이동할 때 이동할 수 없다.")
    void 궁_기물_움직임_실패_테스트_1() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(2, 4), choGeneral);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(1, 5);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("궁 기물이 궁 밖으로 나가려고 할 때 이동할 수 없다.")
    void 궁_기물_움직임_실패_테스트_2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        pieceMap.put(Position.of(1, 4), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(1, 3);

        // when, then
        assertThat(choGeneral.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }
}
