package domain;

import domain.vo.Position;

import java.util.Optional;

public class Game {

    private final Turn turn;
    private final Board board;
    private Status status;

    private Game(final Board board) {
        this.turn = Turn.of();
        this.board = board;
        this.status = Status.PLAYING;
    }

    public static Game of(final Board board) {
        return new Game(board);
    }

    public void tryToMove(Position from, Position to) {
        if (status != Status.PLAYING) {
            throw new IllegalArgumentException("[ERROR] 종료된 게임입니다.");
        }

        Optional<Piece> capturedPiece = board.tryToMove(from, to);

        if (capturedPiece.isPresent() && capturedPiece.get().getType() == Type.GENERAL) {
            Team winner = turn.getTeam();
            if (winner == Team.CHU) {
                status = Status.CHU_WIN;
                return;
            }
            status = Status.HAN_WIN;
            return;
        }

        turn.change();
    }

    public void checkTurn(Team team) {
        if (team != turn.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 상대편 기물이기 떄문에 움직일 수 없습니다.");
        }
    }

    public String getTurnName() {
        return turn.getTeamName();
    }

    public Team getTeam() {
        return turn.getTeam();
    }

    public Board getBoard() {
        return board;
    }

    public Status getStatus() {
        return status;
    }
}
