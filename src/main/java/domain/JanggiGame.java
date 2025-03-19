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

    public void movePiece(Position startPosition, Position endPosition){
        board.movePiece(startPosition,endPosition);
    }


    public Player findWinner() {
        TeamType winTeam = board.findWinTeam();
        return players.stream()
                .filter(player -> player.isSameTeam(winTeam))
                .findAny()
                .orElseThrow(()-> new IllegalStateException("승자가 없습니다."));
    }
}
