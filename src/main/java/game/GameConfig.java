package game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Конфигурация игры, загружаемая из файла game.properties.
 */
public class GameConfig {

    /** Значения по умолчанию, если файл конфигурации не найден. */
    private static final int DEFAULT_MAX_DURATION_SECONDS = 300;

/**
 * Поле DEFAULT_OBSERVER_DELAY_MILLIS.
 */
    private static final long DEFAULT_OBSERVER_DELAY_MILLIS = 500;

    /** Максимальная длительность партии в секундах. */
    private final int maxDurationSeconds;

    /** Задержка между ходами в режиме наблюдателя (мс). */
    private final long observerMoveDelayMillis;

    private GameConfig(int maxDurationSeconds, long observerMoveDelayMillis) {
        this.maxDurationSeconds = maxDurationSeconds;
        this.observerMoveDelayMillis = observerMoveDelayMillis;
    }

    /**
     * Загружает конфигурацию из classpath: /game.properties.
     *
     * @return конфигурация
     */
/**
 * Выполняет действие: load.
 * @return результат выполнения
 */
    public static GameConfig load() {
        Properties props = new Properties();
        try (InputStream in = GameConfig.class.getClassLoader().getResourceAsStream("game.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException ignored) {
        }

        int maxSeconds = parseInt(props.getProperty("maxDurationSeconds"), DEFAULT_MAX_DURATION_SECONDS);
        long delay = parseLong(props.getProperty("observerMoveDelayMillis"), DEFAULT_OBSERVER_DELAY_MILLIS);

        if (maxSeconds <= 0) maxSeconds = DEFAULT_MAX_DURATION_SECONDS;
        if (delay < 0) delay = DEFAULT_OBSERVER_DELAY_MILLIS;

        return new GameConfig(maxSeconds, delay);
    }

    /**
     * Возвращает максимальную длительность партии.
     *
     * @return секунды
     */
/**
 * Возвращает значение maxdurationseconds.
 * @return результат выполнения
 */
    public int getMaxDurationSeconds() {
        return maxDurationSeconds;
    }

    /**
     * Возвращает задержку между ходами в режиме наблюдателя.
     *
     * @return миллисекунды
     */
/**
 * Возвращает значение observermovedelaymillis.
 * @return результат выполнения
 */
    public long getObserverMoveDelayMillis() {
        return observerMoveDelayMillis;
    }

/**
 * Выполняет действие: parseInt.
 * @param s параметр метода
 * @param def параметр метода
 * @return результат выполнения
 */
    private static int parseInt(String s, int def) {
        if (s == null) return def;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

/**
 * Выполняет действие: parseLong.
 * @param s параметр метода
 * @param def параметр метода
 * @return результат выполнения
 */
    private static long parseLong(String s, long def) {
        if (s == null) return def;
        try {
            return Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
