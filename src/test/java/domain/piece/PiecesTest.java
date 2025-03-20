package domain.piece;

import domain.direction.Directions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PiecesTest {

    @Test
    void 특정_위치에_있는_기물을_반환한다() {
        // given
        Position position = Position.of(1, 5);
        Horse expected = new Horse(1, 5, new Directions(List.of()));

        Pieces pieces = new Pieces(List.of(
                new King(2, 5, new Directions(List.of())),
                expected,
                new Pawn(5, 7, new Directions(List.of()))
        ));

        // when
        Piece result = pieces.findByPosition(position);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void 좌표_목록에_일치하는_기물_개수를_계산한다() {
        // given
        Pieces pieces = new Pieces(List.of(
                new King(2, 5, new Directions(List.of())),
                new Horse(1, 5, new Directions(List.of())),
                new Pawn(5, 7, new Directions(List.of())),
                new Pawn(8, 1, new Directions(List.of())),
                new Pawn(2, 4, new Directions(List.of()))
        ));

        List<Position> positions = List.of(
                Position.of(1, 5),
                Position.of(2, 5),
                Position.of(5, 7)
        );

        // when
        int count = pieces.countPiecesInPositions(positions);

        // then
        assertThat(count).isEqualTo(3);
    }

    @Test
    void 기물의_좌표를_변경한다() {
        // given
        Piece piece = new King(2, 5, new Directions(List.of()));
        Position position = Position.of(3, 5);

        List<Piece> pieceElements = new ArrayList<>();
        pieceElements.add(piece);
        pieceElements.add(new Horse(1, 5, new Directions(List.of())));
        Pieces pieces = new Pieces(pieceElements);

        // when
        pieces.updatePosition(piece, position);

        // then
        assertThat(pieces.findByPosition(position).getPosition())
                .isEqualTo(position);
    }

    @Test
    void 좌표에_기물이_존재하는지_판단한다() {
        // given
        Position position = Position.of(3, 5);

        Pieces pieces = new Pieces(List.of(
                new King(2, 5, new Directions(List.of())),
                new Horse(3, 5, new Directions(List.of()))
        ));

        // when
        boolean result = pieces.existByPosition(position);

        // then
        assertThat(result)
                .isTrue();
    }

    @Test
    void 좌표의_기물을_삭제한다() {
        // given
        Piece piece = new King(2, 5, new Directions(List.of()));
        Position position = Position.of(2, 5);

        List<Piece> pieceElements = new ArrayList<>();
        pieceElements.add(piece);
        pieceElements.add(new Horse(1, 5, new Directions(List.of())));
        Pieces pieces = new Pieces(pieceElements);

        // when
        pieces.deleteByPosition(position);

        // then
        assertThat(pieceElements).doesNotContain(piece);
    }

    @Test
    void 왕이_존재하는지_판단하다() {
        // given
        Pieces pieces = new Pieces(new ArrayList<>());
        Pieces piecesInKing = new Pieces(List.of(new King(5, 2, PieceDirection.KING.get())));

        // when & then
        assertThat(pieces.existKing()).isFalse();
        assertThat(piecesInKing.existKing()).isTrue();
    }

    @Test
    void 좌표의_기물이_포인지_판단한다() {
        // given
        Position position1 = Position.of(2, 3);
        Position position2 = Position.of(3, 3);

        Pieces pieces = new Pieces(List.of(new Cannon(2, 3, PieceDirection.CANNON.get())));

        // when & then
        pieces.isCannonByPosition(position1);

        // then
        assertAll(
                () -> assertThat(pieces.isCannonByPosition(position1)).isTrue(),
                () -> assertThat(pieces.isCannonByPosition(position2)).isFalse()
        );
    }
}
