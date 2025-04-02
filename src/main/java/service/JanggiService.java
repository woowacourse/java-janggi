package service;

import java.util.List;

import dao.GameDao;
import dao.PieceDao;
import dto.BoardDto;
import dto.FinalScoreDto;
import dto.GameDto;
import dto.TeamDto;
import model.Game;
import model.Position;
import model.Team;
import model.board.Board;
import model.board.TableSetting;
import model.piece.Piece;

public class JanggiService {

    private final GameDao gameDao = new GameDao();
    private final PieceDao pieceDao = new PieceDao();

    public List<GameDto> gameList() {
        List<Game> games = gameDao.selectAll();
        return games.stream()
            .map(GameDto::from)
            .toList();
    }

    public int newGame(String gameName) {
        return gameDao.insert(new Game(gameName, Team.CHO));
    }

    public void tableSettingForCurrentTurn(int gameId, TableSetting tableSetting) {
        Game game = gameDao.selectById(gameId);
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        board.addTeamPieces(game.getTurn(), tableSetting);
        pieceDao.insertAll(gameId, board.getPieces(game.getTurn()));
    }

    public BoardDto getBoard(int gameId) {
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        return BoardDto.from(board);
    }

    public TeamDto currentTurn(int gameId) {
        Game game = gameDao.selectById(gameId);
        return TeamDto.from(game.getTurn());
    }

    public void move(int gameId, Position source, Position destination) {
        Game game = gameDao.selectById(gameId);
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        Piece moved = board.get(source);
        Piece dead = board.find(destination);
        board.movePiece(source, destination, game.getTurn());
        if (dead != null) {
            pieceDao.delete(dead);
        }
        pieceDao.update(moved);
    }

    public boolean isPlaying(int gameId) {
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        return board.getWinnerIfGameOver() == null;
    }

    public void nextTurn(int gameId) {
        Game game = gameDao.selectById(gameId);
        game.nextTurn();
        gameDao.update(game);
    }

    public TeamDto getWinner(int gameId) {
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        return TeamDto.from(board.getWinnerIfGameOver());
    }

    public void abstain(int gameId) {
        Game game = gameDao.selectById(gameId);
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        Piece abstainKing = board.abstain(game.getTurn());
        pieceDao.delete(abstainKing);
    }

    public FinalScoreDto finalScore(int gameId) {
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        return FinalScoreDto.of(board.getPieceScore());
    }

    public void endGame(int gameId) {
        Game game = gameDao.selectById(gameId);
        Board board = new Board(pieceDao.selectAllByGameId(gameId));
        gameDao.delete(game);
        pieceDao.deleteAllInGame(gameId);
    }
}
