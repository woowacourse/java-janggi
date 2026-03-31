package domain.board;

import domain.coordination.Coordination;
import domain.game.Turn;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import domain.game.dto.BoardDto;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int TOTAL_GENERAL_COUNT = 2;

    protected final Map<Coordination, Piece> board;

    public Board(Map<Coordination, Piece> board) {
        this.board = board;
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
                .count() == TOTAL_GENERAL_COUNT;
    }

    public void checkSameTeam(List<Integer> inputTokens, Turn turn) {
        Coordination coordination = Coordination.of(inputTokens.get(0), inputTokens.get(1));
        Piece piece = board.get(coordination);
        piece.isSameTeam(turn);
    }

    public Map<Coordination, Piece> getBoard() {
        return Map.copyOf(this.board);
    }
}
