package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
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
                new Position(4, 1), new Piece(PieceType.SOLDIER, Camp.HAN),
                destination, new Piece(PieceType.HORSE, Camp.CHO),
                source, new Piece(PieceType.CANNON, Camp.HAN)
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
                destination, new Piece(PieceType.HORSE, Camp.CHO),
                source, new Piece(PieceType.CANNON, Camp.CHO)
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
    void 궁을_잡으면_게임이_끝난다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);

        Board board = new Board(() -> Map.of(
                new Position(4, 1), new Piece(PieceType.SOLDIER, Camp.HAN),
                destination, new Piece(PieceType.GENERAL, Camp.CHO),
                source, new Piece(PieceType.CANNON, Camp.HAN)
        ));

        // when
        boolean gameEnded = board.movePiece(source, destination, Camp.HAN);

        // then
        assertThat(gameEnded).isTrue();
    }
}
