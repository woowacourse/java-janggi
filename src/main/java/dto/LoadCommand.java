package dto;

public record LoadCommand(Type type, long gameNumberToLoad) {

    public static LoadCommand from(long gameNumberToLoad) {
        if (gameNumberToLoad == 0L) {
            return new LoadCommand(Type.NEW_GAME, gameNumberToLoad);
        }

        return new LoadCommand(Type.LOAD_GAME, gameNumberToLoad);
    }

    public boolean isNewGame() {
        return type == Type.NEW_GAME;
    }

    public boolean isLoadGame() {
        return type == Type.LOAD_GAME;
    }

    enum Type {
        NEW_GAME, LOAD_GAME
    }
}
