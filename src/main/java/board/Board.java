package board;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Turn;
import piece.Piece;
import piece.PieceType;
import piece.Team;

public class Board {

    private static final int TOTAL_KING_COUNT = 2;
    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void isValidTurn(final Position startPosition, final Turn turn) {
        Piece pieceByPosition = findPieceByPosition(startPosition);
        Team currentTurnTeam = turn.getCurrentTurnTeam();
        if (!pieceByPosition.isSameTeam(currentTurnTeam)) {
            throw new IllegalArgumentException(
                    String.format("올바른 기물의 위치를 입력해주세요(현재 턴: %s).", currentTurnTeam.name())
            );
        }
    }

    public void move(final Position start, final Position destination) {
        Piece movingPiece = findPieceByPosition(start);
        if (movingPiece.canMove(start, destination, this)) {
            pieces.remove(start);
            pieces.put(destination, movingPiece);
            return;
        }
        throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
    }

    public boolean isExists(final Position position) {
        return pieces.containsKey(position);
    }

    public boolean isSameTeamPosition(final Team team, final Position position) {
        if (!pieces.containsKey(position)) {
            return false;
        }
        return pieces.get(position)
                .isSameTeam(team);
    }

    public boolean isCannonPosition(final Position position) {
        if (!pieces.containsKey(position)) {
            return false;
        }
        return pieces.get(position).getType() == PieceType.CANNON;
    }

    public Piece findPieceByPosition(final Position position) {
        if (pieces.containsKey(position)) {
            return pieces.get(position);
        }
        throw new IllegalArgumentException("올바른 기물의 위치를 입력해주세요.");
    }

    public double calculateTeamScore(final Team team, final Double bonusScore) {
        return bonusScore + pieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    public boolean isAllKingAlive() {
        return TOTAL_KING_COUNT != (int) pieces.values()
                .stream()
                .filter(piece -> piece.getType() == PieceType.KING)
                .count();
    }

    public Team findWinnerTeam() {
        List<Team> winnerTeam = pieces.values().stream()
                .filter(piece -> piece.getType() == PieceType.KING)
                .map(Piece::getTeam)
                .toList();
        if (winnerTeam.size() == TOTAL_KING_COUNT) {
            throw new IllegalStateException("양팀의 궁이 모두 살아있어 승자가 존재하지 않습니다.");
        }
        return winnerTeam.getFirst();

    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

}
