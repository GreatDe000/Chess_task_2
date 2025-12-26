package game;

/**
 * Таймер игры.
 */
public class GameTimer {

/**
 * Поле startTime.
 */
    private final long startTime;
/**
 * Поле maxDurationMillis.
 */
    private final long maxDurationMillis;

    public GameTimer(long maxDurationMillis) {
        this.startTime = System.currentTimeMillis();
        this.maxDurationMillis = maxDurationMillis;
    }

    /**
     * Проверяет, истекло ли игровое время.
     *
     * @return true, если время вышло
     */
/**
 * Проверяет условие timeover.
 * @return результат выполнения
 */
    public boolean isTimeOver() {
        return System.currentTimeMillis() - startTime >= maxDurationMillis;
    }
}
