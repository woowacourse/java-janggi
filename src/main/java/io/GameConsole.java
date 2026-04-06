package io;

import domain.board.Board;
import domain.board.Pieces;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Team;
import domain.room.GameRoom;
import domain.setup.Arrangement;
import domain.setup.Arrangements;
import domain.state.BikjangState;
import domain.state.EndGameState;
import domain.state.GameResult;
import domain.state.GameState;
import domain.state.PlayingState;
import domain.state.ReadyState;
import infrastructure.repository.GameDto;
import infrastructure.repository.GameRepository;
import infrastructure.repository.GameRoomRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class GameConsole {
    private final OutputView outputView;
    private final InputView inputView;
    private final GameRoomRepository gameRoomRepository;
    private final GameRepository gameRepository;

    public GameConsole(GameRoomRepository gameRoomRepository, GameRepository gameRepository) {
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.gameRoomRepository = gameRoomRepository;
        this.gameRepository = gameRepository;
    }

    public void run() {
        GameRoom room = selectOrCreateRoom();
        outputView.printRoomEntered(room);
        Optional<GameDto> snapshot = gameRepository.findLatestByRoom(room.id());
        long currentGameId = resolveGameId(room, snapshot);
        JanggiGame game = resolveGame(snapshot);
        playGame(room, currentGameId, game);
    }

    private long resolveGameId(GameRoom room, Optional<GameDto> snapshot) {
        if (snapshot.isEmpty()) {
            return gameRepository.save(room.id());
        }
        GameDto dto = snapshot.get();
        return dto.gameId();
    }

    private JanggiGame resolveGame(Optional<GameDto> snapshot) {
        if (snapshot.isEmpty()) {
            return new JanggiGame();
        }
        GameDto dto = snapshot.get();
        return restoreGame(dto);
    }

    private void playGame(GameRoom room, long currentGameId, JanggiGame game) {
        game.displayRequestCommand(outputView);
        while (!game.isFinished()) {
            processRound(currentGameId, game);
        }
        endGame(room, currentGameId, game);
    }

    private void processRound(long currentGameId, JanggiGame game) {
        retryUntilSuccess(() -> game.processCommand(inputView.readCommand()));
        saveGame(currentGameId, game);
        game.displayRequestCommand(outputView);
    }

    private void endGame(GameRoom room, long currentGameId, JanggiGame game) {
        saveGame(currentGameId, game);
        gameRoomRepository.finish(room.id());
    }

    private JanggiGame restoreGame(GameDto snapshot) {
        Board board = restoreBoard(snapshot);
        Turn turn = new Turn(snapshot.currentTeam());
        GameState state = restoreState(snapshot);
        return new JanggiGame(board, turn, state);
    }

    private Board restoreBoard(GameDto snapshot) {
        if (snapshot.pieces().isEmpty()) {
            return null;
        }
        return new Board(new Pieces(snapshot.pieces()));
    }

    private GameState restoreState(GameDto snapshot) {
        String stateName = snapshot.stateName();
        if ("READY_HAN".equals(stateName)) {
            return new ReadyState(new Arrangements());
        }
        if ("READY_CHO".equals(stateName)) {
            return restoreReadyCho(snapshot);
        }
        if ("BIKJANG".equals(stateName)) {
            return new BikjangState();
        }
        if ("END".equals(stateName)) {
            return new EndGameState(GameResult.DRAW);
        }
        return new PlayingState();
    }

    private GameState restoreReadyCho(GameDto snapshot) {
        Arrangements arrangements = new Arrangements().assignArrangement(Team.HAN, snapshot.hanArrangement());
        return new ReadyState(arrangements);
    }

    private void saveGame(long currentGameId, JanggiGame game) {
        GameState currentGameState = game.getGameState();
        gameRepository.updateState(currentGameId, currentGameState.stateName(), game.getCurrentTeam());
        saveArrangement(currentGameId, currentGameState, Team.HAN);
        saveArrangement(currentGameId, currentGameState, Team.CHO);
        saveBoard(currentGameId, game);
    }

    private void saveArrangement(long currentGameId, GameState currentGameState, Team team) {
        Arrangement arrangement = currentGameState.getArrangementOf(team);
        if (arrangement == null) {
            return;
        }
        gameRepository.updateArrangement(currentGameId, team, arrangement);
    }

    private void saveBoard(long currentGameId, JanggiGame game) {
        Board board = game.getBoard();
        if (board == null) {
            return;
        }
        gameRepository.updateBoard(currentGameId, board.getAllPieces());
    }

    private GameRoom selectOrCreateRoom() {
        List<GameRoom> rooms = gameRoomRepository.findAllPlaying();
        outputView.printRoomMenu(rooms);
        int choice = retryUntilSuccess(inputView::readRoomMenuChoice);
        return handleRoomChoice(choice, rooms);
    }

    private GameRoom handleRoomChoice(int choice, List<GameRoom> rooms) {
        if (choice == 1) {
            return createRoom();
        }
        if (rooms.isEmpty()) {
            return retryAfterNoRooms();
        }
        return enterRoom(rooms);
    }

    private GameRoom retryAfterNoRooms() {
        outputView.printErrorMessage("[ERROR] 입장할 수 있는 게임방이 없습니다. 새 게임방을 만들어주세요.");
        return selectOrCreateRoom();
    }

    private GameRoom createRoom() {
        outputView.printRoomNamePrompt();
        String name = retryUntilSuccess(inputView::readRoomName);
        return gameRoomRepository.save(name);
    }

    private GameRoom enterRoom(List<GameRoom> rooms) {
        outputView.printRoomNumberPrompt();
        int number = retryUntilSuccess(() -> inputView.readRoomNumber(rooms.size()));
        return rooms.get(number - 1);
    }

    private <T> T retryUntilSuccess(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }

    }

    private void retryUntilSuccess(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
