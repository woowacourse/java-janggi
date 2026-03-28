package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import pieces.Cha;
import pieces.EmptyPiece;
import pieces.FullPiece;
import pieces.Piece;
import pieces.Side;
import position.Position;

class BoardTest {

    @Test
    void 기물의_위치를_이동시키면_기존_위치에는_기물이_존재하지_않는다() {
        // given
        FullPiece piece = new Cha(Side.HAN);
        Position departure = new Position(1, 1);
        Position destination = new Position(2, 1);

        Map<Position, Piece> beforePieces = Map.of(
            departure, piece,
            destination, EmptyPiece.getInstance()
        );
        Board beforeBoard = new Board(beforePieces);

        // when
        Board afterBoard = beforeBoard.move(departure, destination);
        // then
        Map<Position, Piece> afterPieces = afterBoard.pieces();
        assertThat(afterPieces.get(departure).isEmpty()).isTrue();
    }

    @Test
    void 기물의_위치를_이동시키면_도착지에_해당_기물이_존재한다() {
        // given
        FullPiece piece = new Cha(Side.HAN);
        Position departure = new Position(1, 1);
        Position destination = new Position(2, 1);

        Map<Position, Piece> beforePieces = Map.of(
            departure, piece,
            destination, EmptyPiece.getInstance()
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
        FullPiece departurePiece = new Cha(Side.HAN);
        FullPiece destinationPiece = new Cha(Side.CHO);

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
    void 중복이_없는_두_보드를_합친다() {
        // given
        Map<Position, Piece> choPieces = Map.of(new Position(1, 1), new Cha(Side.CHO));
        Map<Position, Piece> hanPieces = Map.of(new Position(1, 2), new Cha(Side.HAN));
        Board choBoard = new Board(choPieces);
        Board hanBoard = new Board(hanPieces);
        // when
        Board mergedBoard = choBoard.merge(hanBoard);
        // then
        Map<Position, Piece> expected = new HashMap<>();
        expected.putAll(choPieces);
        expected.putAll(hanPieces);
        assertThat(mergedBoard.pieces()).isEqualTo(expected);
    }

    @Test
    void 중복이_있는_두_보드를_합치는_경우_예외를_던진다() {
        // given
        Map<Position, Piece> choPieces = Map.of(new Position(1, 1), new Cha(Side.CHO));
        Map<Position, Piece> hanPieces = Map.of(new Position(1, 1), new Cha(Side.HAN));
        Board choBoard = new Board(choPieces);
        Board hanBoard = new Board(hanPieces);
        // when & then
        assertThatThrownBy(() -> choBoard.merge(hanBoard))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
