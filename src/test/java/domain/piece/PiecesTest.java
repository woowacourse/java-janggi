package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.direction.Directions;
import domain.direction.PieceDirections;
import domain.piece.category.Cannon;
import domain.piece.category.Elephant;
import domain.piece.category.General;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.Soldier;
import domain.player.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @Test
    void 특정_위치에_있는_기물을_반환한다() {
        // given
        Position position = Position.of(1, 5);
        Horse expected = new Horse(Position.of(1, 5), new Directions(Set.of(), true));

        Pieces pieces = new Pieces(List.of(
                new General(Position.of(2, 5), new Directions(Set.of(), true)),
                expected,
                new Soldier(Position.of(5, 7), new Directions(Set.of(), true))
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
                new General(Position.of(2, 5), new Directions(Set.of(), true)),
                new Horse(Position.of(1, 5), new Directions(Set.of(), true)),
                new Soldier(Position.of(5, 7), new Directions(Set.of(), true)),
                new Soldier(Position.of(8, 1), new Directions(Set.of(), true)),
                new Soldier(Position.of(2, 4), new Directions(Set.of(), true))
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
        Piece piece = new General(Position.of(2, 5), new Directions(Set.of(), true));
        Position position = Position.of(3, 5);

        List<Piece> pieceElements = new ArrayList<>();
        pieceElements.add(piece);
        pieceElements.add(new Horse(Position.of(1, 5), new Directions(Set.of(), true)));
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
                new General(Position.of(2, 5), new Directions(Set.of(), true)),
                new Horse(Position.of(3, 5), new Directions(Set.of(), true))
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
        Piece piece = new Guard(Position.of(2, 5), new Directions(Set.of(), true));
        Position position = Position.of(2, 5);

        List<Piece> pieceElements = new ArrayList<>();
        pieceElements.add(piece);
        pieceElements.add(new Horse(Position.of(1, 5), new Directions(Set.of(), true)));
        Pieces pieces = new Pieces(pieceElements);

        // when
        pieces.deleteByPosition(position, Team.HAN);

        // then
        assertThat(pieceElements).doesNotContain(piece);
    }

    @Test
    void 왕이_존재하는지_판단하다() {
        // given
        Pieces pieces = new Pieces(new ArrayList<>());
        Pieces piecesInKing = new Pieces(List.of(new General(Position.of(5, 2), PieceDirections.GENERAL.get())));

        // when & then
        assertThat(pieces.existGeneral()).isFalse();
        assertThat(piecesInKing.existGeneral()).isTrue();
    }

    @Test
    void 좌표의_기물이_포인지_판단한다() {
        // given
        Position position1 = Position.of(2, 3);
        Position position2 = Position.of(3, 3);

        Pieces pieces = new Pieces(List.of(new Cannon(Position.of(2, 3), PieceDirections.CANNON.get())));

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
                new Elephant(Position.of(2, 4), PieceDirections.ELEPHANT.get()),
                new Guard(Position.of(2, 4), PieceDirections.ELEPHANT.get()),
                new Soldier(Position.of(2, 4), PieceDirections.ELEPHANT.get())
        ));

        // when
        int result = pieces.calculateTotalScore();

        // then
        assertThat(result).isEqualTo(8);
    }
}
