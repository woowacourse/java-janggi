package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    void 소속팀이_달라도_기물은_동등하다() {
        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;

        Piece cannonCho = new Piece(team, PieceType.CANNON);
        Piece cannonHan = new Piece(opponentTeam, PieceType.CANNON);

        Assertions.assertThat(cannonCho)
                .isEqualTo(cannonHan);
    }

}
