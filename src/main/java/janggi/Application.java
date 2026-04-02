package janggi;

import janggi.dao.GameRoom;

public class Application {
    public static void main(String[] args) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.initTable();

        JanggiService janggiService = new JanggiService(gameRoom);

        Runner runner = new Runner(janggiService);
        runner.run();
    }
}
