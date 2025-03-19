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

    public boolean canMove(Position position) {
        if (!this.board.isExists(position)) {
            return true;
        }
        return !this.board.isSameTeam(position, team);
    }

    // move(Position position) 갈 수 있으면 -> 간다 -> 갔는데 상대 기물이 있으면 먹는다
    // 갈 수 없으면 갈 수 없다고 예외 반환.

    // Set<Position> movableCells();
    // 만약 상대팀 말이면 그것까지 우리팀 말이면 그 전까지
    //

}
