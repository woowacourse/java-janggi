package database.dao;

import database.dto.BoardSummaryDto;
import domain.piece.Team;

import java.sql.Connection;
import java.util.List;

public interface BoardDao {

    // TODO JanggiBoard를 받도록 수정.
    Long save(Connection connection);

    List<BoardSummaryDto> readPlayingJanggiBoard(Connection connection);

    void updateBoardTurn(Connection connection, Long boardId, Team nextTurn);

}
