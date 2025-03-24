package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Janggun;
import piece.Jol;
import pieceProperty.Position;

class PlayerTest {
    @Test
    @DisplayName("플레이어는 국가와 기물들을 가진다")
    void playerTest() {
        Pieces pieces = new Pieces(List.of());

        assertDoesNotThrow(() -> new Player(pieces, CHO));
    }

    @Test
    @DisplayName("왕이 죽었는지 판단 테스트")
    void isKingDieTest() {
        //given
        Pieces pieces = new Pieces(List.of(new Jol(new Position(5, 5))));
        Player player = new Player(pieces, HAN);

        //when - then
        assertThat(player.isKingDie()).isTrue();
    }

    @Test
    @DisplayName("왕이 살았는지 판단 테스트")
    void isKingNotDieTest() {
        //given
        Pieces pieces = new Pieces(List.of(new Janggun(new Position(5, 5))));
        Player player = new Player(pieces, HAN);

        //when - then
        assertThat(player.isKingDie()).isFalse();
    }

    @Test
    @DisplayName("플레이어 기물 이동 테스트")
    void movePieceTest() {
        //given
        Pieces pieces = new Pieces(List.of(new Janggun(new Position(5, 5))));
        Player player = new Player(pieces, HAN);
        Position presentPosition = new Position(5, 5);
        Position destination = new Position(5, 6);

        //when
        player.movePiece(presentPosition, destination);

        //then
        assertThat(player.isKingDie()).isFalse();
    }

    @Test
    @DisplayName("플레이어 기물 삭제 테스트")
    void removePiece() {
        //given
        Pieces pieces = new Pieces(List.of(new Janggun(new Position(5, 5))));
        Player player = new Player(pieces, HAN);
        Position destination = new Position(5, 5);

        //when
        player.removePiece(destination);

        //then
        assertThat(player.getPieces().getPieces().contains(new Janggun(new Position(5, 5)))).isFalse();
    }

    @DisplayName("같은 국가 판단 테스트")
    @Test
    void isSameNationTest() {
        //given
        Player player = new Player(new Pieces(List.of()), HAN);

        //when-then
        assertThat(player.isSameNation(HAN)).isTrue();
        assertThat(player.isSameNation(CHO)).isFalse();
    }

    @Test
    @DisplayName("출발 지점에 있는 기물이 도착 지점에 갈 수 있는지 테스트")
    void canPieceMoveToTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));
        Player player = new Player(pieces, HAN);

        //when - then
        assertThatThrownBy(() -> player.validateAllyPieceAtStart(new Position(5,6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 위치에 아군 기물이 존재하지 않습니다.");


        assertDoesNotThrow(() -> pieces.canPieceMoveTo(new Position(5, 5), new Position(5, 6)));
    }

    @Test
    @DisplayName("도착 지점 아군 판별 테스트")
    void validateAllyPieceAtDestination() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));
        Player player = new Player(pieces, HAN);

        //when - then
        assertThatThrownBy(() -> player.validateAllyPieceAtDestination(new Position(5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 도착지에 아군 기물이 존재합니다.");

        assertDoesNotThrow(() -> pieces.validateAllyPieceAtDestination(new Position(5, 7)));
    }

}
