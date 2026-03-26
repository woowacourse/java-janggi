package domain.player;

import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final Name name;
    private final Team team;
    private final List<Piece> catchedPiece;


    public Player(Name name, Team team) {
        this.name = name;
        this.team = team;
        catchedPiece = new ArrayList<>();
    }

    public String getName() {
        return name.getValue();
    }

    public Team getTeam() {
        return team;
    }

    public List<Piece> getCatchedPiece() {
        return List.copyOf(catchedPiece);
    }

    public void addCatchedPiece(Piece piece) {
        catchedPiece.add(piece);
    }
}
