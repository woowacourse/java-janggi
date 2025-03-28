package janggi.board.strategy;

import janggi.board.Position;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class NormalPlaceStrategy implements PlaceStrategy {

    @Override
    public Map<Position, Piece> initialize() {
        Map<Position, Piece> board = new HashMap<>();
        initializeRedTeam(board);
        initializeGreenTeam(board);
        return board;
    }

    private void initializeRedTeam(Map<Position, Piece> board) {
        Team team = Team.RED;

        placePieces(board, team, Soldier::new, 6, 0, 2, 4, 6, 8);
        placePieces(board, team, Cannon::new, 7, 1, 7);
        board.put(new Position(4, 8), new General(team));
        placePieces(board, team, Chariot::new, 9, 0, 8);
        placePieces(board, team, Elephant::new, 9, 1, 6);
        placePieces(board, team, Horse::new, 9, 2, 7);
        placePieces(board, team, Guard::new, 9, 3, 5);
    }

    private void initializeGreenTeam(Map<Position, Piece> board) {
        Team team = Team.GREEN;

        placePieces(board, team, Soldier::new, 3, 0, 2, 4, 6, 8);
        placePieces(board, team, Cannon::new, 2, 1, 7);
        board.put(new Position(4, 1), new General(team));
        placePieces(board, team, Chariot::new, 0, 0, 8);
        placePieces(board, team, Elephant::new, 0, 1, 6);
        placePieces(board, team, Horse::new, 0, 2, 7);
        placePieces(board, team, Guard::new, 0, 3, 5);
    }

    private void placePieces(Map<Position, Piece> board,
                             Team team,
                             Function<Team, Piece> pieceFactory,
                             int row,
                             int... columns) {
        for (int column : columns) {
            board.put(new Position(column, row), pieceFactory.apply(team));
        }
    }
}
