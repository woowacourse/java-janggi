package domain;

import domain.chesspiece.ChessPiece;
import domain.hurdlePolicy.HurdlePolicy;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.score.Score;
import domain.type.ChessTeam;
import java.util.List;

public class Board {

    private final ChessPiecePositions chessPiecePositions;

    public Board(final ChessPiecePositions chessPiecePositions) {
        this.chessPiecePositions = chessPiecePositions;
    }

    public void move(final ChessPosition from, final ChessPosition to) {
        final ChessPiece chessPiece = chessPiecePositions.findPieceByPosition(from);
        chessPiecePositions.move(chessPiece, to);
    }

    public ChessPiece getChessPieceByPosition(final ChessPosition from) {
        return chessPiecePositions.findPieceByPosition(from);
    }

    public List<ChessPosition> getAvailableDestinations(final ChessPosition from, final ChessPiece chessPiece) {
        final List<Path> coordinatePaths = chessPiece.calculateCoordinatePaths(from);
        final HurdlePolicy hurdlePolicy = chessPiece.getHurdlePolicy();
        return hurdlePolicy.pickDestinations(chessPiece.getTeam(), coordinatePaths, chessPiecePositions);
    }

    public void validateExistPieceByPosition(final ChessPosition from) {
        if (!chessPiecePositions.existPieceByPosition(from)) {
            throw new IllegalArgumentException("해당 위치는 기물이 존재하지 않습니다.");
        }
    }

    public void validateCurrentTeam(final ChessPosition from, final ChessTeam team) {
        final ChessPiece chessPiece = getChessPieceByPosition(from);
        if (!chessPiece.matchTeam(team)) {
            throw new IllegalArgumentException(String.format("현재 턴은 %s 입니다.", team.getName()));
        }
    }

    public Score calculateScoreByTeam(final ChessTeam team) {
        return chessPiecePositions.calculateScoreByTeam(team);
    }

    public void validateToPosition(final List<ChessPosition> chessPositions, final ChessPosition to) {
        if (!chessPositions.contains(to)) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
    }

    public List<ChessPiece> getChessPieces() {
        return chessPiecePositions.getChessPieces();
    }
}
