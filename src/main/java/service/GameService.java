package service;

import dao.BoardDao;
import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import dto.MoveCommandDTO;
import java.util.Map;
import view.PieceName;

public class GameService {

    private final BoardDao boardDao;
    private final JanggiGame janggiGame;

    public GameService(BoardDao boardDao, JanggiGame janggiGame) {
        this.boardDao = boardDao;
        this.janggiGame = janggiGame;
    }

    public void startGame() {
        Board board = new BoardFactory().createBoard();
        boardDao.saveBoard(board.getBoard());
    }

    public void playTurn(MoveCommandDTO commands) {
        Position source = new Position(Row.from(commands.sourceRow()), Column.from(commands.sourceColumn()));
        Position destination = new Position(Row.from(commands.destinationRow()),
                Column.from(commands.destinationColumn()));

        PieceType pieceType = PieceName.getPieceTypeFromName(commands.pieceName());

        janggiGame.move(pieceType, source, destination);
        boardDao.updatePosition(source, destination);
    }

    public Map<Position, Piece> loadBoard() {
        return boardDao.loadBoard();
    }

    public void updatePosition(Position position, Position destination, Piece piece) {
        boardDao.updatePosition(position, destination);
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
}
