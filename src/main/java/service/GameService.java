package service;

import dao.JanggiGameDao;
import dao.converter.BoardConverter;
import database.MysqlConnectionManager;
import dto.GameStatus;
import dto.PieceDto;
import java.util.List;
import model.JanggiGame;
import model.board.Army;
import model.board.Board;
import model.board.Country;
import model.board.HorseElephantStrategy;
import model.board.Status;
import model.move.Move;

public class GameService {
    private static final int NOT_EXISTS = 0;
    private final JanggiGameDao janggiGameDao;

    public GameService(MysqlConnectionManager manager) {
        this.janggiGameDao = new JanggiGameDao(manager);
    }

    public boolean isDuplicated(String roomName) {
        int count = janggiGameDao.countByRoomName(roomName);
        return count > NOT_EXISTS;
    }

    public List<String> getRoomNameList() {
        return janggiGameDao.getRoomNameList();
    }

    public JanggiGame startNewGame(HorseElephantStrategy choStrategy, HorseElephantStrategy hanStrategy,
                                   String roomName) {
        Board board = new Board();

        new Army(choStrategy).deployTo(board, Country.CHO);
        new Army(hanStrategy).deployTo(board, Country.HAN);

        int id = janggiGameDao.createGame(Country.CHO, roomName, Status.PLAYING);
        String snapshot = BoardConverter.toSnapshot(board);
        janggiGameDao.saveGame(id, Country.CHO, BoardConverter.convertToPieceDtos(board), snapshot);
        return new JanggiGame(id, board, Country.CHO, Status.PLAYING);
    }

    public JanggiGame continueGame(String roomName) {
        GameStatus gameStatus = findInitialStatus(roomName);
        List<PieceDto> pieces = janggiGameDao.loadPiecesByGameId(gameStatus.id());
        return new JanggiGame(gameStatus.id(), BoardConverter.convertToBoard(pieces), gameStatus.turn(),
                gameStatus.status());
    }

    public void moveAndSave(JanggiGame game, Move move) {
        String nextSnapshot = BoardConverter.toSnapshot(game.board());
        int repetitionCount = janggiGameDao.loadSnapshotCount(game.id(), nextSnapshot);
        game.move(move, repetitionCount);
        game.nextTurn();
        janggiGameDao.saveGame(game.id(), game.turn(), BoardConverter.convertToPieceDtos(game.board()), nextSnapshot);
    }

    public void finishGame(JanggiGame game) {
        janggiGameDao.saveStatus(game.id(), game.status());
    }

    private GameStatus findInitialStatus(String roomName) {
        return janggiGameDao.findStatusByRoomName(roomName);
    }
}
