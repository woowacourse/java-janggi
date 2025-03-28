package janggiGame.arrangement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import janggiGame.Position;
import janggiGame.piece.Advisor;
import janggiGame.piece.Cannon;
import janggiGame.piece.Chariot;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.Elephant;
import janggiGame.piece.Horse;
import janggiGame.piece.King;
import janggiGame.piece.Pawn;
import janggiGame.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RightElephantStrategyTest {
    @DisplayName("상을 오른쪽으로 배치한다.")
    @Test
    void rightElephantStrategy_Test() {
        RightElephantStrategy strategy = new RightElephantStrategy();

        // when
        Map<Position, Piece> pieces = strategy.arrange(Dynasty.HAN);

        // then
        assertThat(pieces).hasSize(16);
        assertInstanceOf(Chariot.class, pieces.get(Position.of(0, 0)));
        assertInstanceOf(Chariot.class, pieces.get(Position.of(8, 0)));

        assertInstanceOf(Advisor.class, pieces.get(Position.of(3, 0)));
        assertInstanceOf(Advisor.class, pieces.get(Position.of(5, 0)));

        assertInstanceOf(King.class, pieces.get(Position.of(4, 1)));

        assertInstanceOf(Cannon.class, pieces.get(Position.of(1, 2)));
        assertInstanceOf(Cannon.class, pieces.get(Position.of(7, 2)));

        assertInstanceOf(Pawn.class, pieces.get(Position.of(0, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Position.of(2, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Position.of(4, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Position.of(6, 3)));
        assertInstanceOf(Pawn.class, pieces.get(Position.of(8, 3)));

        assertInstanceOf(Horse.class, pieces.get(Position.of(1, 0)));
        assertInstanceOf(Horse.class, pieces.get(Position.of(6, 0)));

        assertInstanceOf(Elephant.class, pieces.get(Position.of(2, 0)));
        assertInstanceOf(Elephant.class, pieces.get(Position.of(7, 0)));
    }
}