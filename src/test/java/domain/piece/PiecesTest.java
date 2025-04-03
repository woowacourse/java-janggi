package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @Test
    void 특정_위치에_있는_기물을_반환한다() {
        // given
        Position position = Position.of(1, 5);
        Piece expected = new Piece(Position.of(1, 5), PieceType.HORSE, MovementRule.HORSE);

        Pieces pieces = new Pieces(List.of(
                new Piece(Position.of(2, 5), PieceType.GENERAL, MovementRule.HAN_GENERAL),
                expected,
                new Piece(Position.of(5, 7), PieceType.ELEPHANT, MovementRule.ELEPHANT)));

        // when
        Piece result = pieces.findByPosition(position);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void 좌표_목록에_일치하는_기물_개수를_계산한다() {
        // given
        Pieces pieces = new Pieces(List.of(
                new Piece(Position.of(2, 5), PieceType.GENERAL, MovementRule.HAN_GENERAL),
                new Piece(Position.of(1, 5), PieceType.HORSE, MovementRule.HORSE),
                new Piece(Position.of(5, 7), PieceType.SOLDIER, MovementRule.HAN_SOLDIER),
                new Piece(Position.of(8, 1), PieceType.SOLDIER, MovementRule.HAN_SOLDIER),
                new Piece(Position.of(2, 4), PieceType.SOLDIER, MovementRule.HAN_SOLDIER)
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
        Piece piece = new Piece(Position.of(2, 5), PieceType.GENERAL, MovementRule.HAN_GENERAL);
        Position position = Position.of(3, 5);

        List<Piece> pieceElements = new ArrayList<>();
        pieceElements.add(piece);
        pieceElements.add(new Piece(Position.of(1, 5), PieceType.HORSE, MovementRule.HORSE));
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
                new Piece(Position.of(2, 5), PieceType.GENERAL, MovementRule.HAN_GENERAL),
                new Piece(Position.of(3, 5), PieceType.HORSE, MovementRule.HORSE)
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
        Piece piece = new Piece(Position.of(2, 5), PieceType.GUARD, MovementRule.HAN_GUARD);
        Position position = Position.of(2, 5);

        List<Piece> pieceElements = new ArrayList<>();
        pieceElements.add(piece);
        pieceElements.add(new Piece(Position.of(1, 5), PieceType.HORSE, MovementRule.HORSE));
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
        Pieces piecesInKing = new Pieces(
                List.of(new Piece(Position.of(5, 2), PieceType.GENERAL, MovementRule.HAN_GENERAL)));

        // when & then
        assertThat(pieces.existGeneral()).isFalse();
        assertThat(piecesInKing.existGeneral()).isTrue();
    }

    @Test
    void 좌표의_기물이_포인지_판단한다() {
        // given
        Position position1 = Position.of(2, 3);
        Position position2 = Position.of(3, 3);

        Pieces pieces = new Pieces(List.of(new Piece(Position.of(2, 3), PieceType.CANNON, MovementRule.CANNON)));

        // when & then
        pieces.isCannonByPosition(position1);

        // then
        assertAll(
                () -> assertThat(pieces.isCannonByPosition(position1)).isTrue(),
                () -> assertThat(pieces.isCannonByPosition(position2)).isFalse()
        );
    }

    @Test
    void 기물들의_점수_총_합산을_계산한다() {
        // given
        Pieces pieces = new Pieces(List.of(
                new Piece(Position.of(2, 4), PieceType.ELEPHANT, MovementRule.ELEPHANT),
                new Piece(Position.of(2, 4), PieceType.GUARD, MovementRule.HAN_GUARD),
                new Piece(Position.of(2, 4), PieceType.SOLDIER, MovementRule.HAN_SOLDIER)
        ));

        // when
        int result = pieces.calculateTotalScore();

        // then
        assertThat(result).isEqualTo(8);
    }
}
