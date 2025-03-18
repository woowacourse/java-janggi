package domain;

public class Piece {
    private final Position position;
    private final Team team;
    private final Board board;

    public Piece(Position position, Team team, Board board) {
        this.position = position;
        this.team = team;
        this.board = board;
    }

    public Position getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }

    public Board getBoard() {
        return board;
    }

    public boolean canMove(final int x1, final int y1) {
        if (!this.board.isExists(x1, y1)) {
            return true;
        }
        return !this.board.isSameTeam(x1, y1, Team.BLUE);
    }

}
