package domain.game;

import domain.board.*;
import domain.vo.Position;

import java.util.Optional;

public class Game {

    private final Long id;
    private final Turn turn;
    private final Board board;
    private Status status;

    private Game(Long id, final Board board, final Team team, final Status status) {
        this.id = id;
        this.turn = Turn.of(team);
        this.board = board;
        this.status = status;
    }

    public static Game of(final Board board) {
        return new Game(null, board, Team.CHU, Status.PLAYING);
    }

    public static Game loadGame(final Long id, final Board board, final Team team, final Status status) {
        return new Game(id, board, team, status);
    }

    public void validateFromPosition(Position from) {
        Piece piece = board.findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));
        checkTurn(piece.getTeam());
    }

    public MoveResult validateMove(Position from, Position to) {
        validateFromPosition(from);
        board.validateMove(from, to);

        Optional<Piece> capturedPiece = board.findPieceByPosition(to);
        Status nextStatus = getNextStatus(capturedPiece);
        Team nextTeam = getNextTeam(nextStatus);

        return new MoveResult(from, to, capturedPiece.isPresent(), nextStatus, nextTeam);
    }

    public void applyMoveResult(MoveResult moveResult) {
        board.movePiece(moveResult.from(), moveResult.to());
        this.status = moveResult.status();
        this.turn.change();
    }

    public void checkTurn(Team team) {
        if (team != turn.getTeam()) {
            throw new IllegalArgumentException("해당 기물은 상대편 기물이기 떄문에 움직일 수 없습니다.");
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

    public Long getId() {
        return id;
    }

    private Team getNextTeam(Status nextStatus) {
        Team nextTeam = turn.getTeam();
        if (nextStatus == Status.PLAYING) {
            nextTeam = turn.getTeam().opposite();
        }
        return nextTeam;
    }

    private Status getNextStatus(Optional<Piece> capturedPiece) {
        Status nextStatus = status;
        if (capturedPiece.isPresent() && capturedPiece.get().getType() == Type.GENERAL) {
            if (turn.getTeam() == Team.CHU)
                nextStatus = Status.CHU_WIN;
            if (turn.getTeam() == Team.HAN)
                nextStatus = Status.HAN_WIN;
        }
        return nextStatus;
    }
}
