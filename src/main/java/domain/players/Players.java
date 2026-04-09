package domain.players;

import domain.board.Board;
import domain.piece.Side;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Players {
    private final Map<Side, Player> players;

    public Players() {
        players = new LinkedHashMap<>();
        players.put(Side.HAN, new Player(Side.HAN));
        players.put(Side.CHO, new Player(Side.CHO));
    }

    public Players(Map<Side, Player> players) {
        this.players = players;
    }

    public static Players of(Map<Side, Player> players) {
        return new Players(players);
    }

    public void updateState(Board board) {
        players.get(Side.HAN).updateScore(board.calculateScoreBy(Side.HAN));
        players.get(Side.CHO).updateScore(board.calculateScoreBy(Side.CHO));

        if (board.isFinished()) {
            Side winner = board.getWinner();
            players.get(winner).updateStatus(PlayerStatus.WIN);
            players.get(winner.opposite()).updateStatus(PlayerStatus.LOSS);

        }
    }

    public List<Player> getPlayers() {
        return players.values().stream().toList();
    }
}
