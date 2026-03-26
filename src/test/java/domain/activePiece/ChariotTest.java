package domain.activePiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.Position;
import domain.piece.Piece;
import domain.piece.Team;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    void 차는_직선_운동한다() {
        Piece cha = new Chariot(Team.HAN);
        assertThat(cha.canMove(new Position(1,4), new Position(1,8))).isTrue();

    }

    @Test
    void 차는_직선이_아닌_방향으로_가지_못한다() {
        Piece cha = new Chariot(Team.HAN);
        assertThat(cha.canMove(new Position(5,5), new Position(3,3))).isFalse();

    }

}
