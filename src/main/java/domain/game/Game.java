package domain.game;

import domain.board.Board;
import domain.board.Piece;
import domain.board.Team;
import domain.board.Type;
import domain.vo.Position;

import java.util.Optional;

public class Game {

    private final Turn turn;
    private final Board board;
    private Status status;

    private Game(final Board board, final Team team, final Status status) {
        this.turn = Turn.of(team);
        this.board = board;
        this.status = status;
    }

    public static Game of(final Board board) {
        return new Game(board, Team.CHU, Status.PLAYING);
    }

    public static Game loadGame(final Board board, final Team team, final Status status) {
        return new Game(board, team, status);
    }

    public void tryToMove(Position from, Position to) {
        if (status != Status.PLAYING) {
            throw new IllegalArgumentException("[ERROR] 종료된 게임입니다.");
        }

        Optional<Piece> capturedPiece = board.tryToMove(from, to);

        if (capturedPiece.isPresent() && capturedPiece.get().getType() == Type.GENERAL) {
            Team winner = turn.getTeam();
            status = (winner == Team.CHU) ? Status.CHU_WIN : Status.HAN_WIN;
        }
    }

    public void changeTurn() {
        if (status == Status.PLAYING) {
            turn.change();
        }
    }

    public void checkTurn(Team team) {
        if (team != turn.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 상대편 기물이기 떄문에 움직일 수 없습니다.");
        }
    }

    public int calculateScore(Team team) {
        return board.calculateScore(team);
    }

    public void lose(String turnName) {
        if (turnName.equals(Team.CHU.getName())) {
            this.status = Status.HAN_WIN;
        }
        if (turnName.equals(Team.HAN.getName())) {
            this.status = Status.CHU_WIN;
        }
    }

    public String getTurnDisplayName() {
        return turn.getTeamName();
    }

    public Team getCurrentTeam() {
        return turn.getTeam();
    }

    public Board getBoard() {
        return board;
    }

    public Status getStatus() {
        return status;
    }
}
