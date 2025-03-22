package domain;

import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public class JanggiGame {

    private final Players players;
    private final Board board;

    public JanggiGame(Players players, Map<Position, Piece> pieces) {
        this.players = players;
        this.board = new Board(pieces);
    }

    public void movePiece(Position startPosition, Position endPosition, TeamType teamType) {
        board.movePiece(startPosition, endPosition, teamType);
    }

    public Player findWinner() {
        TeamType winTeam = board.findWinTeam();
        return findPlayerByTeam(winTeam);
    }

    public Map<Position, Piece> getAlivePieces() {
        return board.getAlivePieces();
    }

    public boolean isInProgress(){
        return board.isInProgress();
    }


    public Player findPlayerByTeam(TeamType playerTeam) {
        return players.getTeamPlayer(playerTeam);
    }
}
