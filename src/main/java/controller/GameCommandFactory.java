package controller;

public class GameCommandFactory {

    public static GameCommand create(CommandType type) {
        return switch (type) {
            case MOVE -> new MoveController();
            case PASS -> new PassController();
            case SURRENDER -> new SurrenderController();
        };
    }
}
