package controller;

import domain.*;
import domain.dao.JanggiCoordinateDao;
import domain.dao.JanggiDao;
import domain.dao.JanggiGameDao;
import domain.dao.JanggiPieceDao;
import domain.dto.GameRoomDTO;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

import java.util.List;

import static domain.JanggiBoard.COL_SIZE;
import static domain.JanggiBoard.ROW_SIZE;

public class JanggiController {
    public final static JanggiCoordinate GAME_STOP_COORDINATE = new JanggiCoordinate(-1, -1);

    private final static JanggiGameDao gameDao = new JanggiGameDao(JanggiDao.getConnection());
    private final static JanggiCoordinateDao coordinateDao = new JanggiCoordinateDao(JanggiDao.getConnection());
    private final static JanggiPieceDao pieceDao = new JanggiPieceDao(JanggiDao.getConnection());

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void startJanggiGame() {
        GameCommand command = getCreateGameCommand();
        JanggiGame game = null;
        int gameId = 0;

        if (command == GameCommand.CREATE_NEW_GAME_COMMAND) {
            String gameName = inputView.getCreateGameName();
            gameId = gameDao.createGame(gameName, Country.CHO);
            game = new JanggiGame(PieceInitializer.init(), Country.CHO);
        }
        if (command == GameCommand.LOAD_GAME_COMMAND) {
            List<GameRoomDTO> gameRooms = gameDao.findAllGames();
            String gameName = inputView.getGameName(gameRooms);
            gameId = gameDao.getGameIdByName(gameName);
            String currentTurn = gameDao.getCurrTurnById(gameId);
            game = new JanggiGame(coordinateDao.finaAllPieces(gameId), Country.fromName(currentTurn));
        }

        while (!game.isGameOver()) {
            try {
                outputView.printCurrBoard(game.getBoard());
                outputView.printCurrTurn(game.getCurrTurn());
                JanggiCoordinate from = inputView.readMovePiece();
                if (from.equals(GAME_STOP_COORDINATE)) {
                    saveGame(game.getBoard(), game.getCurrTurn(), gameId);
                    return;
                }

                JanggiCoordinate to = inputView.readMoveDestination();
                game.movePlayerPiece(from, to);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }

        outputView.printWinner(game.getWinner());
        outputView.printScore(Country.CHO, game.getCountryScore(Country.CHO));
        outputView.printScore(Country.HAN, game.getCountryScore(Country.HAN));
        gameDao.deleteGameRoom(gameId);
    }

    private GameCommand getCreateGameCommand() {
        gameDao.createGameTableIfNotExist();
        List<GameRoomDTO> gameRooms = gameDao.findAllGames();
        if (gameRooms.size() == 0) {
            return GameCommand.CREATE_NEW_GAME_COMMAND;
        }

        outputView.printGameNames(gameRooms);

        while (true) {
            try {
                return inputView.getCreateCommand();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void saveGame(JanggiBoard board, Country currTurn, int gameId) {
        pieceDao.createPieceTableIfNotExist();
        coordinateDao.createCoordinateTableIfNotExist();

        coordinateDao.deleteCoordinatesByGameId(gameId);
        pieceDao.deletePiecesByGameId(gameId);
        gameDao.updateTurn(gameId, currTurn.getName());

        for (int row = board.BOUNDARY_START; row <= ROW_SIZE; row++) {
            for (int col = board.BOUNDARY_START; col <= COL_SIZE; col++) {
                JanggiCoordinate coordinate = new JanggiCoordinate(row, col);
                if (board.isOccupied(coordinate)) {
                    Piece piece = board.findPieceByCoordinate(coordinate);
                    int pieceId = pieceDao.addPiece(gameId, piece);

                    coordinateDao.insertPieceToCoordinate(pieceId, coordinate, gameId);
                }
            }
        }
    }
}
