package domain.player;

import static common.exception.ErrorMessage.EMPTY_NAME;

import common.exception.JanggiException;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final PlayerProfile playerProfile;
    private final List<Piece> caughtPiece;


    public Player(Name name, Team team) {
        validateNotBlank(name.value());
        playerProfile = new PlayerProfile(name, team);
        caughtPiece = new ArrayList<>();
    }

    public boolean hasName(String name) {
        return playerProfile.name().value().equals(name);
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

    private void validateNotBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new JanggiException(EMPTY_NAME.getMessage());
        }
    }
}
