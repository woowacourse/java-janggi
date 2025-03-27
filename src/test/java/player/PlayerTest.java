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

class PlayerTest {
    @Test
    @DisplayName("플레이어는 국가와 기물들을 가진다")
    void playerTest() {
        Pieces pieces = new Pieces(List.of());

        assertDoesNotThrow(() -> new Player(pieces));
    }

    @Test
    @DisplayName("왕이 죽었는지 판단 테스트")
    void isJanggunDieTest() {
        //given
        Pieces pieces = new Pieces(List.of(new Jol(new Position(5, 5))));
        Player player = new Player(pieces);

        //when - then
        assertThat(player.isJanggunDie()).isTrue();
    }

    @Test
    @DisplayName("왕이 살았는지 판단 테스트")
    void isKingNotDieTest() {
        //given
        Pieces pieces = new Pieces(List.of(new Janggun(new Position(5, 5))));
        Player player = new Player(pieces);

        //when - then
        assertThat(player.isJanggunDie()).isFalse();
    }

    @Test
    @DisplayName("플레이어 기물 삭제 테스트")
    void removePiece() {
        //given
        Pieces pieces = new Pieces(List.of(new Janggun(new Position(5, 5))));
        Player player = new Player(pieces);
        Position destination = new Position(5, 5);

        //when
        player.removePiece(destination);

        //then
        assertThat(player.getPieces().getPieces().contains(new Janggun(new Position(5, 5)))).isFalse();
    }

    @Test
    @DisplayName("출발 지점에 있는 기물이 도착 지점에 갈 수 있는지 테스트")
    void validateAllyPieceAtStartTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));
        Player player = new Player(pieces);

        //when - then
        assertThatThrownBy(() -> player.validateAllyPieceAtStart(new Position(5,6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다.");


        assertDoesNotThrow(() -> player.canPieceMoveTo(new Position(5, 5), new Position(5, 6)));
    }

    @Test
    @DisplayName("도착 지점 아군 판별 테스트")
    void validateAllyPieceAtDestination() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));
        Player player = new Player(pieces);

        //when - then
        assertThatThrownBy(() -> player.validateAllyPieceAtDestination(new Position(5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 도착지에 아군 기물이 존재합니다.");

        assertDoesNotThrow(() -> player.validateAllyPieceAtDestination(new Position(5, 7)));
    }

    @Test
    @DisplayName("출발 지점에 있는 기물이 도착 지점에 갈 수 있는지 테스트")
    void canPieceMoveToTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));
        Player player = new Player(pieces);

        //when - then
        assertThatThrownBy(() -> player.canPieceMoveTo(new Position(5,5), new Position(5, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물이 움직일 수 없는 위치입니다.");

        assertDoesNotThrow(() -> player.canPieceMoveTo(new Position(5, 5), new Position(5, 6)));
    }

    @Test
    @DisplayName("기물 이동 경로 작성 테스트")
    void makeRouteTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Pieces pieces = new Pieces(List.of(jol));
        Position position = new Position(5, 6);
        Player player = new Player(pieces);

        //when
        Positions actual = player.makeRoute(new Position(5, 5), position);

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
                new Position(2, 5), new Position(7, 5), new Position(1,  3)
        ));
        Player player = new Player(pieces);

        //when
        int actual = player.countObstacle(positions);

        //then
        assertThat(actual).isEqualTo(0);
    }

    @Test
    @DisplayName("기물 이동 테스트")
    void movePieceTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 5));
        Po po = new Po(new Position(4, 3));
        Pieces pieces = new Pieces(List.of(jol, janggun, po));
        Player player = new Player(pieces);

        //when
        player.movePiece(new Position(6, 5), new Position(6, 6));

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
        Player player = new Player(pieces);

        //when-then
        assertThat(player.isPoAt(new Position(4, 3))).isTrue();
    }

    @Test
    @DisplayName("경로에 포 존재 확인 테스트")
    void isExistPoInRoute() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 5));
        Po po = new Po(new Position(4, 3));
        Pieces pieces = new Pieces(List.of(jol, janggun, po));
        Player player = new Player(pieces);
        Positions route1 = new Positions(
                List.of(new Position(4, 3))
        );

        Positions route2 = new Positions(
                List.of(new Position(0, 0))
        );

        //when-then
        assertThat(player.isExistPoInRoute(route1)).isTrue();
        assertThat(player.isExistPoInRoute(route2)).isFalse();
    }

}
