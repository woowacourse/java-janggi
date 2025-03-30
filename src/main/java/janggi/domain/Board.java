package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Board {
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
        Piece piece = board.get(beforePosition);

        validateTurn(team, piece);
        if (piece.isPalacePiece() && palacePositions.contains(beforePosition) && palacePositions.contains(
                afterPosition)) {
            piece.getPalaceMovableValidator(beforePosition, afterPosition).accept(new Pieces(board));
        }
        piece.getMovableValidator(beforePosition, afterPosition).accept(new Pieces(board));
        board.put(beforePosition, new None());
        board.put(afterPosition, piece);
    }

    private void validateTurn(final Team team, final Piece piece) {
        if (!piece.is(team)) {
            throw new IllegalArgumentException("지금은 " + team.getName() + "팀 기물만 이동할 수 있습니다.");
        }
    }

    public boolean checkGameOver() {
        return board.values().stream()
                .filter(Piece::isGeneral)
                .count() != 2;
    }

    public Team getWinner() {
        if (board.values().stream()
                .noneMatch(piece -> piece.isGeneral() && piece.getTeam() == Team.RED)) {
            return Team.BLUE;
        }
        if (board.values().stream()
                .noneMatch(piece -> piece.isGeneral() && piece.getTeam() == Team.BLUE)) {
            return Team.RED;
        }
        return Team.NONE;
    }

    public int calculateScoreByTeam(final Team team) {
        return board.values().stream()
                .filter(piece -> piece.getTeam() == team)
                .mapToInt(Piece::getScore)
                .sum();
    }
}
