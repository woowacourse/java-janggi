package janggi;

import static janggi.board.position.Column.getColumn;
import static janggi.board.position.Row.getRow;

import janggi.board.Board;
import janggi.board.GameOverException;
import janggi.board.position.Position;
import janggi.piece.Canon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();
    private static final InputParser parser = new InputParser();

    public static void main(String[] args) {
        Board board = makeBoard();
        outputView.printGameStartMessage();
        Team team = Team.GREEN;
        while (true) {
            outputView.printBoard(board);
            if (!playTurn(board, team)) {
                break;
            }
            team = team.convertTeam();
        }
    }

    private static boolean playTurn(Board board, Team team) {
        try {
            String startAndGoal = inputView.readStartAndGoalPosition(team);
            Position startPosition = parser.splitStartPosition(startAndGoal);
            Position goalPosition = parser.splitGoalPosition(startAndGoal);
            board.movePiece(startPosition, goalPosition, team);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            return playTurn(board, team);
        } catch (GameOverException e) {
            outputView.printBoard(board);
            outputView.printGameOver(team);
            return false;
        }
        return true;
    }

    private static Board makeBoard() {
        Map<Position, Piece> board = new HashMap<>();
        initializeRedTeam(board);
        initializeGreenTeam(board);
        return new Board(board);
    }

    private static void initializeRedTeam(Map<Position, Piece> board) {
        Team team = Team.RED;
        board.put(new Position(getColumn(0), getRow(6)), new Soldier(team));
        board.put(new Position(getColumn(2), getRow(6)), new Soldier(team));
        board.put(new Position(getColumn(4), getRow(6)), new Soldier(team));
        board.put(new Position(getColumn(6), getRow(6)), new Soldier(team));
        board.put(new Position(getColumn(8), getRow(6)), new Soldier(team));

        board.put(new Position(getColumn(1), getRow(7)), new Canon(team));
        board.put(new Position(getColumn(7), getRow(7)), new Canon(team));

        board.put(new Position(getColumn(4), getRow(8)), new General(team));

        board.put(new Position(getColumn(0), getRow(9)), new Chariot(team));
        board.put(new Position(getColumn(8), getRow(9)), new Chariot(team));

        board.put(new Position(getColumn(1), getRow(9)), new Elephant(team));
        board.put(new Position(getColumn(6), getRow(9)), new Elephant(team));

        board.put(new Position(getColumn(2), getRow(9)), new Horse(team));
        board.put(new Position(getColumn(7), getRow(9)), new Horse(team));

        board.put(new Position(getColumn(3), getRow(9)), new Guard(team));
        board.put(new Position(getColumn(5), getRow(9)), new Guard(team));
    }

    private static void initializeGreenTeam(Map<Position, Piece> board) {
        Team team = Team.GREEN;
        board.put(new Position(getColumn(0), getRow(3)), new Soldier(team));
        board.put(new Position(getColumn(2), getRow(3)), new Soldier(team));
        board.put(new Position(getColumn(4), getRow(3)), new Soldier(team));
        board.put(new Position(getColumn(6), getRow(3)), new Soldier(team));
        board.put(new Position(getColumn(8), getRow(3)), new Soldier(team));

        board.put(new Position(getColumn(1), getRow(2)), new Canon(team));
        board.put(new Position(getColumn(7), getRow(2)), new Canon(team));

        board.put(new Position(getColumn(4), getRow(1)), new General(team));

        board.put(new Position(getColumn(0), getRow(0)), new Chariot(team));
        board.put(new Position(getColumn(8), getRow(0)), new Chariot(team));

        board.put(new Position(getColumn(1), getRow(0)), new Elephant(team));
        board.put(new Position(getColumn(6), getRow(0)), new Elephant(team));

        board.put(new Position(getColumn(2), getRow(0)), new Horse(team));
        board.put(new Position(getColumn(7), getRow(0)), new Horse(team));

        board.put(new Position(getColumn(3), getRow(0)), new Guard(team));
        board.put(new Position(getColumn(5), getRow(0)), new Guard(team));
    }
}
