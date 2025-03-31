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
    public static final String CREATE_ROOM_COMMAND = "0";

    private final JanggiService janggiService;
    private final InputView inputView;
    private final OutputView outputView;

    private String roomId;

    public JanggiGameFlow(JanggiService janggiService, InputView inputView, OutputView outputView) {
        this.janggiService = janggiService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void selectGameRoom() {
        List<Room> allPlayingRoom = janggiService.findAllPlayingRoom();
        outputView.printPlayingRoom(allPlayingRoom);
        String rawRoomIdNumber = inputView.readRoomId(allPlayingRoom);
        if (rawRoomIdNumber.equals(CREATE_ROOM_COMMAND)) {
            createRoom();
            return;
        }
        int roomIdNumber = Integer.parseInt(rawRoomIdNumber);
        roomId = allPlayingRoom.get(roomIdNumber - 1).roomId();
    }

    private void createRoom() {
        String roomId = inputView.readRoomIdToCreate();
        janggiService.createJanggiGame(roomId); // TODO: validate duplicated id
        this.roomId = roomId;
    }

    public void play() {
        while (!janggiService.isGameEnd(roomId)) {
            processTurn();
        }
    }

    private void processTurn() {
        Janggi janggi = janggiService.loadJanggiGame(roomId);
        outputView.printJanggiUnits(janggi.getUnits());
        String rawPosition = inputView.readUnitPosition(janggi.getTurn());
        if (rawPosition.equals(SURRENDER_COMMAND)) {
            janggiService.surrender(roomId, janggi.getTurn().getOpposite());
            return;
        }
        Position current = parsePosition(rawPosition);
        handleMove(janggi, current);
    }

    private void handleMove(Janggi janggi, Position position) {
        Routes routes = janggiService.findAllRoute(roomId, position.getX(), position.getY());
        outputView.printAvailableRoute(position, routes);

        Position destination = parsePosition(inputView.readDestinationPosition(janggi.getTurn()));
        if (routes.hasRouteTo(position, destination)) {
            janggiService.moveTo(roomId, position, destination);
        }
    }

    public void endGame() {
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
