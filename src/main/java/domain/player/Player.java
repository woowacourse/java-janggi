package domain.player;

import common.exception.JanggiException;
import domain.piece.BasicPiece;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final PlayerProfile playerProfile;
    private final List<Piece> pieces;

    public Player(Name name, Team team) {
        validateNotBlank(name.value());
        playerProfile = new PlayerProfile(name, team);
        pieces = new ArrayList<>();
    }

    public boolean hasName(String name) {
        return playerProfile.hasName(name);
    }

    public boolean isDifferentTeam(BasicPiece piece) {
        return piece.isDifferentTeam(playerProfile.team());
    }

    public PlayerProfile getProfile() {
        return playerProfile;
    }

    private void validateNotBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new JanggiException("플레이어의 이름은 빈 문자열일 수 없습니다.");
        }
    }

    public void addPiece(Piece piece) {
        validatePiece(piece);
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public double calculateScore() {
        double pieceScore = pieces.stream()
                .mapToDouble(this::calculatePieceScore)
                .sum();
        return pieceScore + playerProfile.team().getScore();
    }

    private double calculatePieceScore(Piece piece) {
        return piece.getPieceType().score();
    }

    private void validatePiece(Piece piece) {
        if (piece == null) {
            throw new JanggiException("기물은 null일 수 없습니다.");
        }
        if (piece.isDifferentTeam(playerProfile.team())) {
            throw new JanggiException("플레이어와 다른 팀의 기물은 추가할 수 없습니다.");
        }
    }
}
