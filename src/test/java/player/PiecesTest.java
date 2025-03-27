package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Janggun;
import piece.Jol;
import piece.Po;
import pieceProperty.Position;
import pieceProperty.Positions;

class PiecesTest {

    @Test
    @DisplayName("Pieces 기물 삭제 테스트")
    void removeTest() {
        Jol jol = new Jol(new Position(5, 5));
        Pieces pieces = new Pieces(List.of(jol));

        pieces.removePiece(new Position(5, 5));
        assertThat(pieces.getPieces().contains(jol)).isFalse();
    }

    @Test
    @DisplayName("왕이 죽었는지 확인하는 테스트")
    void hasJanggun() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Pieces pieces = new Pieces(List.of(jol));

        //when - then
        assertThat(pieces.hasJanggun()).isTrue();
    }

    @Test
    @DisplayName("왕이 죽었는지 확인하는 테스트 (왕 생존)")
    void hasJanggunTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));

        //when - then
        assertThat(pieces.hasJanggun()).isFalse();
    }

    @Test
    @DisplayName("시작 지점 아군 판별 테스트")
    void validateAllyPieceAtStart() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));

        //when - then
        assertThatThrownBy(() -> pieces.validateAllyPieceAtStart(new Position(5, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다.");
        ;
        assertDoesNotThrow(() -> pieces.validateAllyPieceAtStart(new Position(5, 5)));
    }

    @Test
    @DisplayName("도착 지점 아군 판별 테스트")
    void validateAllyPieceAtDestination() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));

        //when - then
        assertThatThrownBy(() -> pieces.validateAllyPieceAtDestination(new Position(5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 도착지에 아군 기물이 존재합니다.");

        assertDoesNotThrow(() -> pieces.validateAllyPieceAtDestination(new Position(5, 7)));
    }

    @Test
    @DisplayName("출발 지점에 있는 기물이 도착 지점에 갈 수 있는지 테스트")
    void canPieceMoveToTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));

        //when - then
        assertThatThrownBy(() -> pieces.canPieceMoveTo(new Position(5,5), new Position(5, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물이 움직일 수 없는 위치입니다.");

        assertDoesNotThrow(() -> pieces.canPieceMoveTo(new Position(5, 5), new Position(5, 6)));
    }

    @Test
    @DisplayName("기물 이동 경로 작성 테스트")
    void makeRouteTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Pieces pieces = new Pieces(List.of(jol));
        Position position = new Position(5, 6);

        //when
        Positions actual = pieces.makeRoute(new Position(5, 5), position);

        //then
        assertThat(actual.getPositions().contains(position)).isFalse();
    }

    @Test
    @DisplayName("경로 상에 같은 위치 기물 개수 확인")
    void countObstacleTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 5));
        Po po = new Po(new Position(4, 3));
        Pieces pieces = new Pieces(List.of(jol, janggun, po));
        Positions positions = new Positions(List.of(
                new Position(5, 5), new Position(6, 5), new Position(4, 3)
        ));

        //when
        int actual = pieces.countObstacle(positions);

        //then
        assertThat(actual).isEqualTo(3);
    }

    @Test
    @DisplayName("기물 이동 테스트")
    void movePieceTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 5));
        Po po = new Po(new Position(4, 3));
        Pieces pieces = new Pieces(List.of(jol, janggun, po));

        //when
        pieces.movePiece(new Position(6, 5), new Position(6, 6));

        //then
        assertThat(janggun.isSamePosition(new Position(6, 6))).isTrue();
    }

    @Test
    @DisplayName("포 존재 확인 테스트")
    void isPoAtTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 5));
        Po po = new Po(new Position(4, 3));
        Pieces pieces = new Pieces(List.of(jol, janggun, po));

        //when-then
        assertThat(pieces.isPoAt(new Position(4, 3))).isTrue();
    }

    @Test
    @DisplayName("경로에 포 존재 확인 테스트")
    void isExistPoInRoute() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 5));
        Po po = new Po(new Position(4, 3));
        Pieces pieces = new Pieces(List.of(jol, janggun, po));
        Positions route1 = new Positions(
                List.of(new Position(4, 3))
        );

        Positions route2 = new Positions(
                List.of(new Position(0, 0))
        );

        //when-then
        assertThat(pieces.isExistPoInRoute(route1)).isTrue();
        assertThat(pieces.isExistPoInRoute(route2)).isFalse();
    }

}
