package janggi.domain.moveRules.outofpalacemoverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JumpMoveRuleTest {

    @Test
    @DisplayName("마는 경로에 장애물이 있다면 이동할 수 없다.")
    void 마는_경로에_장애물이_있을_경우_이동_불가() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position position = new Position(5, 5);
        Team choTeam = Team.CHO;
        Piece piece = new Piece(choTeam, PieceType.MA);
        Position obstaclePosition = new Position(5, 4);
        Piece obstaclePiece = new Piece(choTeam, PieceType.ZOL);
        state.put(position, piece);
        state.put(obstaclePosition, obstaclePiece);
        MoveRule maMoveRule = new JumpMoveRule();

        //when && then
        assertThat(maMoveRule.calculateAvailablePositions(position, choTeam, state))
                .doesNotContain(
                        new Position(4, 3),
                        new Position(6, 3)
                );
    }

    @Test
    @DisplayName("상는 경로에 장애물이 있다면 이동할 수 없다.")
    void 상은_경로에_장애물이_있을_경우_이동_불가() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position position = new Position(5, 5);
        Team choTeam = Team.CHO;
        Piece piece = new Piece(choTeam, PieceType.SANG);
        Position obstaclePosition = new Position(5, 4);
        Piece obstaclePiece = new Piece(choTeam, PieceType.ZOL);
        state.put(position, piece);
        state.put(obstaclePosition, obstaclePiece);
        MoveRule maMoveRule = new JumpMoveRule();

        //when && then
        assertThat(maMoveRule.calculateAvailablePositions(position, choTeam, state))
                .doesNotContain(
                        new Position(3, 2),
                        new Position(7, 2)
                );
    }
}
