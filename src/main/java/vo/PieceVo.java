package vo;

public class PieceVo {

    private String pieceName;
    private int pointX;
    private int pointY;
    private String team;

    public PieceVo(String pieceName, int pointX, int pointY, String team) {
        this.pieceName = pieceName;
        this.pointX = pointX;
        this.pointY = pointY;
        this.team = team;
    }

    public String getPieceName() {
        return pieceName;
    }

    public int getPointX() {
        return pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public String getTeam() {
        return team;
    }
}
