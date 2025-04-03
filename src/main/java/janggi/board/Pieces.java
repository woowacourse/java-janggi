package janggi.board;

import janggi.piece.Country;
import janggi.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Pieces {

    private static final JanggiScore MAX_JANGGI_SCORE_OF_HAN = new JanggiScore(73.5);
    private static final JanggiScore MAX_JANGGI_SCORE_OF_CHO = new JanggiScore(72);

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces){
        this.pieces = new ArrayList<>(pieces);
    }

    public JanggiScore calculateAllScoreByCountry(final Country country){
        JanggiScore janggiScore = new JanggiScore(0);
        for(final Piece piece : pieces){
            janggiScore = piece.plusScore(janggiScore);
        }
        return calculateScoreByCountry(janggiScore, country);
    }

    private JanggiScore calculateScoreByCountry(final JanggiScore janggiScore, final Country country){
        if(country == Country.HAN){
            return MAX_JANGGI_SCORE_OF_HAN.minus(janggiScore);
        }
        return MAX_JANGGI_SCORE_OF_CHO.minus(janggiScore);
    }
}
