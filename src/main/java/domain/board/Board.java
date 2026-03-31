package domain.board;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.List;
import java.util.Map;

public class Board {

    protected final Map<Coordination, Piece> board;

    public Board(Map<Coordination, Piece> board) {
        this.board = board;
    }

    public Map<Coordination, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public void move(Coordination from, Coordination to) {
        Piece piece = board.get(from);

        validateRule(from, to, piece);
        validatePiecesOnPath(from, to, piece);
        validateNotSameTeam(to, piece);

        resolve(from, to, piece);
    }

    public boolean hasTwoGenerals() {
        return board.keySet().stream()
                .filter(key -> board.get(key).isGeneral())
                .count() == 2;
    }

    public void checkSameTeam(Coordination coordination, Turn turn) {
        Piece piece = board.get(coordination);
        piece.validateSameTeam(turn);
    }

    private void validateNotSameTeam(Coordination to, Piece piece) {
        piece.validateNotSameTeam(board.get(to));
    }

    private void validatePiecesOnPath(Coordination from, Coordination to, Piece piece) {
        List<Coordination> path = piece.resolvePath(from, to);
        List<Piece> piecesOnPath = path.stream()
                .map(board::get)
                .filter(p -> !p.isEmpty())
                .toList();
        piece.validatePath(piecesOnPath);
    }

    private void validateRule(Coordination from, Coordination to, Piece piece) {
        piece.validateRule(from, to);
    }

    private void resolve(Coordination from, Coordination to, Piece piece) {
        board.put(to, piece);
        board.put(from, new EmptyPiece(Team.NONE));
    }
}
