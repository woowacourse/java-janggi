package service;

import dao.BoardDao;
import dao.GameStateDao;
import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.state.SetUp;
import dto.MoveCommandDTO;
import java.util.Map;
import view.PieceName;

public class GameService {

    private final BoardDao boardDao;
    private final GameStateDao gameStateDao;
    private final JanggiGame janggiGame;

    public GameService(BoardDao boardDao, GameStateDao gameStateDao) {
        this.boardDao = boardDao;
        this.gameStateDao = gameStateDao;
        janggiGame = new JanggiGame(new SetUp());
    }

    public void startGame(boolean loadGame) {
        Board board = loadOrCreateBoard(loadGame);
        PieceColor color = getTurnColor(loadGame);
        janggiGame.startGame(board, color);
    }

    public PieceColor getTurnColor(boolean loadGame) {
        if (!loadGame) {
            gameStateDao.initializeTurn();
        }
        return PieceColor.valueOf(gameStateDao.getCurrentTurn());
    }

    private Board loadOrCreateBoard(boolean loadGame) {
        Map<Position, Piece> boardData = boardDao.loadBoard();

        if (boardData.isEmpty() || !loadGame) {
            boardData = new BoardFactory().createBoard().getBoard();
            boardDao.deleteBoard();
            boardDao.saveBoard(boardData);
            gameStateDao.initializeTurn();
        }

        return new Board(boardData);
    }

    public void playTurn(MoveCommandDTO commands) {
        Position source = new Position(Row.from(commands.sourceRow()), Column.from(commands.sourceColumn()));
        Position destination = new Position(Row.from(commands.destinationRow()),
                Column.from(commands.destinationColumn()));

        PieceType pieceType = PieceName.getPieceTypeFromName(commands.pieceName());

        janggiGame.move(pieceType, source, destination);
        boardDao.updatePosition(source, destination);
        gameStateDao.switchTurn();
    }

    public PieceColor getTurnColor() {
        return janggiGame.getTurnColor();
    }

    public boolean isGameFinished() {
        return janggiGame.isFinished();
    }

    public PieceColor getWinner() {
        return janggiGame.getWinner();
    }

    public double getRedTeamScore() {
        return janggiGame.getRedTeamScore();
    }

    public double getBlueTeamScore() {
        return janggiGame.getBlueTeamScore();
    }

    public Board getBoard() {
        return janggiGame.getBoard();
    }
}
