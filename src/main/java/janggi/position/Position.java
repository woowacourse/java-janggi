package janggi.position;

public record Position(Row x, Column y) {

    public int getRow() {
        return x.value();
    }

    public int getColumn() {
        return y.value();
    }


}
