package janggi.board;

import janggi.piece.Country;
import janggi.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Pieces {
    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces){
        this.pieces = new ArrayList<>(pieces);
    }

    public JanggiScore calculateAllScoreByCountry(final Country country){
        JanggiScore janggiScore = new JanggiScore(0);
        for(final Piece piece : pieces){
            janggiScore = piece.plusScore(janggiScore);
        }
        return janggiScore.calculateScoreByCountry(country);
    }
}
