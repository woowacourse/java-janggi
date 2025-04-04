package gameflow;

import domain.Janggi;
import domain.position.Position;
import domain.position.Routes;
import domain.unit.DefaultUnitPosition;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.Units;
import entity.Room;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiGameFlow {

    private static final String SURRENDER_COMMAND = "GG";
    private static final String CREATE_ROOM_COMMAND = "0";

    private final JanggiService janggiService;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGameFlow(JanggiService janggiService, InputView inputView, OutputView outputView) {
        this.janggiService = janggiService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public String selectGameRoom() {
        List<Room> allPlayingRoom = janggiService.findAllPlayingRoom();
        String rawRoomIdNumber = inputView.readRoomId(allPlayingRoom);
        if (rawRoomIdNumber.equals(CREATE_ROOM_COMMAND)) {
            return createRoom();
        }
        int roomIdNumber = Integer.parseInt(rawRoomIdNumber);
        Room room = allPlayingRoom.get(roomIdNumber - 1);
        return room.roomId();
    }

    private String createRoom() {
        String roomId = inputView.readRoomIdToCreate();
        while (janggiService.existsRoom(roomId)) {
            roomId = inputView.reReadRoomIdToCreate();
        }
        Janggi janggi = initGame();
        janggiService.createJanggiRoom(roomId, janggi);
        return roomId;
    }

    private Janggi initGame() {
        Map<Position, Unit> hanUnits = DefaultUnitPosition.createTotalUnits(Team.HAN);
        Map<Position, Unit> choUnits = DefaultUnitPosition.createTotalUnits(Team.CHO);
        Units totalUnits = Units.of(hanUnits, choUnits);
        return Janggi.of(totalUnits);
    }

    public void play(String roomId) {
        Janggi janggi = janggiService.loadJanggiGame(roomId);
        while (isPlaying(janggi)) {
            try {
                janggi = processTurn(roomId);
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private boolean isPlaying(Janggi janggi) {
        return !janggi.isEnd();
    }

    private Janggi processTurn(String roomId) {
        Janggi janggi = janggiService.loadJanggiGame(roomId);
        outputView.printJanggiUnits(janggi.getUnits());
        String rawPosition = inputView.readUnitPosition(janggi.getTurn());
        if (rawPosition.equals(SURRENDER_COMMAND)) {
            janggi.surrender();
            janggiService.endGame(roomId, janggi.getTurn().getOpposite());
            return janggi;
        }
        Position current = parsePosition(rawPosition);
        handleMove(roomId, janggi, current);
        return janggi;
    }

    private void handleMove(String roomId, Janggi janggi, Position position) {
        Routes routes = janggi.findMovableRoutesFrom(position);
        outputView.printAvailableRoute(position, routes);

        Position destination = parsePosition(inputView.readDestinationPosition(janggi.getTurn()));
        janggi.doTurn(position, destination);
        janggiService.movePiece(roomId, position, destination);
    }

    public void endGame(String roomId) {
        Janggi janggi = janggiService.loadJanggiGame(roomId);
        Team winner = janggi.getWinner();
        janggiService.endGame(roomId, winner);
        double han = janggi.getScoreOf(Team.HAN);
        double cho = janggi.getScoreOf(Team.CHO);
        outputView.printWinner(winner, cho, han);
    }

    private List<Integer> parseIntegers(String rawPosition) {
        return Arrays.stream(rawPosition.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    private Position parsePosition(String rawPosition) {
        List<Integer> positionValue = parseIntegers(rawPosition);
        return Position.of(positionValue.get(0), positionValue.get(1));
    }
}
