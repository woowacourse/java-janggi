package domain.state;

import domain.constant.Country;

public class Finished implements State{
    private final Country winner;

    public Finished(Country winner) {
        this.winner = winner;
    }

    @Override
    public State changeTurn() {
        throw new IllegalArgumentException("게임이 종료되었습니다.");
    }

    @Override
    public Country getCountry() {
        return winner;
    }
}
