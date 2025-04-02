package controller;

import domain.*;
import domain.dao.JanggiCoordinateDao;
import domain.dao.JanggiDBConnect;
import domain.dao.JanggiGameDao;
import domain.dao.JanggiPieceDao;
import domain.dto.GameIdDto;
import domain.dto.GameRoomDto;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

import java.util.List;

import static domain.JanggiBoard.COL_SIZE;
import static domain.JanggiBoard.ROW_SIZE;

public class JanggiController {

    private final static JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());
    private final static JanggiCoordinateDao coordinateDao = new JanggiCoordinateDao(JanggiDBConnect.getConnection());
    private final static JanggiPieceDao pieceDao = new JanggiPieceDao(JanggiDBConnect.getConnection());

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void startJanggiGame() {
        GameCommand command = getCreateGameCommand();

        GameIdDto gameIdDto = createGame(command);
        JanggiGame game = gameIdDto.game();
        int gameId = gameIdDto.gameId();

        while (!game.isGameOver()) {
            try {
                outputView.printCurrBoard(game.getBoard());
                outputView.printCurrTurn(game.getCurrTurn());
                JanggiCoordinate from = inputView.readMovePiece();
                if (from.equals(InputView.GAME_STOP_COORDINATE)) {
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

    private GameIdDto createGame(GameCommand command) {
        if (command == GameCommand.CREATE_NEW_GAME_COMMAND) {
            return createNewGame();
        }
        return loadGame();
    }

    private GameIdDto createNewGame() {
        while (true) {
            try {
                String gameName = inputView.getCreateGameName();
                return new GameIdDto(
                        gameDao.createGame(gameName, Country.CHO),
                        new JanggiGame(PieceInitializer.init(), Country.CHO)
                );
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private GameIdDto loadGame() {
        while (true) {
            try {
                List<GameRoomDto> gameRooms = gameDao.findAllGames();
                String gameName = inputView.getGameName(gameRooms);
                int gameId = gameDao.getGameIdByName(gameName);
                String currentTurn = gameDao.getCurrTurnById(gameId);

                return new GameIdDto(
                        gameDao.getGameIdByName(gameName),
                        new JanggiGame(coordinateDao.findAllPieces(gameId), Country.fromName(currentTurn))
                );
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private GameCommand getCreateGameCommand() {
        List<GameRoomDto> gameRooms = gameDao.findAllGames();
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
