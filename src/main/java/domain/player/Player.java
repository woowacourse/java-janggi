package domain.player;

import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final PlayerProfile playerProfile;
    private final List<Piece> caughtPiece;


    public Player(Name name, Team team) {
        playerProfile = new PlayerProfile(name, team);
        caughtPiece = new ArrayList<>();
    }

    public String getName() {
        return playerProfile.name().value();
    }

    public Team getTeam() {
        return playerProfile.team();
    }

    public List<Piece> getCaughtPiece() {
        return List.copyOf(caughtPiece);
    }

    public void addCaughtPiece(Piece piece) {
        caughtPiece.add(piece);
    }
}
