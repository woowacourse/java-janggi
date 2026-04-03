package infrastructure.repository;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;
import domain.setup.Arrangement;
import java.util.Map;
import java.util.Optional;

public interface GameRepository {
    long save(long roomId);
    void updateState(long gameId, String stateName, Team currentTeam);
    void updateArrangement(long gameId, Team team, Arrangement arrangement);
    void updateBoard(long gameId, Map<Position, Piece> pieces);
    Optional<GameDto> findLatestByRoom(long roomId);
}
