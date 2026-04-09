package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ChariotTest {
    @DisplayName("차가 포에 의해 잡힐 수 있다.")
    @Test
    void canBeCaughtByCannon() {
        Piece piece = new Chariot(Camp.CHO);
        assertThat(piece.canBeCaughtByCannon()).isTrue();
    }

    @DisplayName("차는 넘을 수 있다.")
    @Test
    void canBeJumpedOver() {
        Piece piece = new Chariot(Camp.CHO);
        assertThat(piece.canBeJumpedOver()).isTrue();
    }

    @DisplayName("경로에 기물이 있으면 False를 반환한다")
    @Test
    void canPassRoute_PiecesInPathIsNotEmpty_ReturnFalse() {
        Piece piece = new Chariot(Camp.CHO);
        Map<Position, Piece> piecesInPath = new HashMap<>();
        piecesInPath.put(Position.of(3, 3),
                new Elephant(Camp.HAN));
        assertThat(piece.canPassRoute(piecesInPath)).isFalse();
    }

    @DisplayName("경로에 기물이 없으면 True를 반환한다")
    @Test
    void canPassRoute_PiecesInPathIsEmpty_ReturnTrue() {
        Piece piece = new Chariot(Camp.CHO);
        Map<Position, Piece> piecesInPath = new HashMap<>();
        assertThat(piece.canPassRoute(piecesInPath)).isTrue();
    }

    @DisplayName("도착 지점에 있는 기물이 같은 진영이면 False를 반환한다")
    @Test
    void canCatch_DestinationPieceIsSameCamp_ReturnFalse() {
        Piece piece = new Chariot(Camp.CHO);
        Piece destinationPiece = new Elephant(Camp.CHO);
        assertThat(piece.canCatch(destinationPiece)).isFalse();
    }

    @DisplayName("도착 지점에 있는 기물이 다른 진영이면 True를 반환한다")
    @Test
    void canCatch_DestinationPieceIsNotSameCamp_ReturnTrue() {
        Piece piece = new Chariot(Camp.CHO);
        Piece destinationPiece = new Elephant(Camp.HAN);
        assertThat(piece.canCatch(destinationPiece)).isTrue();
    }

    @Test
    void 필수적인_기물이_아니면_false를_출력한다() {
        Piece piece = new Chariot(Camp.CHO);
        boolean essential = piece.isEssential();

        assertThat(essential).isFalse();
    }
}
