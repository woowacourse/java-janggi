// TODO 2025. 4. 4. 00:23: 도커 사용법, README에 작성하기

import controller.JanggiController;
import database.MysqlConnectionManager;

public class Application {

    public static void main(String[] args) {

        JanggiController controller = new JanggiController(new MysqlConnectionManager());
        controller.run();
    }
}
