import board.Board;
import game.Game;
import game.GameConfig;
import game.GameMode;
import game.GameTimer;
import pieces.PieceColor;
import player.BotPlayer;
import ui.ConsoleGameUI;
import ui.SwingGameUI;

import javax.swing.*;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Точка входа в программу.
 * Запуск возможен в двух режимах:
 * 1) "Хочу играть!" — интерактивный режим (человек против бота) + аргумент цвета
 * 2) "Я наблюдатель" — режим наблюдения (бот против бота) в консоли
 */
public class Main {

    /**
     * Запуск программы.
     *
     * @param args аргументы командной строки (не используются)
     */
/**
 * Выполняет действие: main.
 * @param args параметр метода
 */
    public static void main(String[] args) {
        GameConfig config = GameConfig.load();

        System.out.println("Введите режим запуска:");
        System.out.println("1) Хочу играть! [white|black|белый|черный]");
        System.out.println("2) Я наблюдатель");
        System.out.print("> ");

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine().trim();

        if (line.startsWith("Хочу играть!")) {
            PieceColor humanColor = parseColorOrRandom(line);
            startInteractive(humanColor, config);
            return;
        }

        if (line.equals("Я наблюдатель")) {
            startObserver(config);
            return;
        }

        System.out.println("Неизвестная команда.");
    }

/**
 * Выполняет действие: startInteractive.
 * @param humanColor параметр метода
 * @param config параметр метода
 */
    private static void startInteractive(PieceColor humanColor, GameConfig config) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board();
            new SwingGameUI(board, humanColor, config.getMaxDurationSeconds());
        });
    }

/**
 * Выполняет действие: startObserver.
 * @param config параметр метода
 */
    private static void startObserver(GameConfig config) {
        SwingUtilities.invokeLater(() -> {
            Board board = new Board();
            new SwingGameUI(board, config.getMaxDurationSeconds(), (int) config.getObserverMoveDelayMillis());
        });
    }

/**
 * Выполняет действие: parseColorOrRandom.
 * @param line параметр метода
 * @return результат выполнения
 */
    private static PieceColor parseColorOrRandom(String line) {
        String tail = line.replaceFirst("^Хочу играть!", "").trim().toLowerCase(Locale.ROOT);
        if (tail.isEmpty()) {
            return new Random().nextBoolean() ? PieceColor.WHITE : PieceColor.BLACK;
        }

        if (tail.contains("white") || tail.contains("бел")) {
            return PieceColor.WHITE;
        }
        if (tail.contains("black") || tail.contains("чер")) {
            return PieceColor.BLACK;
        }

        return new Random().nextBoolean() ? PieceColor.WHITE : PieceColor.BLACK;
    }
}
