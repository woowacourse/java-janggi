package janggi.domain.piece;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

@ReplaceUnderBar
class PiecesTest {

    @Test
    void 특정_위치의_기물을_찾는다_없다면_예외가_발생한다() {
        Rook rook = new Rook(Side.HAN, 0, 0);
        Pieces pieces = new Pieces(List.of(rook));

        assertAll(
            () -> assertThat(pieces.findPieceByPosition(new Position(0, 0))).isEqualTo(rook),
            () -> assertThatIllegalArgumentException()
                .isThrownBy(() -> pieces.findPieceByPosition(new Position(4,4)))
                .withMessage("해당 위치엔 기물이 존재하지 않습니다.")
        );
    }

    @Test
    void 왕이_2개가_아니라면_게임이_끝난_것이다() {
        Pieces oneKingPieces = new Pieces(List.of(new King(Side.CHO, 4, 4)));
        Pieces twoKingPieces = new Pieces(List.of(new King(Side.CHO, 4, 4), new King(Side.HAN, 4, 7)));

        assertAll(
            () -> assertThat(oneKingPieces.isEnd()).isTrue(),
            () -> assertThat(twoKingPieces.isEnd()).isFalse()
        );
    }

    @Test
    void 왕이_하나만_남은_경우_해당_왕의_진영이_승리한다() {
        Pieces oneKingPieces = new Pieces(List.of(new King(Side.CHO, 4, 4)));

        assertThat(oneKingPieces.getWinner()).isEqualTo(Side.CHO);
    }

    @Test
    void 기물들의_점수를_계산할_수_있다() {
        Pieces pieces = new Pieces(List.of(
            new Rook(Side.CHO, 0, 0), new Rook(Side.HAN, 0, 9),
            new Knight(Side.CHO, 1, 0), new Knight(Side.HAN, 1, 9),
            new Elephant(Side.CHO, 2, 0), new Elephant(Side.HAN, 2, 9),
            new Cannon(Side.CHO, 1, 2), new Cannon(Side.HAN, 1, 7),
            new Pawn(Side.CHO, 0, 3), new Pawn(Side.HAN, 0, 6),
            new King(Side.CHO, 4, 1), new King(Side.HAN, 4, 8),
            new Guard(Side.CHO, 3,0), new Guard(Side.HAN, 3,9)
        ));

        Map<Side, Double> scoreResult = Map.of(
            Side.CHO, 33.0,
            Side.HAN, 34.5
        );
        assertThat(pieces.calculateGameScore()).isEqualTo(scoreResult);
    }
}