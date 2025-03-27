package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecesTest {

    @Test
    @DisplayName("각기 다른 위치의 32개의 장기 말이 생성된다.")
    void should_return_32_pieces_when_create_initial_pieces() {
        // when
        Pieces pieces = Pieces.init();

        // then
        assertThat(pieces.getPieces()).hasSize(32);
    }

    @Test
    @DisplayName("Position이 주어질 때 해당 위치의 장기말을 반환한다")
    void should_return_piece_by_position() {
        // given
        Piece tank = new Tank(Color.RED);
        Position position = new Position(5, 5);
        Pieces pieces = new Pieces(Map.of(position, tank));

        // when
        Piece findPiece = pieces.getPieceByPosition(position);

        // then
        assertThat(findPiece).isEqualTo(tank);
    }

    @Test
    @DisplayName("Position이 주어질 때 해당 위치에 장기말이 없으면 예외가 발생한다")
    void should_throw_exception_when_piece_not_exist() {
        // given
        Position position = new Position(5, 5);
        Pieces pieces = new Pieces(Map.of());

        // when & then
        assertThatThrownBy(() -> pieces.getPieceByPosition(position))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("Position 목록이 주어질 때, 해당 위치에 장기말이 있으면 true 반환한다")
    void should_return_true_when_exist_piece_on_position() {
        // given
        Position position = new Position(5, 5);
        Piece tank = new Tank(Color.RED);
        Pieces pieces = new Pieces(Map.of(position, tank));

        List<Position> positions = List.of(position);

        // when
        boolean containsPiece = pieces.containsPiece(positions);

        // then
        assertThat(containsPiece).isEqualTo(true);
    }

    @Test
    @DisplayName("Position 목록이 주어질 때, 해당 위치에 장기말이 없으면 false 반환한다")
    void should_return_false_when_not_exist_piece_on_position() {
        // given
        Position position = new Position(5, 5);
        Pieces pieces = new Pieces(Map.of());
        List<Position> positions = List.of(position);

        // when
        boolean containsPiece = pieces.containsPiece(positions);

        // then
        assertThat(containsPiece).isEqualTo(false);
    }

    @Test
    @DisplayName("시작점과 끝점이 주어질 때 시작점에 있는 말이 끝점으로 이동한다")
    void should_move_piece_by_start_and_end_position() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Pieces pieces = new Pieces(Map.of(start, tank));

        // when
        pieces.moveForward(start, end);

        // then
        assertAll(
                () -> assertThat(pieces.containsPiece(start)).isFalse(),
                () -> assertThat(pieces.containsPiece(end)).isTrue()
        );
    }

    @Test
    @DisplayName("시작점과 끝점이 주어질 때 끝점에 말이 있다면 제거하고 시작점에 있는 말이 끝점으로 이동한다")
    void should_move_piece_by_start_and_end_position_when_exist_piece_on_end_position() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece cannon = new Cannon(Color.BLUE);
        Pieces pieces = new Pieces(Map.of(start, tank,
                end, cannon));

        // when
        pieces.moveForward(start, end);

        // then
        assertAll(
                () -> assertThat(pieces.containsPiece(start)).isFalse(),
                () -> assertThat(pieces.containsPiece(end)).isTrue()
        );
    }

    @Test
    @DisplayName("시작점과 끝점에 있는 두 장기말이 같은 색이라면 true 반환한다")
    void should_return_true_when_same_color_piece_on_start_and_end_position() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece cannon = new Cannon(Color.RED);
        Pieces pieces = new Pieces(Map.of(start, tank,
                end, cannon));

        // when
        boolean isSameColor = pieces.isSameColorPiece(start, end);

        // then
        assertThat(isSameColor).isEqualTo(true);
    }

    @Test
    @DisplayName("시작점과 끝점에 있는 두 장기말이 다른 색이라면 false 반환한다")
    void should_return_false_when_difference_color_piece_on_start_and_end_position() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece cannon = new Cannon(Color.BLUE);
        Pieces pieces = new Pieces(Map.of(start, tank,
                end, cannon));

        // when
        boolean isSameColor = pieces.isSameColorPiece(start, end);

        // then
        assertThat(isSameColor).isEqualTo(false);
    }

    @Test
    @DisplayName("시작점과 끝점에 있는 두 장기말이 둘 다 포라면 true 반환한다")
    void should_return_true_when_start_and_end_position_piece_is_cannon() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece redCannon = new Cannon(Color.RED);
        Piece blueCannon = new Cannon(Color.BLUE);
        Pieces pieces = new Pieces(Map.of(start, redCannon,
                end, blueCannon));

        // when
        boolean isEachCannon = pieces.isEachCannonPiece(start, end);

        // then
        assertThat(isEachCannon).isEqualTo(true);
    }

    @Test
    @DisplayName("끝점에 있는 장기말이 포가 아니라면 false 반환한다")
    void should_return_false_when_end_position_piece_is_not_cannon() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece redCannon = new Cannon(Color.RED);
        Piece tank = new Tank(Color.BLUE);
        Pieces pieces = new Pieces(Map.of(start, redCannon,
                end, tank));

        // when
        boolean isEachCannon = pieces.isEachCannonPiece(start, end);

        // then
        assertThat(isEachCannon).isEqualTo(false);
    }

    @Test
    @DisplayName("시작점에 있는 장기말이 포가 아니라면 false 반환한다")
    void should_return_false_when_start_position_piece_is_not_cannon() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece blueCannon = new Cannon(Color.BLUE);
        Pieces pieces = new Pieces(Map.of(start, tank,
                end, blueCannon));

        // when
        boolean isEachCannon = pieces.isEachCannonPiece(start, end);

        // then
        assertThat(isEachCannon).isEqualTo(false);
    }

    @Test
    @DisplayName("Position 목록이 주어질 때 경로상에 있는 장기말의 수를 반환한다")
    void should_return_piece_count_by_positions() {
        // given
        Position firstPosition = new Position(5, 5);
        Position secondPosition = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece cannon = new Cannon(Color.RED);
        Pieces pieces = new Pieces(Map.of(firstPosition, tank,
                secondPosition, cannon));
        List<Position> positions = List.of(firstPosition, secondPosition);

        // when
        long countPieceOnPath = pieces.countPieceOnPath(positions);

        // then
        assertThat(countPieceOnPath).isEqualTo(2);
    }

    @Test
    @DisplayName("Position 목록이 주어질 때 경로상에 캐논이 있으면 true 반환한다")
    void should_return_true_when_cannon_exist_on_path() {
        // given
        Position firstPosition = new Position(5, 5);
        Position secondPosition = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece cannon = new Cannon(Color.RED);
        Pieces pieces = new Pieces(Map.of(firstPosition, tank,
                secondPosition, cannon));
        List<Position> positions = List.of(firstPosition, secondPosition);

        // when
        boolean isCannonOnPath = pieces.isCannonPieceOnPath(positions);

        // then
        assertThat(isCannonOnPath).isEqualTo(true);
    }

    @Test
    @DisplayName("Position 목록이 주어질 때 경로상에 캐논이 없으면 false 반환한다")
    void should_return_false_when_cannon_not_exist_on_path() {
        // given
        Position firstPosition = new Position(5, 5);
        Position secondPosition = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece horse = new Horse(Color.RED);
        Pieces pieces = new Pieces(Map.of(firstPosition, tank, secondPosition, horse));
        List<Position> positions = List.of(firstPosition, secondPosition);

        // when
        boolean isCannonOnPath = pieces.isCannonPieceOnPath(positions);

        // then
        assertThat(isCannonOnPath).isEqualTo(false);
    }
}
