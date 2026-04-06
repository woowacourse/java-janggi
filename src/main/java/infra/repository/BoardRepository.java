package infra.repository;

import domain.Team;
import controller.dto.CurrentBoardStatus;
import java.util.List;

public interface BoardRepository {
    void save(List<CurrentBoardStatus> boardStatuses, Team currentTurn);
    List<CurrentBoardStatus> readBoard();
    Team readTurn();
    void deleteAll();
}
