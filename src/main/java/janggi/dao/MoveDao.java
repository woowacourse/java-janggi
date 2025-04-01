package janggi.dao;

import janggi.dto.MoveDto;
import java.util.List;

public interface MoveDao {

    List<MoveDto> selectAllHistory(int gameId);

    void saveHistory(MoveDto moveDto, int gameId);
}
