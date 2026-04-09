package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import participant.Score;
import participant.Turn;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class BoardTest {

    @Test
    void 기물_정보는_내부에서_방어적_복사로_초기화한다() {
        // given
        Piece piece = new Piece(Side.HAN, PieceType.CHA);
        Position departure = new Position(1, 1);
        Map<Position, Piece> pieces = new HashMap<>();
        Board board = new Board(pieces);
        // when
        pieces.put(departure, piece);
        // then
        assertThat(board.pieces()).hasSize(0);
    }

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

    @ParameterizedTest
    @EnumSource(PieceType.class)
    void 특정_위치의_기물_타입을_반환한다(PieceType expected) {
        // given
        Position position = new Position(0, 0);
        Piece piece = new Piece(Side.HAN, expected);
        Map<Position, Piece> pieces = Map.of(
            position, piece
        );
        Board board = new Board(pieces);
        // when
        PieceType pieceType = board.getPieceTypeAt(position).get();
        // then
        assertThat(pieceType).isEqualTo(expected);
    }

    @Test
    void 남은_기물의_점수를_반환한다() {
        // given
        Side side = Side.CHO;
        Position departure = new Position(5, 0);
        Piece choPiece = new Piece(side, PieceType.JOL_BYEONG);
        Map<Position, Piece> pieces = Map.of(
            departure, choPiece
        );
        Board board = new Board(pieces);
        // when
        Score score = board.calculateScoreOf(side);
        // then
        Score expected = choPiece.getScore();
        assertThat(score).isEqualTo(expected);
    }

    @Test
    void 출발지와_도착지가_동일한_경우_예외를_던진다() {
        // given
        Position choDeparture = new Position(9, 0);
        Position choDestination = new Position(8, 0);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Board board = new Board(Map.of(
            choDeparture, choPiece
        ));
        // when & then
        assertThatThrownBy(() -> board.move(choDestination, choDeparture))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 한의_턴일_때_초의_기물로_공격하는_경우_예외를_던진다() {
        // given
        Turn hanTurn = Turn.HAN_TURN;
        Position choDeparture = new Position(9, 0);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Board board = new Board(Map.of(
            choDeparture, choPiece
        ));
        // when & then
        assertThatThrownBy(() -> board.validatePositions(choDeparture, hanTurn))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
