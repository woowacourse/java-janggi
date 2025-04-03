package janggi.board;

import janggi.GameState;
import janggi.board.palace.Palace;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> janggiBoard;
    private final Palace palace;

    public Board() {
        this.palace = Palace.AREA;
        this.janggiBoard = new HashMap<>();
    }

    public void deployPiece(final Position position, final Piece piece) {
        janggiBoard.put(position, piece);
    }

    public GameState pieceMove(final Position currentPosition, final Position targetPosition) {
        final Piece piece = janggiBoard.get(currentPosition);
        piece.moveTo(currentPosition, targetPosition, janggiBoard, palace);
        return updatePiecePosition(currentPosition, targetPosition);
    }

    private GameState updatePiecePosition(final Position currentPosition, final Position targetPosition) {
        final Piece currentPiece = janggiBoard.remove(currentPosition);
        final Piece targetPiece = janggiBoard.get(targetPosition);

        if (isKingCapture(targetPiece)) {
            janggiBoard.put(targetPosition, currentPiece);
            return GameState.END;
        }

        janggiBoard.put(targetPosition, currentPiece);
        return GameState.IN_PROGRESS;
    }

    private boolean isKingCapture(final Piece piece) {
        return piece != null && PieceType.isKing(piece.getPieceType());
    }

    public double calculateTotalScore(final Team team) {
        final double totalScore = calculateTotalScoreBy(team);
        if (team == Team.HAN) {
            return totalScore + 1.5;
        }
        return totalScore;
    }

    private double calculateTotalScoreBy(final Team team) {
        double totalScore = 0;

        for (final Piece piece : janggiBoard.values()) {
            if (piece.getTeam() == team) {
                totalScore = piece.sumScore(totalScore);
            }
        }
        return totalScore;
    }

    public void validateEmptyPieceBy(final Position currentPosition) {
        if (isNotContainPiece(currentPosition)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다. 기물이 존재하는 좌표를 입력해 주세요.");
        }
    }

    private boolean isNotContainPiece(final Position currentPosition) {
        return !janggiBoard.containsKey(currentPosition);
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }
}
