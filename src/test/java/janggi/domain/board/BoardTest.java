package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 목적지에_반대_진영_기물이_있으면_해당_기물을_제거한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);

        Board board = new Board(() -> Map.of(
                new Position(4, 1), new Piece(Camp.HAN, PieceType.SOLDIER),
                destination, new Piece(Camp.CHO, PieceType.HORSE),
                source, new Piece(Camp.HAN, PieceType.CANNON)
        ));
        // when
        board.movePiece(source, destination, Camp.HAN);
        // then
        boolean destinationExists = board.hasSamePieceTypeAt(destination, PieceType.CANNON);
        boolean sourceExists = board.hasPieceAt(source);

        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(destinationExists).isTrue();
            assertSoftly.assertThat(sourceExists).isFalse();
        });
    }

    @Test
    void 상대_진영의_기물을_이동_시키면_예외가_발생한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);

        // when
        Board board = new Board(() -> Map.of(
                destination, new Piece(Camp.CHO, PieceType.HORSE),
                source, new Piece(Camp.CHO, PieceType.CANNON)
        ));
        // then
        Assertions.assertThatThrownBy(() -> board.movePiece(source, destination, Camp.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상대 진영의 기물은 이동할 수 없습니다.");
    }

    @Test
    void 출발지에_기물이_존재하지_않으면_예외가_발생한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);
        // when
        Board board = new Board(Map::of);
        // then
        Assertions.assertThatThrownBy(() -> board.movePiece(source, destination, Camp.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출발지에 기물이 존재하지 않습니다.");
    }

    @Test
    void 각_진영의_남아있는_기물로_점수를_계산한다() {
        // given
        Board board = new Board(() -> Map.of(
                new Position(1, 4), new Piece(Camp.CHO, PieceType.GENERAL),
                new Position(0, 0), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(0, 8), new Piece(Camp.CHO, PieceType.CHARIOT),

                new Position(4, 1), new Piece(Camp.HAN, PieceType.SOLDIER),
                new Position(7, 1), new Piece(Camp.HAN, PieceType.CANNON),
                new Position(7, 7), new Piece(Camp.HAN, PieceType.CANNON)
        ));

        // when
        double choScore = board.calculatePieceScore(Camp.CHO);
        double hanScore = board.calculatePieceScore(Camp.HAN);

        // then
        assertThat(choScore).isEqualTo(26);
        assertThat(hanScore).isEqualTo(16);
    }
}
