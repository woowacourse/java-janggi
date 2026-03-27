package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 목적지에_반대_진영_기물이_있으면_해당_기물을_제거한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);

        Board board = new Board(() -> Map.of(
                new Position(4, 1), new Piece(PieceRule.SOLDIER, Camp.HAN),
                destination, new Piece(PieceRule.HORSE, Camp.CHO),
                source, new Piece(PieceRule.CANNON, Camp.HAN)
        ));
        // when
        board.movePiece(source, destination);
        // then
        boolean destinationExists = board.hasSamePieceRuleAt(destination, PieceRule.CANNON);
        boolean sourceExists = board.hasPieceAt(source);

        assertThat(destinationExists).isTrue();
        assertThat(sourceExists).isFalse();
    }
}
