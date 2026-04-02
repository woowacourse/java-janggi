package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class BoardTest {

    @Test
    void 기물의_위치를_이동시키면_기존_위치에는_기물이_존재하지_않는다() {
        // given
        Piece piece = new Piece(Side.HAN, PieceType.CHA);
        Position departure = new Position(1, 1);
        Position destination = new Position(2, 1);

        Map<Position, Piece> beforePieces = Map.of(
            departure, piece
        );
        Board beforeBoard = new Board(beforePieces);

        // when
        Board afterBoard = beforeBoard.move(departure, destination);
        // then
        Map<Position, Piece> afterPieces = afterBoard.pieces();
        assertThat(afterPieces.get(departure)).isNull();
    }

    @Test
    void 기물의_위치를_이동시키면_도착지에_해당_기물이_존재한다() {
        // given
        Piece piece = new Piece(Side.HAN, PieceType.CHA);
        Position departure = new Position(1, 1);
        Position destination = new Position(2, 1);

        Map<Position, Piece> beforePieces = Map.of(
            departure, piece
        );
        Board beforeBoard = new Board(beforePieces);
        // when
        Board afterBoard = beforeBoard.move(departure, destination);
        // then
        Map<Position, Piece> afterPieces = afterBoard.pieces();
        assertThat(afterPieces.get(destination)).isEqualTo(piece);
    }

    @Test
    void 출발지의_기물이_도착지의_기물_위치로_이동하면_제거한다() {
        // given
        Position departure = new Position(1, 1);
        Position destination = new Position(2, 1);
        Piece departurePiece = new Piece(Side.HAN, PieceType.CHA);
        Piece destinationPiece = new Piece(Side.CHO, PieceType.CHA);

        Map<Position, Piece> beforePieces = Map.of(
            departure, departurePiece,
            destination, destinationPiece
        );
        Board beforeBoard = new Board(beforePieces);
        // when
        Board afterBoard = beforeBoard.move(departure, destination);
        // then
        Map<Position, Piece> afterPieces = afterBoard.pieces();
        Optional<Piece> deletedPiece = afterPieces.values().stream()
            .filter(piece -> piece.equals(destinationPiece))
            .findAny();
        assertThat(deletedPiece.isEmpty()).isTrue();
    }

    @Test
    void 궁() {
        // given

        // when

        // then

    }
}
