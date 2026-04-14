package janggi.view.dto;

public class GameRoom {

    private final long id;
    private final String turn;
    private final boolean isOnGoing;

    private GameRoom(Long id, String turn, boolean isOnGoing) {
        this.id = id;
        this.turn = turn;
        this.isOnGoing = isOnGoing;
    }

    public static GameRoom from(long id, String turn, boolean isOnGoing) {
        return new GameRoom(id, turn, isOnGoing);
    }

    public long getId() {
        return id;
    }

    public String getTurn() {
        return turn;
    }

    public boolean isOnGoing() {
        return isOnGoing;
    }
}
