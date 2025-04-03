package gameflow;

import domain.Janggi;
import domain.position.Position;
import domain.position.Routes;
import domain.unit.Team;
import entity.Room;
import java.util.Arrays;
import java.util.List;
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
        outputView.printPlayingRoom(allPlayingRoom);
        String rawRoomIdNumber = inputView.readRoomId(allPlayingRoom);
        if (rawRoomIdNumber.equals(CREATE_ROOM_COMMAND)) {
            return createRoom();
        }
        int roomIdNumber = Integer.parseInt(rawRoomIdNumber);
        return allPlayingRoom.get(roomIdNumber - 1).roomId();
    }

    private String createRoom() {
        String roomId = inputView.readRoomIdToCreate();
        janggiService.createJanggiGame(roomId); // TODO: validate duplicated id
        return roomId;
    }

    public void play(String roomId) {
        while (!janggiService.isGameEnd(roomId)) {
            processTurn(roomId);
        }
    }

    private void processTurn(String roomId) {
        Janggi janggi = janggiService.loadJanggiGame(roomId);
        outputView.printJanggiUnits(janggi.getUnits());
        String rawPosition = inputView.readUnitPosition(janggi.getTurn());
        if (rawPosition.equals(SURRENDER_COMMAND)) {
            janggiService.surrender(roomId, janggi.getTurn().getOpposite());
            return;
        }
        Position current = parsePosition(rawPosition);
        handleMove(roomId, janggi, current);
    }

    private void handleMove(String roomId, Janggi janggi, Position position) {
        Routes routes = janggiService.findAllRoute(roomId, position.getX(), position.getY());
        outputView.printAvailableRoute(position, routes);

        Position destination = parsePosition(inputView.readDestinationPosition(janggi.getTurn()));
        if (routes.hasRouteTo(position, destination)) {
            janggiService.moveTo(roomId, position, destination);
        }
    }

    public void endGame(String roomId) {
        Team winner = janggiService.getWinner(roomId);
        double han = janggiService.calculateScoreOf(roomId, Team.HAN);
        double cho = janggiService.calculateScoreOf(roomId, Team.CHO);
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
