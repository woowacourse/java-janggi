package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}