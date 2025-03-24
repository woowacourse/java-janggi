package domain.chessPiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.score.Score;
import domain.type.ChessPieceType;
import domain.position.ChessPosition;
import domain.type.ChessTeam;

import java.util.List;

public interface ChessPiece {
    List<ChessPosition> getDestinations(ChessPosition startPosition, ChessPiecePositions positions);
    ChessPieceType getChessPieceType();
    ChessTeam getTeam();
    Score getScore();
    List<Path> getCoordinatePaths(final ChessPosition startPosition);
    HurdlePolicy getHurdlePolicy();
}
