package janggi;

import janggi.board.Board;
import janggi.board.TableOption;
import janggi.piece.Piece;
import janggi.piece.PieceGenerator;
import janggi.position.Position;
import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;

import java.util.List;
import java.util.Map;

public class JanggiGame {
    public static void main(String[] args) {
        Input input = new Input();
        Output output = new Output();
        List<Piece> initialPieces = generateInitialPieces(input);

        Team turn = Team.CHO;
        Board board = new Board(initialPieces);
        output.printBoard(board.getPositionedPieces());
        while (true) {
            try {
                Map.Entry<Position, Position> moveableInfo = input.readMoveablePiece();
                board.attack(turn, moveableInfo.getKey(), moveableInfo.getValue());
                output.printBoard(board.getPositionedPieces());
                turn = changeTurn(turn);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static Team changeTurn(Team turn) {
        if (turn == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public static List<Piece> generateInitialPieces(Input input) {
        String choTableOptionInput = input.readTableOption(Team.CHO);
        TableOption choTable = TableOption.from(choTableOptionInput);
        String hanTableOptionInput = input.readTableOption(Team.HAN);
        TableOption hanTable = TableOption.from(hanTableOptionInput);
        return new PieceGenerator().generateInitialPieces(hanTable, choTable);
    }

}
