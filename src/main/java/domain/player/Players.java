package domain.player;

import domain.Team;
import domain.piece.Piece;
import java.util.Objects;

public class Players {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player turn;

    public Players(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.turn = firstPlayer;
    }

    public static Players ofNames(String firstPlayerName, String secondPlayerName) {
        return new Players(new Player(firstPlayerName, Team.CHO), new Player(secondPlayerName, Team.HAN));
    }

    public void nextTurn() {
        if (turn == firstPlayer) {
            turn = secondPlayer;
            return;
        }
        turn = firstPlayer;
    }

    public boolean isSameTeamThisTurnPlayerAndPiece(Piece piece) {
        return turn.isTeam(piece);
    }

    public Player getThisTurnPlayer() {
        return turn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Players players = (Players) o;
        return Objects.equals(firstPlayer, players.firstPlayer) && Objects.equals(secondPlayer,
                players.secondPlayer) && Objects.equals(turn, players.turn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPlayer, secondPlayer, turn);
    }
}
