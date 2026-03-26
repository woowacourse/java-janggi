package janggi.domain;

import janggi.domain.movestorage.MoveStorage;

public class Piece {
    private final MoveStorage moveStorage;
    private final Team team;
    private final int score;
    private final String name;

    public Piece(MoveStorage moveStorage, Team team, int score, String name) {
        this.moveStorage = moveStorage;
        this.team = team;
        this.score = score;
        this.name = name;
    }
    // TODO: 이동가능 여부 구현 (2026. 3. 26.)
    public void verifyMove(Position from, Position to, BoardState boardState) {

    }

    public String getName() {
        return name;
    }
}
