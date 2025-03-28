package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.character.PieceType;
import janggiGame.testhelper.FakePiece;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {
    @DisplayName("기물을 기물이 어떤 나라의 것인지 알릴 수 있다.")
    @Test
    void pieceGetDynasty_Test() {
        // given
        FakePiece fakePiece = new FakePiece(Dynasty.HAN);

        // when // then
        assertThat(fakePiece.hasDynasty(Dynasty.HAN)).isTrue();
    }

    @DisplayName("기물은 자신의 타입을 알릴 수 있다.")
    @Test
    void pieceGetType_Test() {
        // given
        FakePiece fakePiece = new FakePiece(Dynasty.HAN);

        // when // then
        assertThat(fakePiece.getType()).isEqualTo(PieceType.PAWN);
    }

    @DisplayName("기물은 출발 점과 도착 점을 입력 받아 이동 하는 경로의 중간 지점을 반환할 수 있다.")
    @Test
    void pieceGetIntermediatePoints_Test() {
        // given
        FakePiece fakePiece = new FakePiece(Dynasty.HAN);

        // when // then
        assertThat(fakePiece.getIntermediatePoints(Position.of(0, 9), Position.of(0, 7))
                .contains(Position.of(0, 8))).isTrue();
    }

    @DisplayName("기물은 이동 조건을 판별할 수 있다.")
    @Test
    void pieceValidateMove_Test() {
        // given
        FakePiece fakePiece = new FakePiece(Dynasty.HAN);

        // when // then
        Map<Position, Piece> intermediatePointsWithPiece = Map.of(
                Position.of(0, 8), new Pawn(Dynasty.HAN)
        );

        Piece destinationPiece = new Pawn(Dynasty.HAN);

        assertThatCode(() -> fakePiece.validateMove(intermediatePointsWithPiece, destinationPiece))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

}
