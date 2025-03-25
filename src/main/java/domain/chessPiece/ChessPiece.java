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
    // TODO: 관련 테스트 삭제 및 메서드 삭제
    List<ChessPosition> getDestinations(ChessPosition startPosition, ChessPiecePositions positions);
    List<Path> getCoordinatePaths(ChessPosition startPosition);
    ChessPieceType getChessPieceType();
    HurdlePolicy getHurdlePolicy();
    ChessTeam getTeam();
    Score getScore();
}
