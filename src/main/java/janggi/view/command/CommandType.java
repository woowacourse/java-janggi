package janggi.view.command;

public enum CommandType {

    MOVE,
    SAVE,
    QUIT,
    ;

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isSave() {
        return this == SAVE;
    }

    public boolean isQuit() {
        return this == QUIT;
    }
}
