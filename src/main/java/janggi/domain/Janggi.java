package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.strategy.ElephantHorseElephantHorse;
import janggi.domain.board.strategy.ElephantHorseHorseElephant;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.board.strategy.HorseElephantElephantHorse;
import janggi.domain.board.strategy.HorseElephantHorseElephant;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public class Janggi {
    private static final List<FormationStrategy> FORMATIONS = List.of(
            new HorseElephantElephantHorse(),
            new HorseElephantHorseElephant(),
            new ElephantHorseHorseElephant(),
            new ElephantHorseElephantHorse()
    );

    private final Board board;
    private Camp currentCamp;
    private boolean running;

    private Janggi(Board board) {
        this.board = board;
        this.currentCamp = Camp.CHO;
        this.running = true;
    }

    public static Janggi start(int choFormationNumber, int hanFormationNumber) {
        return new Janggi(BoardFactory.create(
                readFormation(choFormationNumber),
                readFormation(hanFormationNumber)));
    }

    private static FormationStrategy readFormation(int choice) {
        if (choice < 1 || choice > FORMATIONS.size()) {
            throw new IllegalArgumentException("1~4 중 선택해주세요.");
        }
        return FORMATIONS.get(choice - 1);
    }

    public void play(Position from, Position to) {
        board.movePiece(from, to);
        if (!board.isAliveEssentialPiece(currentCamp.next())) {
            finish();
            return;
        }
        currentCamp = currentCamp.next();
    }

    public void validateTurn(Position from) {
        Piece piece = board.selectPiece(from);
        if (!piece.isSameCamp(currentCamp)) {
            throw new IllegalArgumentException("자신의 기물만 선택할 수 있습니다.");
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void finish() {
        running = false;
    }

    public Camp currentCamp() {
        return currentCamp;
    }

    public Map<Position, Piece> getBoard() {
        return board.janggiBoard();
    }

    public void surrender() {
        finish();
        currentCamp = currentCamp.next();
    }

    public void draw() {
        finish();
        currentCamp = board.calculateScoreResult();
    }
}
