package domain;

import domain.piece.Piece;
import java.util.List;

public class JanggiGame {
    private final List<Player> players;
    private final Board board;

    public JanggiGame(List<Player> players, List<Piece> pieces) {
        this.players = players;
        this.board = new Board(pieces);
    }

}
