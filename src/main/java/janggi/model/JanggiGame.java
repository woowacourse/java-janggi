package janggi.model;

import janggi.dao.BoardDao;
import janggi.dao.TurnDao;
import janggi.db.DBConnection;
import janggi.db.DBInitializer;
import janggi.model.piece.Piece;
import java.util.Set;

public class JanggiGame {
    private final Board board;
    private final Turn turn;

    private final BoardDao boardDao;
    private final TurnDao turnDao;
    private final DBInitializer dbInitializer;

    public JanggiGame() {
        DBConnection dbConnection = new DBConnection();
        boardDao = new BoardDao(dbConnection);
        turnDao = new TurnDao(dbConnection);
        dbInitializer = new DBInitializer(dbConnection);
        if (!dbInitializer.existDb()) {
            initializeDb();
        }
        board = boardDao.findBoard();
        turn = new Turn(turnDao.findCurrentTurn());
    }

    public JanggiGame(Board board, Turn turn, DBConnection dbConnection) {
        this.board = board;
        this.turn = turn;
        boardDao = new BoardDao(dbConnection);
        turnDao = new TurnDao(dbConnection);
        dbInitializer = new DBInitializer(dbConnection);
    }

    public boolean existCatchablePiece(Color ourColor, Position destination) {
        OccupiedPositions occupiedPositions = board.generateOccupiedPositions();
        return occupiedPositions.existPosition(destination) && !occupiedPositions.existSameColor(destination, ourColor);
    }

    public void playTurn(Position departure, Position destination) {
        Piece piece = board.findPieceByPositionAndColor(departure, turn.getCurrentTurn());
        OccupiedPositions occupied = board.generateOccupiedPositions();
        Set<Position> movablePositions = piece.calculateMovablePositions(departure, occupied);
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        board.move(departure, destination);
        turn.nextTurn();
        boardDao.updateOccupiedPositions(board);
        turnDao.updateCurrentTurn(turn.getCurrentTurn());
    }

    public Board getBoard() {
        return board;
    }

    public Color getCurrentTurn() {
        return turn.getCurrentTurn();
    }

    public double calculateScore(Color color) {
        return board.calculateScore(color);
    }

    private void initializeDb() {
        GameInitializer gameInitializer = new GameInitializer();
        dbInitializer.init();
        Board generatedBoard = gameInitializer.generateBoard();
        Turn generatedTurn = gameInitializer.generateTurn();
        boardDao.updateOccupiedPositions(generatedBoard);
        turnDao.updateCurrentTurn(generatedTurn.getCurrentTurn());
    }
}
