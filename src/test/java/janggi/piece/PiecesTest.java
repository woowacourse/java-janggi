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
    @DisplayName("시작점과 도착점에 있는 장기말이 같은 타입이라면 true 반환한다")
    void should_return_true_when_start_and_end_position_piece_is_same_piece_type() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece redCannon = new Cannon(Color.RED);
        Piece blueCannon = new Cannon(Color.BLUE);
        Pieces pieces = new Pieces(Map.of(start, redCannon,
                end, blueCannon));

        // when
        boolean isSamePieceType = pieces.isSamePieceType(start, end);

        // then
        assertThat(isSamePieceType).isEqualTo(true);
    }

    @Test
    @DisplayName("시작점과 도착점에 있는 장기말이 다른 타입이라면 false 반환한다")
    void should_return_false_when_end_position_piece_is_not_same_piece_type() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece redCannon = new Cannon(Color.RED);
        Piece tank = new Tank(Color.BLUE);
        Pieces pieces = new Pieces(Map.of(start, redCannon,
                end, tank));

        // when
        boolean isSamePieceType = pieces.isSamePieceType(start, end);

        // then
        assertThat(isSamePieceType).isEqualTo(false);
    }

    @Test
    @DisplayName("장기말이 같은지 확인하는 과정에 시작점에 장기말이 없는 경우 false 반환한다")
    void should_return_false_when_start_position_piece_empty() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece redCannon = new Cannon(Color.RED);
        Pieces pieces = new Pieces(Map.of(end, redCannon));

        // when
        boolean isSamePieceType = pieces.isSamePieceType(start, end);

        // then
        assertThat(isSamePieceType).isEqualTo(false);
    }

    @Test
    @DisplayName("장기말이 같은지 확인하는 과정에 도착점에 장기말이 없는 경우 false 반환한다")
    void should_return_false_when_end_position_piece_empty() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(5, 6);
        Piece redCannon = new Cannon(Color.RED);
        Pieces pieces = new Pieces(Map.of(start, redCannon));

        // when
        boolean isSamePieceType = pieces.isSamePieceType(start, end);

        // then
        assertThat(isSamePieceType).isEqualTo(false);
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
    @DisplayName("경로상에 주어진 장기말과 같은 타입의 장기말이 있는 경우 true 반환한다")
    void should_return_true_when_exist_same_piece_type_on_path() {
        // given
        Position firstPosition = new Position(5, 5);
        Piece cannon = new Cannon(Color.RED);
        Pieces pieces = new Pieces(Map.of(firstPosition, cannon));
        List<Position> path = List.of(firstPosition);
        Piece targetCannon = new Cannon(Color.BLUE);

        // when
        boolean isSamePieceTypeOnPath = pieces.isSamePieceTypeOnPath(path, targetCannon);

        // then
        assertThat(isSamePieceTypeOnPath).isEqualTo(true);
    }

    @Test
    @DisplayName("장기말의 이동 경로에 같은 타입의 장기말이 없는 경우 false 반환한다")
    void should_return_false_when_cannon_not_exist_on_path() {
        // given
        Position firstPosition = new Position(5, 5);
        Position secondPosition = new Position(5, 6);
        Piece tank = new Tank(Color.RED);
        Piece horse = new Horse(Color.RED);
        Piece otherPiece = new Cannon(Color.RED);
        Pieces pieces = new Pieces(Map.of(firstPosition, tank, secondPosition, horse));
        List<Position> positions = List.of(firstPosition, secondPosition);

        // when
        boolean isSamePieceTypeOnPath = pieces.isSamePieceTypeOnPath(positions, otherPiece);

        // then
        assertThat(isSamePieceTypeOnPath).isEqualTo(false);
    }

    @Test
    @DisplayName("색상이 주어질 때 해당 색상의 장기말 점수를 반환한다")
    void should_return_piece_score_by_color() {
        // given
        Pieces pieces = Pieces.init();

        // when
        int redScore = pieces.calculatePieceScore(Color.RED);
        int blueScore = pieces.calculatePieceScore(Color.BLUE);

        // then
        assertAll(
                () -> assertThat(redScore).isEqualTo(72),
                () -> assertThat(blueScore).isEqualTo(72)
        );
    }
}
