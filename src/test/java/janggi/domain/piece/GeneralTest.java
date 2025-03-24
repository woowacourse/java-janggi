package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralTest {

    @DisplayName("궁은 팀의 기물이 있는 곳으로 이동할 수 없다.")
    @Test
    void test1() {
        // given
        Position startingPosition = Position.of(1, 5);
        Piece startingPiece = new General(Side.HAN);
        Position endPosition = Position.of(1, 4);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Soldier(Side.HAN)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("궁의 이동 경로에 상대 팀 기물이 있으면 이동할 수 있다.")
    @Test
    void test2() {
        // given
        Position startingPosition = Position.of(1, 5);
        Piece startingPiece = new General(Side.HAN);
        Position endPosition = Position.of(1, 4);

        Map<Position, Piece> startingPieces = Map.of(
                startingPosition, startingPiece,
                endPosition, new Soldier(Side.CHO)
        );

        // when
        boolean actual = startingPiece.canMove(startingPieces, startingPosition, endPosition);

        // then
        assertThat(actual).isTrue();
    }
}
