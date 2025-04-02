package controller;

import domain.*;
import domain.dao.JanggiCoordinateDao;
import domain.dao.JanggiGameDao;
import domain.dao.JanggiPieceDao;
import domain.dto.GameFindDto;
import domain.dto.GameIdDto;
import domain.dto.GameRoomDto;
import view.GameCommand;
import view.InputView;
import view.OutputView;
import view.PageCommand;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameDao gameDao;
    private final JanggiCoordinateDao coordinateDao;
    private final JanggiPieceDao pieceDao;

    public JanggiController(InputView inputView,
                            OutputView outputView,
                            JanggiGameDao gameDao,
                            JanggiCoordinateDao coordinateDao,
                            JanggiPieceDao pieceDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameDao = gameDao;
        this.coordinateDao = coordinateDao;
        this.pieceDao = pieceDao;
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

    private GameCommand getCreateGameCommand() {
        if (gameDao.countGameRooms() == 0) {
            return GameCommand.CREATE_NEW_GAME_COMMAND;
        }
        while (true) {
            try {
                return inputView.getCreateCommand();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
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
                Page page = getGameRoomPage();
                List<GameRoomDto> gameRooms = page.getCurrPageGames(gameDao);
                String gameName = inputView.getGameName(gameRooms);

                GameFindDto findDto = gameDao.getGameByName(gameName);
                int gameId = findDto.gameId();
                Country currentTurn = findDto.currTurn();

                return new GameIdDto(
                        gameId,
                        new JanggiGame(
                                coordinateDao.findAllPieces(gameId),
                                currentTurn));
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private Page getGameRoomPage() {
        Page currPage = new Page(Page.START_PAGE);
        int pageCount = gameDao.countGameRooms() / Page.PAGE_INTERVAL + 1;

        while (true) {
            List<GameRoomDto> gameRoomDtos = currPage.getCurrPageGames(gameDao);
            outputView.printGameNames(gameRoomDtos);
            PageCommand pageCommand = getPageCommand();

            Page nextPage = currPage.movePage(pageCommand);
            if (pageCommand == PageCommand.CURR_PAGE) {
                break;
            }
            if (nextPage.getCurrPage() < pageCount) {
                currPage = nextPage;
            }
        }
        return currPage;
    }

    private PageCommand getPageCommand() {
        while (true) {
            try {
                return inputView.getPageMoveCommand();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void saveGame(JanggiBoard board, Country currTurn, int gameId) {
        coordinateDao.deleteCoordinatesByGameId(gameId);
        pieceDao.deletePiecesByGameId(gameId);
        gameDao.updateTurn(gameId, currTurn.getName());

        List<Integer> piecesId = pieceDao.addPiecesBatch(gameId, board.getPieces());
        List<JanggiCoordinate> occupiedCoordinate = board.getOccupiedCoordinates();

        coordinateDao.addPieceToCoordinateBatch(piecesId, occupiedCoordinate, gameId);
    }
}
