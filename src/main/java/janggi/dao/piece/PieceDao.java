package janggi.dao.piece;

import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import janggi.piece.players.Team;
import java.util.List;

public interface PieceDao {

    List<PieceDto> select(Team team);

    List<PieceDto> selectAll();

    void insert(PieceDto pieceDto);

    void update(PieceMove pieceMove);

    void delete(PieceMove pieceMove);

    void deleteAll();
}
