package janggi.domain.game;

import janggi.domain.piece.Piece;
import java.util.Map;

public class Players {

    private final Map<Side, Player> players;
    private Turn turn;

    private Players(Map<Side, Player> players, Turn turn) {
        this.players = players;
        this.turn = turn;
    }

    public static Players from(String choPlayerName, String hanPlayerName) {
        validateDuplicatedNames(choPlayerName, hanPlayerName);
        Player choPlayer = new Player(choPlayerName, Side.CHO);
        Player hanPlayer = new Player(hanPlayerName, Side.HAN);

        return new Players(Map.of(Side.CHO, choPlayer, Side.HAN, hanPlayer), Turn.init());
    }

    public static Players fromCurrentSide(String choPlayerName, String hanPlayerName, Turn turn) {
        validateDuplicatedNames(choPlayerName, hanPlayerName);
        Player choPlayer = new Player(choPlayerName, Side.CHO);
        Player hanPlayer = new Player(hanPlayerName, Side.HAN);

        return new Players(Map.of(Side.CHO, choPlayer, Side.HAN, hanPlayer), turn);
    }

    private static void validateDuplicatedNames(String choPlayerName, String hanPlayerName) {
        if (choPlayerName.equals(hanPlayerName)) {
            throw new IllegalArgumentException("[ERROR] 플레이어는 중복된 이름을 가질 수 없습니다.");
        }
    }

    public boolean isCurrentSidePiece(Piece selectedPiece) {
        return currentPlayer().isOwnPiece(selectedPiece);
    }

    public Player currentPlayer() {
        if (!players.containsKey(turn.currentSide())) {
            throw new IllegalStateException("[ERROR] 현재 턴에 해당하는 플레이어가 없습니다.");
        }
        return players.get(turn.currentSide());
    }

    public Map<Side, String> getPlayersInfo() {
        Player choPlayer = players.get(Side.CHO);
        Player hanPlayer = players.get(Side.HAN);
        return Map.of(Side.CHO, choPlayer.name(), Side.HAN, hanPlayer.name());
    }

    public void nextTurn() {
        turn.next();
    }
}
