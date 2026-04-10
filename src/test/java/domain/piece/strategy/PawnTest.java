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

class PawnTest {
    @Test
    @DisplayName("졸 기물이 움직임의 여부를 판단할 수 있다.")
    void 졸_기물_움직임_여부_판단1() {
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

    @Test
    @DisplayName("궁성 영역에서 졸 기물이 움직임의 여부를 판단할 수 있다.")
    void 졸_기물_움직임_여부_판단2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(1, 4), choPawn);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 5);

        // when, then
        assertThat(choPawn.canMove(pieceMap, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("초 진영의 중 졸 기물은 뒤로 움직일 수 없다.")
    void 초_진영_졸_뒤로_움직임_실패1() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece hanPawn = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(4, 1), hanPawn);

        Position startPosition = Position.of(4, 1);
        Position endPosition = Position.of(3, 1);

        // when, then
        assertThat(hanPawn.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("초 진영의 중 졸 기물은 궁성 영역이 아닐때, 대각석으로 이동할 수 없다.")
    void 초_진영_졸_움직임_실패2() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece hanPawn = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(1, 4), hanPawn);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 3);

        // when, then
        assertThat(hanPawn.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }

    @Test
    @DisplayName("졸 기물은 궁성 내에서 경로가 없는 경로로 이동할 수 없다.")
    void 초_진영_졸_움직임_실패3() {
        // given
        Map<Position, Piece> pieceMap = new LinkedHashMap<>();
        Piece hanPawn = Piece.of(Side.CHO, PieceType.PAWN);
        pieceMap.put(Position.of(2, 4), hanPawn);

        Position startPosition = Position.of(2, 4);
        Position endPosition = Position.of(3, 5);

        // when, then
        assertThat(hanPawn.canMove(pieceMap, startPosition, endPosition)).isFalse();
    }
}
