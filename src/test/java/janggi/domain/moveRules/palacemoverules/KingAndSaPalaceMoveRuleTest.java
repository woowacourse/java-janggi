package janggi.domain.moveRules.palacemoverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class KingAndSaPalaceMoveRuleTest {

    @ParameterizedTest
    @EnumSource(value = PieceType.class, names = {"KING", "SA"})
    @DisplayName("왕과 사는 궁성 안에서만 이동 가능")
    void 왕과_사는_궁성_안에서만_이동_가능(PieceType pieceType) {
        //given
        Map<Position, Piece> customState = new HashMap<>();
        Position currentPosition = new Position(4, 8);
        Piece piece = new Piece(Team.CHO, pieceType);
        customState.put(currentPosition, piece);
        MoveRule oncePalaceMoveRule = new KingAndSaPalaceMoveRule();

        //when
        List<Position> availablePositions = oncePalaceMoveRule.calculateAvailablePositions(currentPosition, Team.CHO,
                customState);

        //then
        assertThat(availablePositions).containsExactlyInAnyOrder(
                new Position(5, 8),
                new Position(4, 9),
                new Position(5, 9)
        );
    }
}
