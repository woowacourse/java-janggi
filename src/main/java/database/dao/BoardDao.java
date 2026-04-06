package database.dao;

import database.dto.BoardSummaryDto;

import java.sql.Connection;
import java.util.List;

public interface BoardDao {

    // TODO JanggiBoard를 받도록 수정.
    Long save(Connection connection);

    List<BoardSummaryDto> readPlayingJanggiBoard(Connection connection);

}
