package domain;

import controller.dto.CurrentBoardStatus;
import java.util.List;
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

    public List<CurrentBoardStatus> getCurrentBoardStatus(){
        return board.getCurrentStatus();
    }

    /**
     * 헬퍼 메서드
     */
    private InitializeStrategy getBoardInitializeStrategy(String formationInput) {
        return HorseElephantFormation.getStrategy(formationInput);
    }
}
