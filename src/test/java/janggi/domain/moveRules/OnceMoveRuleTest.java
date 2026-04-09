package janggi.domain.moveRules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class OnceMoveRuleTest {

    @Test
    @DisplayName("초나라 졸은 북,동,서 방향으로 이동할 수 있다.")
    void 초나라_졸은_북동서_이동가능() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position position = new Position(5, 5);
        Team choTeam = Team.CHO;
        Piece choZolPiece = new Piece(choTeam, PieceType.ZOL);
        state.put(position, choZolPiece);
        MoveRule choZolMoveRule = new OnceMoveRule();

        //when
        List<Position> availablePositions = choZolMoveRule.calculateAvailablePositions(position, choTeam, state);

        //then
        assertThat(availablePositions)
                .containsExactlyInAnyOrder(
                        new Position(5, 4),
                        new Position(4, 5),
                        new Position(6, 5)
                );
    }

    @ParameterizedTest
    @EnumSource(value = PieceType.class, names = {"KING", "SA"})
    @DisplayName("왕과 사는 모든 방향으로 이동할 수 있다.")
    void 왕과_사는_모든_방향으로_이동_가능() {
        //given
        Map<Position, Piece> state = new HashMap<>();
        Position position = new Position(5, 9);
        Team choTeam = Team.CHO;
        Piece king = new Piece(Team.CHO, PieceType.KING);
        Piece sa = new Piece(Team.CHO, PieceType.SA);
        state.put(position, king);
        MoveRule onceMoveRule = new OnceMoveRule();

        //when
        List<Position> availablePositions = onceMoveRule.calculateAvailablePositions(position, choTeam, state);

        //then
        assertThat(availablePositions)
                .containsExactlyInAnyOrder(
                        new Position(5, 8),
                        new Position(4, 9),
                        new Position(6, 9),
                        new Position(5, 10),
                        new Position(4, 8),
                        new Position(6, 8),
                        new Position(4, 10),
                        new Position(6, 10)
                );
    }
}
