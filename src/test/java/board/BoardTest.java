package board;

import domain.board.Board;
import domain.board.Placement;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BoardTest {

    @Test
    @DisplayName("한 쪽 진영의 장기판 상차림 초기화할 수 있다.")
    void placePieces_WhenSideAndPlacement_PlacedCorrectly() {
        // given
        Board board = new Board();

        // when
        board.placePieces(Side.HAN, Placement.OUTER_ELEPHANT);

        // then
        assertThat(board.findBy(Position.of(10,2))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
        assertThat(board.findBy(Position.of(10,3))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10,7))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10,8))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
    }

    @Test
    @DisplayName("장기판에서 해당 포지션의 기물을 찾을 수 있다.")
    void findBy_ReturnsPiece_WhenPositionExistsOnBoard() {
        // given
        Board board = new Board();
        board.placePieces(Side.HAN, Placement.OUTER_ELEPHANT);

        // when
        Piece piece = board.findBy(Position.of(10, 2));

        // then
        assertThat(piece.getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(piece.getSide()).isEqualTo(Side.HAN);
    }

    @Test
    @Deprecated
    @DisplayName("장기판에서 해당 포지션의 기물이 존재하지 않을 때 테스트")
    void 장기판_존재하지_않는_포지션의_기물_조회_() {

    }

    @Test
    @DisplayName("졸이 기물의 이동했을 때 상대 진영의 기물을 포획할 수 있다.")
    void 졸_기물_포획_성공() {
        // given
        Board board = new Board();
        board.placePieces(Side.CHO, Placement.INNER_ELEPHANT);
        board.placePieces(Side.HAN, Placement.INNER_ELEPHANT);
        board.move(Position.of(7,1), Position.of(6,1), Side.HAN);
        board.move(Position.of(6,1), Position.of(5,1), Side.HAN);

        // when
        Position startPosition = Position.of(4, 1);
        Position endPosition = Position.of(5, 1);
        board.move(startPosition, endPosition, Side.CHO);

        // then
        Piece piece = board.findBy(endPosition);
        assertThat(piece).isEqualTo(Piece.of(Side.CHO, PieceType.PAWN));
    }
}
