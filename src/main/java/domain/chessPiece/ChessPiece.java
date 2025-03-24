package domain.chessPiece;

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
}
