package janggi.domain.moveRules.palaceMoveRules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Palace;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import janggi.domain.moveRules.palaceMoveRule.ChaPalaceMoveRule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaPalaceMoveRuleTest {

    @Test
    @DisplayName("차는 궁성 모서리와 중앙에서 대각선으로 이동할 수 있다")
    void 차의_궁성에서_이동_가능_경로() {
        //given
        Map<Position, Piece> customState = new HashMap<>();
        Position chaCurrentPosition = new Position(4, 10);
        Team choTeam = Team.CHO;
        Piece cha = new Piece(Team.CHO, PieceType.CHA);
        customState.put(chaCurrentPosition, cha);
        MoveRule chaPalaceMoveRule = new ChaPalaceMoveRule();
        Position chaPalaceCenter = Palace.CHO_PALACE_CENTER;
        Position pointReflect = Palace.calculateOppositePalaceDiagonalPosition(chaCurrentPosition);

        //when
        List<Position> availablePositions = chaPalaceMoveRule.calculateAvailablePositions(chaCurrentPosition, choTeam,
                customState);

        //then
        assertThat(availablePositions).containsAll(List.of(chaPalaceCenter, pointReflect));
    }


}
