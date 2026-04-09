package janggi.domain.game;

import java.util.Iterator;
import java.util.Set;

public class Players implements Iterable<Player> {
    private static final String ERROR_DUPLICATED_NAME = "[ERROR] 플레이어는 중복된 이름을 가질 수 없습니다.";
    private static final String ERROR_PLAYER_NOT_FOUND = "[ERROR] 해당하는 플레이어를 찾을 수 없습니다.";

    private final Set<Player> players;
    private final Turn turn;

    private Players(Set<Player> players) {
        this.players = players;
        this.turn = new Turn();
    }

    public static Players of(String choPlayerName, String hanPlayerName) {
        validateDuplicatedNames(choPlayerName, hanPlayerName);
        Player choPlayer = new Player(choPlayerName, Side.CHO);
        Player hanPlayer = new Player(hanPlayerName, Side.HAN);

        return new Players(Set.of(choPlayer, hanPlayer));
    }

    private static void validateDuplicatedNames(String choPlayerName, String hanPlayerName) {
        if (choPlayerName.equals(hanPlayerName)) {
            throw new IllegalArgumentException(ERROR_DUPLICATED_NAME);
        }
    }

    public Player getCurrentPlayer() {
        return players.stream()
                .filter(player -> player.isMyTurn(turn))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(ERROR_PLAYER_NOT_FOUND));
    }

    public void switchTurn() {
        turn.switchTurn();
    }

    public Iterator<Player> iterator() {
        return players.iterator();
    }

    public Turn getTurn() {
        return turn;
    }

    public String getChoPlayerName() {
        return getPlayerNameBySide(Side.CHO);
    }

    public String getHanPlayerName() {
        return getPlayerNameBySide(Side.HAN);
    }

    private String getPlayerNameBySide(Side side) {
        return players.stream()
                .filter(player -> player.getSide() == side)
                .map(Player::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(ERROR_PLAYER_NOT_FOUND));
    }
}
