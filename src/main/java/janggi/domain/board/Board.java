package janggi.domain.board;

import janggi.domain.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import janggi.domain.piece.impl.None;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Board {
    private static final double SCORE_SECOND_TURN_BENEFIT = 1.5;

    private final Map<Position, Piece> board;
    private final Set<Position> palacePositions;

    public Board(final Map<Position, Piece> board, final Set<Position> palacePositions) {
        this.board = new HashMap<>(board);
        this.palacePositions = palacePositions;
    }

    public Map<Position, Piece> getBoard() {
        return new HashMap<>(board);
    }

    public void movePiece(final Team team, final Position beforePosition, final Position afterPosition) {
        Piece pieceToMove = board.get(beforePosition);

        validateTurn(team, pieceToMove);
        validateMovement(beforePosition, afterPosition, pieceToMove);
        executeMovement(beforePosition, afterPosition, pieceToMove);
    }

    private void validateTurn(final Team team, final Piece piece) {
        if (!piece.is(team)) {
            throw new IllegalArgumentException("지금은 " + team.getName() + "팀 기물만 이동할 수 있습니다.");
        }
    }

    private void validateMovement(final Position beforePosition, final Position afterPosition, final Piece piece) {
        if (piece.isPalacePiece() && palacePositions.contains(beforePosition) && palacePositions.contains(
                afterPosition)) {
            piece.getPalaceMovableValidator(beforePosition, afterPosition).accept(new Pieces(board));
            return;
        }
        piece.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(board));
    }

    private void executeMovement(final Position beforePosition, final Position afterPosition, final Piece piece) {
        board.put(beforePosition, new None());
        board.put(afterPosition, piece);
    }

    public boolean checkGameOver() {
        return board.values().stream()
                .filter(Piece::isGeneral)
                .count() != 2;
    }

    public Team getWinner() {
        if (!hasGeneral(Team.RED)) {
            return Team.BLUE;
        }
        if (!hasGeneral(Team.BLUE)) {
            return Team.RED;
        }
        return Team.NONE;
    }

    private boolean hasGeneral(final Team team) {
        return board.values().stream()
                .anyMatch(piece -> piece.isGeneral() && piece.getTeam() == team);
    }

    public double calculateScoreByTeam(final Team team, final Turn turn) {
        int sum = board.values().stream()
                .filter(piece -> piece.getTeam() == team)
                .mapToInt(Piece::getScore)
                .sum();

        if (turn.startedBy(team)) {
            return sum;
        }
        return sum + SCORE_SECOND_TURN_BENEFIT;
    }
}
