package database.dao;

import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import domain.piece.Team;

import java.sql.Connection;
import java.util.List;

public interface BoardDao {

    // TODO JanggiBoard를 받도록 수정.
    Long save(Connection connection);

    List<BoardSummaryDto> readAllPlaying(Connection connection);

    BoardSummaryDto readPlayingById(Connection connection, Long boardId);

    void updateTurn(Connection connection, Long boardId, Team nextTurn);

    void updateResult(Connection connection, Long boardId, GameResult gameResult);

}
