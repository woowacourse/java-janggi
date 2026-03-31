package domain.board;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;

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
        piece.validateMovable(from, to, Map.copyOf(board));
        resolve(from, to, piece);
    }

    private void resolve(Coordination from, Coordination to, Piece piece) {
        board.put(to, piece);
        board.put(from, new EmptyPiece(Team.NONE));
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
}
