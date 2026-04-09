package database.dao;

import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import domain.piece.Team;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BoardDao {

    Long save() throws SQLException;

    List<BoardSummaryDto> readAllPlaying() throws SQLException;

    Optional<BoardSummaryDto> readPlayingById(Long boardId) throws SQLException;

    void updateTurn(Long boardId, Team nextTurn) throws SQLException;

    void updateResult(Long boardId, GameResult gameResult) throws SQLException;

}
