package janggi.piece;

import janggi.player.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pieces {

    private final List<Piece> elements;

    public Pieces(final List<Piece> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

    public static Pieces from(final Collection<Piece> elements) {
        return new Pieces(elements.stream().toList());
    }

    public static Pieces empty() {
        return new Pieces(List.of());
    }

    public Pieces add(final Piece piece) {
        final List<Piece> newPieces = new ArrayList<>(this.elements);
        newPieces.add(piece);
        return new Pieces(newPieces);
    }

    public Pieces addAll(final List<Piece> pieces) {
        final List<Piece> newPieces = new ArrayList<>(this.elements);
        newPieces.addAll(pieces);
        return new Pieces(newPieces);
    }

    public Pieces addAll(final Pieces pieces) {
        return addAll(pieces.getPieces());
    }

    public Pieces getByTeam(final Team team) {
        return new Pieces(elements.stream()
                .filter(piece -> piece.isAlly(team))
                .toList());
    }

    public List<Piece> getPieces() {
        return elements;
    }
}
