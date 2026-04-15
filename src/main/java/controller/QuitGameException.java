package controller;

public class QuitGameException extends RuntimeException {
    public QuitGameException() {
        super("[INFO] 게임을 중단하고 메뉴로 돌아갑니다.");
    }
}
