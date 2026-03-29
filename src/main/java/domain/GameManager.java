package domain;

import java.util.Map;
import strategy.InitializeStrategy;

public class GameManager {
    private final Board board;

    public GameManager(Map<Team, String> formationInput) {
        this.board = new Board(
                getBoardInitializeStrategy(formationInput.get(Team.CHO)),
                getBoardInitializeStrategy(formationInput.get(Team.HAN))
        );
    }

    /**
     * 헬퍼 메서드
     */
    private InitializeStrategy getBoardInitializeStrategy(String formationInput) {
        return HorseElephantFormation.getStrategy(formationInput);
    }
}
