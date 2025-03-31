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
import domain.state.SetUp;
import dto.MoveCommandDTO;
import java.util.Map;
import view.PieceName;

public class GameService {

    private final BoardDao boardDao;
    private final JanggiGame janggiGame;

    public GameService(BoardDao boardDao) {
        this.boardDao = boardDao;
        janggiGame = new JanggiGame(new SetUp());
    }

    private Board loadOrCreateBoard() {
        // 1. DB에서 저장된 보드를 불러옴
        Map<Position, Piece> boardData = boardDao.loadBoard();

        if (boardData.isEmpty()) {
            boardData = new BoardFactory().createBoard().getBoard();
            boardDao.saveBoard(boardData);
        }

        return new Board(boardData);
    }

    public void startGame() {
        Board board = loadOrCreateBoard();
        janggiGame.startGame(board);
    }

    public void playTurn(MoveCommandDTO commands) {
        Position source = new Position(Row.from(commands.sourceRow()), Column.from(commands.sourceColumn()));
        Position destination = new Position(Row.from(commands.destinationRow()),
                Column.from(commands.destinationColumn()));

        PieceType pieceType = PieceName.getPieceTypeFromName(commands.pieceName());

        janggiGame.move(pieceType, source, destination);
        boardDao.updatePosition(source, destination);
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
