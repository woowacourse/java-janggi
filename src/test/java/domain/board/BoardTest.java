package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.movepolicy.exception.InvalidPathRuleException;
import domain.movepolicy.exception.MovePolicyErrorMessage;
import domain.pieces.Cha;
import domain.pieces.EmptyPiece;
import domain.pieces.FullPiece;
import domain.pieces.Gung;
import domain.pieces.exception.InvalidMoveException;
import domain.pieces.Piece;
import domain.pieces.Po;
import domain.pieces.Side;
import domain.pieces.exception.PieceErrorMessage;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 기물의_위치를_이동시키면_기존_위치에는_기물이_존재하지_않는다() {
        // given
        Position before = new Position(1, 1);
        Position after = new Position(2, 1);
        FullPiece piece = new Cha(Side.HAN);
        Board beforeBoard = boardWith(before, piece, after, new EmptyPiece());
        // when
        Board afterBoard = beforeBoard.move(before, after);
        // then
        Map<Position, Piece> afterPieces = afterBoard.pieces();
        assertThat(afterPieces.get(before).isEmpty()).isTrue();
    }

    @Test
    void 기물의_위치를_이동시키면_도착지에_해당_기물이_존재한다() {
        // given
        Position before = new Position(1, 1);
        Position after = new Position(2, 1);
        FullPiece piece = new Cha(Side.HAN);
        Board beforeBoard = boardWith(before, piece, after, new EmptyPiece());
        // when
        Board afterBoard = beforeBoard.move(before, after);
        // then
        Map<Position, Piece> afterPieces = afterBoard.pieces();
        assertThat(afterPieces.get(after)).isEqualTo(piece);
    }

    @Test
    void 출발지의_기물이_도착지의_기물_위치로_이동하면_제거한다() {
        // given
        Position departure = new Position(1, 1);
        Position destination = new Position(2, 1);
        FullPiece departurePiece = new Cha(Side.HAN);
        FullPiece destinationPiece = new Cha(Side.CHO);
        Board beforeBoard = boardWith(departure, departurePiece, destination, destinationPiece);
        // when
        Board afterBoard = beforeBoard.move(departure, destination);
        // then
        Map<Position, Piece> afterPieces = afterBoard.pieces();
        Optional<Piece> deletedPiece = afterPieces.values().stream()
                .filter(piece -> piece.equals(destinationPiece))
                .findAny();
        assertThat(deletedPiece).isEmpty();
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

    @Nested
    @DisplayName("포의 이동을 보드에서 검증한다")
    class PoMovement {

        @Test
        void 포는_이동_경로에_포를_제외한_기물이_1개_있을_때_이동할_수_있다() {
            // given
            Position departure = new Position(3, 3);
            Position bridge = departure.moveUp();
            Position destination = departure.moveUp().moveUp();

            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(departure, new Po(Side.CHO));
            pieces.put(bridge, new Gung(Side.HAN));
            pieces.put(destination, new EmptyPiece());

            Board beforeBoard = new Board(pieces);

            // when
            Board afterBoard = beforeBoard.move(departure, destination);

            // then
            assertThat(afterBoard.pieces().get(departure).isEmpty()).isTrue();
            assertThat(afterBoard.pieces().get(destination).getType()).isEqualTo(new Po(Side.CHO).getType());
            assertThat(afterBoard.pieces().get(destination).isCho()).isTrue();
        }

        @Test
        void 포는_이동_경로에_기물이_없으면_예외를_던진다() {
            // given
            Position departure = new Position(3, 3);
            Position bridge = departure.moveUp();
            Position destination = departure.moveUp().moveUp();

            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(departure, new Po(Side.CHO));
            pieces.put(bridge, new EmptyPiece());
            pieces.put(destination, new EmptyPiece());

            Board board = new Board(pieces);

            // when & then
            assertThatThrownBy(() -> board.move(departure, destination))
                    .isInstanceOf(InvalidPathRuleException.class)
                    .hasMessage(MovePolicyErrorMessage.PATH_MUST_CONTAIN_PIECE.message());
        }

        @Test
        void 포는_이동_경로의_기물이_포이면_예외를_던진다() {
            // given
            Position departure = new Position(3, 3);
            Position bridge = departure.moveUp();
            Position destination = departure.moveUp().moveUp();

            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(departure, new Po(Side.CHO));
            pieces.put(bridge, new Po(Side.HAN));
            pieces.put(destination, new EmptyPiece());

            Board board = new Board(pieces);

            // when & then
            assertThatThrownBy(() -> board.move(departure, destination))
                    .isInstanceOf(InvalidPathRuleException.class)
                    .hasMessage(MovePolicyErrorMessage.PO_CANNOT_JUMP_OVER_PO.message());
        }

        @Test
        void 궁성_대각선_이동시_포를_제외한_기물이_1개_있으면_이동할_수_있다() {
            // given
            Position departure = new Position(0, 3);
            Position bridge = departure.moveRightUp();
            Position destination = departure.moveRightUp().moveRightUp();

            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(departure, new Po(Side.CHO));
            pieces.put(bridge, new Gung(Side.HAN));
            pieces.put(destination, new EmptyPiece());

            Board beforeBoard = new Board(pieces);

            // when
            Board afterBoard = beforeBoard.move(departure, destination);

            // then
            assertThat(afterBoard.pieces().get(departure).isEmpty()).isTrue();
            assertThat(afterBoard.pieces().get(destination).getType()).isEqualTo(new Po(Side.CHO).getType());
            assertThat(afterBoard.pieces().get(destination).isCho()).isTrue();
        }

        @Test
        void 궁성_대각선_이동시_이동_경로에_기물이_없으면_예외를_던진다() {
            // given
            Position departure = new Position(0, 3);
            Position bridge = departure.moveRightUp();
            Position destination = departure.moveRightUp().moveRightUp();

            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(departure, new Po(Side.CHO));
            pieces.put(bridge, new EmptyPiece());
            pieces.put(destination, new EmptyPiece());

            Board board = new Board(pieces);

            // when & then
            assertThatThrownBy(() -> board.move(departure, destination))
                    .isInstanceOf(InvalidPathRuleException.class)
                    .hasMessage(MovePolicyErrorMessage.PATH_MUST_CONTAIN_PIECE.message());
        }

        @Test
        void 궁성_대각선_이동시_이동_경로의_기물이_포이면_예외를_던진다() {
            // given
            Position departure = new Position(0, 3);
            Position bridge = departure.moveRightUp();
            Position destination = departure.moveRightUp().moveRightUp();

            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(departure, new Po(Side.CHO));
            pieces.put(bridge, new Po(Side.HAN));
            pieces.put(destination, new EmptyPiece());

            Board board = new Board(pieces);

            // when & then
            assertThatThrownBy(() -> board.move(departure, destination))
                    .isInstanceOf(InvalidPathRuleException.class)
                    .hasMessage(MovePolicyErrorMessage.PO_CANNOT_JUMP_OVER_PO.message());
        }

        @Test
        void 궁성_대각선_선분이_아닌_대각선으로는_이동할_수_없다() {
            // given
            Position departure = new Position(0, 4);
            Position destination = departure.moveRightUp();

            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(departure, new Po(Side.CHO));
            pieces.put(destination, new EmptyPiece());

            Board board = new Board(pieces);

            // when & then
            assertThatThrownBy(() -> board.move(departure, destination))
                    .isInstanceOf(InvalidMoveException.class)
                    .hasMessage(PieceErrorMessage.PO_INVALID_MOVE.message());
        }
    }

    private Board boardWith(Position firstPosition, Piece firstPiece,
                            Position secondPosition, Piece secondPiece) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(firstPosition, firstPiece);
        pieces.put(secondPosition, secondPiece);
        return new Board(pieces);
    }
}
