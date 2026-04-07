package dto;

public class IntersectionDto {
    private final int y;
    private final int x;
    private final String pieceLabel;
    private final String team;

    public IntersectionDto(int y, int x, String pieceLabel, String team) {
        this.y = y;
        this.x = x;
        this.pieceLabel = pieceLabel;
        this.team = team;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getPieceLabel() {
        return pieceLabel;
    }

    public String getTeam() {
        return team;
    }
}
