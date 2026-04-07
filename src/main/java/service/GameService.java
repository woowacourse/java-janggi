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
import model.move.Move;
import view.OutputView;

public class GameService {
    private static final int START_NEW_MODE = 1;
    private final JanggiGameDao janggiGameDao;

    public GameService(MysqlConnectionManager manager) {
        this.janggiGameDao = new JanggiGameDao(manager);
    }

    public JanggiGame startNewGame(HorseElephantStrategy choStrategy, HorseElephantStrategy hanStrategy) {
        Board board = new Board();

        new Army(choStrategy).deployTo(board, Country.CHO);
        new Army(hanStrategy).deployTo(board, Country.HAN);

        int id = janggiGameDao.createGame(Country.CHO);
        janggiGameDao.saveGame(id, Country.CHO, BoardConverter.convertToPieceDtos(board));
        return new JanggiGame(id, board, Country.CHO);
    }

    public JanggiGame continueGame(int mode) {
        GameStatus status = findInitialStatus(mode);
        List<PieceDto> pieces = janggiGameDao.loadPiecesByGameId(status.id());
        return new JanggiGame(status.id(), BoardConverter.convertToBoard(pieces), status.turn());
    }

    public GameStatus findInitialStatus(int mode) {
        if (mode == START_NEW_MODE) {
            return new GameStatus(0, Country.CHO);
        }
        GameStatus status = janggiGameDao.findLatestStatus();
        if (status == null) {
            OutputView.printError("저장된 게임이 없습니다. 새 게임을 시작합니다.");
            return new GameStatus(0, Country.CHO);
        }
        return status;
    }

    public void moveAndSave(JanggiGame game, Move move) {
        game.move(move);
        game.nextTurn();
        janggiGameDao.saveGame(game.id(), game.turn(), BoardConverter.convertToPieceDtos(game.board()));
    }
}
