package janggi.domain.game;

import janggi.domain.piece.Piece;
import java.util.Map;

public class Players {

    private final Map<Side, Player> players;
    private Turn turn;

    private Players(Map<Side, Player> players) {
        this.players = players;
    }

    public static Players from(String choPlayerName, String hanPlayerName) {
        validateDuplicatedNames(choPlayerName, hanPlayerName);
        Player choPlayer = new Player(choPlayerName, Side.CHO);
        Player hanPlayer = new Player(hanPlayerName, Side.HAN);

        return new Players(Map.of(Side.CHO, choPlayer, Side.HAN, hanPlayer));
    }

    private static void validateDuplicatedNames(String choPlayerName, String hanPlayerName) {
        if (choPlayerName.equals(hanPlayerName)) {
            throw new IllegalArgumentException("[ERROR] 플레이어는 중복된 이름을 가질 수 없습니다.");
        }
    }

    public boolean isCurrentSidePiece(Side side, Piece selectedPiece) {
        return currentPlayer(side).isOwnPiece(selectedPiece);
    }

    public Player currentPlayer(Side side) {
        Player currentPlayer = players.get(side);
        if (currentPlayer == null) {
            throw new IllegalStateException("[ERROR] 현재 턴에 해당하는 플레이어가 없습니다.");
        }
        return currentPlayer;
    }

    public Map<Side, String> getPlayersInfo() {
        Player choPlayer = players.get(Side.CHO);
        Player hanPlayer = players.get(Side.HAN);
        return Map.of(Side.CHO, choPlayer.name(), Side.HAN, hanPlayer.name());
    }
}
