package database.dao;

import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import domain.piece.Team;

import java.util.List;
import java.util.Optional;

public interface BoardDao {

    Long save();

    List<BoardSummaryDto> selectNotFinishedBoards();

    Optional<BoardSummaryDto> selectBoardSummary(Long boardId);

    void updateTurn(Long boardId, Team nextTurn);

    void updateResult(Long boardId, GameResult gameResult);

    boolean existsById(Long boardId);

}
