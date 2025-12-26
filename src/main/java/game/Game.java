package game;

import board.Board;
import board.Cell;
import move.Move;
import pieces.PieceColor;
import player.Player;
import ui.GameUI;

/**
 * Класс, управляющий логикой игры.
 */
public class Game {

    /** Доска игры. */
    private final Board board;

    /** Первый игрок. */
    private final Player player1;

    /** Второй игрок. */
    private final Player player2;

    /** Пользовательский интерфейс. */
    private final GameUI ui;

    /** Таймер игры. */
    private final GameTimer timer;

    /** Режим завершения по времени. */
    private final GameMode mode;

    /** Задержка между ходами (для наблюдателя). */
    private final long moveDelayMillis;

    public Game(Player player1, Player player2, GameUI ui, GameTimer timer, GameMode mode, long moveDelayMillis) {
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.ui = ui;
        this.timer = timer;
        this.mode = mode;
        this.moveDelayMillis = moveDelayMillis;
    }

    /**
     * Возвращает используемую доску.
     *
     * @return доска
     */
/**
 * Возвращает значение board.
 * @return результат выполнения
 */
    public Board getBoard() {
        return board;
    }

    /**
     * Запускает игровой цикл.
     * Игра завершается при мате, пате или окончании времени.
     */
/**
 * Выполняет действие: start.
 */
    public void start() {
        Player current = player1;

        while (!timer.isTimeOver()) {
            ui.render(board);

            // мат/пат перед ходом (на случай, если игроку уже нечем ходить)
            if (board.isCheckmate(current.getColor())) {
                Player winner = (current == player1) ? player2 : player1;
                ui.showMessage("Мат! Победил: " + winner.getName());
                return;
            }
            if (board.isStalemate(current.getColor())) {
                ui.showMessage("Пат. Ничья.");
                return;
            }

            Move move = current.makeMove(board);
            if (move == null) {
                // ход невозможен -> мат/пат уже обработали выше
                ui.showMessage(current.getName() + ": нет хода");
                return;
            }

            Cell from = board.getCell(move.getFromRow(), move.getFromCol());
            Cell to = board.getCell(move.getToRow(), move.getToCol());

            boolean success = board.move(from, to, current.getColor());
            if (!success) {
                ui.showMessage("Некорректный ход. Попробуйте ещё раз.");
                continue; // тот же игрок повторяет
            }

            // проверка мата сопернику
            Player opponent = (current == player1) ? player2 : player1;
            if (board.isCheckmate(opponent.getColor())) {
                ui.render(board);
                ui.showMessage("Мат! Победил: " + current.getName());
                return;
            }

            current = opponent;

            sleepQuietly(moveDelayMillis);
        }

        // время вышло
        if (mode == GameMode.SINGLE_PLAYER) {
            ui.showMessage("Время вышло. Вы проиграли.");
        } else {
            ui.showMessage("Время вышло. Ничья.");
        }
    }

/**
 * Выполняет действие: sleepQuietly.
 * @param millis параметр метода
 */
    private void sleepQuietly(long millis) {
        if (millis <= 0) return;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
