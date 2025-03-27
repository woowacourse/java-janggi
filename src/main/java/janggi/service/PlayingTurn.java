package janggi.service;

import janggi.domain.Team;

public class PlayingTurn {

    private static final int MAX_ROUND = 30;

    private Team current;
    private int round;

    public PlayingTurn() {
        this(Team.CHO, 1);
    }

    public PlayingTurn(Team current, int round) {
        this.current = current;
        this.round = round;
    }

    public void toss() {
        this.round++;
        current = Team.values()[this.round % 2];
    }

    public boolean isEnded() {
        return round >= MAX_ROUND;
    }

    public Team currentTeam() {
        return this.current;
    }

    public int currentRound() {
        return round;
    }

    // TODO
    /*
    MoveVector, InfiniteMoveVector
    상속관계, 네이밍 고려
    PieceSearcher 네이밍 고려

    PlayingTurn 테스트
    GameService 테스트
     */
}
