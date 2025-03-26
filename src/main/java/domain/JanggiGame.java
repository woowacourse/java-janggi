package domain;

import domain.participants.Player;
import domain.participants.Players;
import domain.piece.Piece;
import domain.piece.TeamType;
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

    public Map<Position, Piece> getAlivePiecesInfo() {
        return board.getAlivePieces();
    }

    public boolean isFinished() {
        return board.isFinished();
    }

    public Player findPlayerByTeam(TeamType playerTeam) {
        return players.getTeamPlayer(playerTeam);
    }
}
