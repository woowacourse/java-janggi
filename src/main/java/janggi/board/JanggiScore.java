package janggi.board;

import janggi.piece.Country;

public record JanggiScore(double value) {

    private static final JanggiScore MAX_JANGGI_SCORE_OF_HAN = new JanggiScore(73.5);
    private static final JanggiScore MAX_JANGGI_SCORE_OF_CHO = new JanggiScore(72);


    public JanggiScore plus(final JanggiScore janggiScore) {
        return new JanggiScore(value + janggiScore.value);
    }

    public JanggiScore minus(final JanggiScore janggiScore){
        return new JanggiScore(value - janggiScore.value);
    }

    public JanggiScore calculateScoreByCountry(final Country country){
        if (country == Country.HAN) {
            return MAX_JANGGI_SCORE_OF_HAN.minus(this);
        }
        return MAX_JANGGI_SCORE_OF_CHO.minus(this);
    }
}
