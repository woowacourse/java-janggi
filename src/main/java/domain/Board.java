package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int KIND_COUNT = 2;

    private Map<Position, Piece> alivePieces;

    public Board(Map<Position, Piece> alivePieces) {
        this.alivePieces = new HashMap<>(alivePieces);
    }

    public void movePiece(Position startPosition, Position endPosition, TeamType team) {
        Piece findPiece = findPieceByPosition(startPosition);

        findPiece.validateCanMove(team,startPosition, endPosition, alivePieces);

        changePiecePosition(startPosition, findPiece, endPosition);
    }

    public boolean isFinished() {
        long kingCount = alivePieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .count();
        return kingCount != KIND_COUNT;
    }

    public TeamType findWinTeam() {
        Piece winnerKing = findWinnerKing();
        return winnerKing.getTeamType();
    }

    public ScoreCalculator createScoreCalculator() {
        List<Piece> alivePieces = this.alivePieces.values().stream().toList();
        return new ScoreCalculator(alivePieces);
    }

    private void changePiecePosition(Position startPosition, Piece piece, Position endPosition) {
        alivePieces.remove(startPosition);
        alivePieces.remove(endPosition);
        alivePieces.put(endPosition, piece);
    }

    private Piece findPieceByPosition(Position startPosition) {
        return alivePieces.computeIfAbsent(startPosition, key -> {
            throw new IllegalArgumentException("해당 좌표에는 말이 존재하지 않습니다.");
        });
    }

    private Piece findWinnerKing() {
        List<Piece> aliveKings = alivePieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .toList();
        if (aliveKings.size() != 1) {
            throw new IllegalStateException("아직 게임이 끝나지 않았습니다.");
        }
        return aliveKings.get(0);
    }

    public Map<Position, Piece> getAlivePieces() {
        return new HashMap<>(alivePieces);
    }
}
