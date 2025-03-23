package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Janggun;
import piece.Jol;
import pieceProperty.Position;

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
    void isKingDie() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Pieces pieces = new Pieces(List.of(jol));

        //when - then
        assertThat(pieces.isKingDie()).isTrue();
    }

    @Test
    @DisplayName("왕이 죽었는지 확인하는 테스트 (왕 생존)")
    void isKingDieTest() {
        //given
        Jol jol = new Jol(new Position(5, 5));
        Janggun janggun = new Janggun(new Position(6, 4));
        Pieces pieces = new Pieces(List.of(jol, janggun));

        //when - then
        assertThat(pieces.isKingDie()).isFalse();
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
        assertThatThrownBy(() -> pieces.validateAllyPieceAtDestination(new Position(5, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 도착지에 아군 기물이 존재합니다.");
        ;
        assertDoesNotThrow(() -> pieces.validateAllyPieceAtDestination(new Position(5, 5)));
    }

}
