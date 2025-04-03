// TODO 2025. 4. 1. 13:55: dao 테스트 & 테스트 DB 생성
// TODO 2025. 4. 1. 13:56: e.printStackTrace()
// TODO 2025. 4. 3. 18:23: BoardDao에서 PieceDao를 필드를 가지지 않는 방향으로
// TODO 2025. 4. 3. 18:24: PieceDao에서 Connection을 가져오는 로직이 아닌 다른 곳에서
// TODO 2025. 4. 4. 00:23: 도커 사용법, README에 작성하기

import controller.JanggiController;

public class Application {

    public static void main(String[] args) {

        JanggiController controller = new JanggiController();
        controller.run();
    }
}
