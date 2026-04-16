package exception;

public class ObstacleInPathException extends IllegalArgumentException {
    public ObstacleInPathException() {
        super(ErrorMessage.OBSTACLE_IN_PATH.getMessage());
    }
}
