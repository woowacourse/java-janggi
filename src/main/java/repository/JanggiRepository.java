package repository;

import model.coordinate.Position;
import model.game.GameStatus;
import model.game.Team;
import model.game.dto.GameDto;
import model.piece.Piece;
import repository.command.MoveCommand;

import java.util.Map;
import java.util.Optional;

public interface JanggiRepository {

    long saveGame(Team turn, Map<Position, Piece> board);

    void updateGame(long gameId, MoveCommand moveCommand, GameStatus status);

    Optional<GameDto> findRecentGame();

    Map<Position, Piece> findPiecesByGameId(long gameId);

    void updateCurrentGameStatus(Long gameId, GameStatus gameStatus);
}
