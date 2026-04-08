package janggi.domain.player;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.turn.ActiveTurn;
import janggi.domain.turn.InactiveTurn;
import janggi.domain.turn.TurnState;
import java.util.List;

public class Players {
    private final List<Player> players;

    private Players(Player cho, Player han) {
        this.players = List.of(cho, han);
    }

    public static Players createInitial(Name choName, Name hanName) {
        validateDuplicateName(choName, hanName);
        return new Players(
                new Player(choName, Side.CHO, ActiveTurn.INSTANCE),
                new Player(hanName, Side.HAN, InactiveTurn.INSTANCE)
        );
    }

    public static Players createRestored(Name choName, Name hanName, Side currentTurn) {
        validateDuplicateName(choName, hanName);
        return new Players(
                new Player(choName, Side.CHO, determineTurnState(Side.CHO, currentTurn)),
                new Player(hanName, Side.HAN, determineTurnState(Side.HAN, currentTurn))
        );
    }

    private static TurnState determineTurnState(Side playerSide, Side currentTurn) {
        if (playerSide == currentTurn) {
            return ActiveTurn.INSTANCE;
        }
        return InactiveTurn.INSTANCE;
    }

    private static void validateDuplicateName(Name choName, Name hanName) {
        if (choName.equals(hanName)) {
            throw new IllegalArgumentException("동일한 플레이어 이름을 사용할 수 없습니다.");
        }
    }

    public Player getCurrentPlayer() {
        return players.stream()
                .filter(Player::isCurrentTurn)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("현재 턴인 플레이어가 없습니다."));
    }

    public Side getCurrentSide() {
        return players.stream()
                .filter(Player::isCurrentTurn)
                .map(Player::getSide)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("현재 턴인 플레이어의 진영이 존재하지 않습니다."));
    }

    public void switchPlayer() {
        players.forEach(Player::toggleTurn);
    }

    public Name getPlayerNameBySide(Side side) {
        return players.stream()
                .filter(player -> player.getSide() == side)
                .map(Player::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 진영의 플레이어가 없습니다."));
    }

    public void validateAlly(Piece piece) {
        getCurrentPlayer().validateAlly(piece);
    }
}
