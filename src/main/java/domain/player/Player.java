package domain.player;

import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final Name name;
    private final Team team;
    private final List<Piece> caughtPiece;


    public Player(Name name, Team team) {
        this.name = name;
        this.team = team;
        caughtPiece = new ArrayList<>();
    }

    public String getName() {
        return name.value();
    }

    public Team getTeam() {
        return team;
    }

    public List<Piece> getCaughtPiece() {
        return List.copyOf(caughtPiece);
    }

    public void addCaughtPiece(Piece piece) {
        caughtPiece.add(piece);
    }
}
