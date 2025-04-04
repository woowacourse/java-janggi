package domain.chesspiece;

import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.ChessPosition;
import domain.score.Score;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;
import java.util.List;

public interface ChessPiece {
    ChessPieceType getChessPieceType();
    ChessTeam getTeam();
    Score getScore();
    List<Path> calculateCoordinatePaths(final ChessPosition startPosition);
    HurdlePolicy getHurdlePolicy();
    ChessPiece from(final ChessPosition position);
    boolean matchPosition(final ChessPosition position);
    ChessPosition getPosition();
    boolean matchTeam(final ChessTeam team);
}
