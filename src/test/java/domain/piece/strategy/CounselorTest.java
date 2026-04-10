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

class CounselorTest {

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(1, 4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 4);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(1, 4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 5);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단3() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(1, 4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(1, 5);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단4() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(2, 4), choCounselor);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(1, 4);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단5() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(2, 4), choCounselor);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(3, 4);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단6() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(2, 4), choCounselor);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(2, 5);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단7() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(2, 5), choCounselor);

        Position startPosition = Position.of(2, 5);
        Position endPosition = Position.of(2, 4);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 움직임의 여부를 판단할 수 있다.")
    void 사_기물_움직임_여부_판단8() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(2, 5), choCounselor);

        Position startPosition = Position.of(2, 5);
        Position endPosition = Position.of(1, 4);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("사 기물이 궁내에서 경로가 없는 경로로 이동할 때")
    void 사_기물_움직임_실패_테스트_1() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(2, 4), choCounselor);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(1, 5);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("사 기물이 궁 밖으로 나가려고 할 때")
    void 사_기물_움직임_실패_테스트_2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        pieceMap.put(Position.of(1, 4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(1, 3);

        // when, then
        assertThat(choCounselor.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }
}
