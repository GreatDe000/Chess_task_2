package ui;

import board.Board;
import board.Cell;
import pieces.Piece;
import pieces.PieceColor;
import player.BotPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Графический интерфейс шахмат с интерактивным мышиным управлением.
 * Поддерживает игру человек против бота.
 */
public class SwingGameUI extends JFrame {

    /** Шахматная доска. */
    private final Board board;

    /** Кнопки клеток 8x8. Индексация: [y][x]. */
    private final JButton[][] buttons = new JButton[Board.SIZE][Board.SIZE];

    /** Выбранная пользователем клетка (откуда ходим). */
    private Cell selectedCell;

    /** Текущий ход (кто должен ходить). */
    private PieceColor currentTurn;

    /** Цвет человека. В режиме наблюдателя отсутствует (null). */
    private final PieceColor humanColor;

    /** Бот, против которого играет человек (только интерактивный режим). В режиме наблюдателя равен null. */
    private final BotPlayer bot;

    /** Бот, играющий белыми (только режим наблюдателя). */
    private final BotPlayer whiteBot;

    /** Бот, играющий чёрными (только режим наблюдателя). */
    private final BotPlayer blackBot;

    /** Признак режима наблюдателя (бот против бота). */
    private final boolean observerMode;

    /** Задержка между ходами ботов в режиме наблюдателя (мс). */
    private final int observerMoveDelayMillis;

    /** Таймер для отложенных ходов бота, чтобы не блокировать UI. */
    private javax.swing.Timer botMoveTimer;

    /** Отображение оставшегося времени. */
    private final JLabel timerLabel = new JLabel("", SwingConstants.CENTER);

    /** Сколько секунд осталось до окончания. */
    private int remainingSeconds;
    /** Максимальная длительность партии в секундах. */
    private final int maxDurationSeconds;


    /** Swing-таймер для обновления времени. */
    private final javax.swing.Timer swingTimer;

    /** Признак завершённой игры. */
    private boolean gameOver = false;

    public SwingGameUI(Board board, PieceColor humanColor, int maxDurationSeconds) {
        this.board = board;
        this.humanColor = humanColor;
        this.currentTurn = PieceColor.WHITE;


        this.observerMode = false;
        this.observerMoveDelayMillis = 0;
        this.whiteBot = null;
        this.blackBot = null;
        PieceColor botColor = (humanColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        this.bot = new BotPlayer(botColor, "Бот");

        this.maxDurationSeconds = maxDurationSeconds;

        this.remainingSeconds = maxDurationSeconds;

        setTitle("Шахматы");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 720);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(Board.SIZE, Board.SIZE));
        initializeBoard(boardPanel);
        add(boardPanel, BorderLayout.CENTER);

        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(timerLabel, BorderLayout.NORTH);
        updateTimerLabel();

        swingTimer = new javax.swing.Timer(1000, e -> onTick());
        swingTimer.start();

        setVisible(true);

        // если человек выбрал чёрных, бот делает первый ход
        if (currentTurn != humanColor) {
            scheduleBotMove(bot.getColor());
        }
    }

    
    /**
     * Создаёт окно в режиме наблюдателя (бот против бота).
     *
     * @param board игровая доска
     * @param maxDurationSeconds максимальная длительность партии в секундах
     * @param observerMoveDelayMillis задержка между ходами ботов (мс)
     */
    public SwingGameUI(Board board, int maxDurationSeconds, int observerMoveDelayMillis) {
        this.board = board;
        this.humanColor = null;
        this.bot = null;

        this.whiteBot = new BotPlayer(PieceColor.WHITE, "Белый бот");
        this.blackBot = new BotPlayer(PieceColor.BLACK, "Чёрный бот");

        this.observerMode = true;
        this.observerMoveDelayMillis = Math.max(0, observerMoveDelayMillis);

        this.currentTurn = PieceColor.WHITE;

        this.maxDurationSeconds = maxDurationSeconds;
        this.remainingSeconds = maxDurationSeconds;

        setTitle("Шахматы — наблюдатель");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 720);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(Board.SIZE, Board.SIZE));
        initializeBoard(boardPanel);
        add(boardPanel, BorderLayout.CENTER);

        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(timerLabel, BorderLayout.SOUTH);
        updateTimerLabel();

        swingTimer = new javax.swing.Timer(1000, e -> onTick());
        swingTimer.start();

        setVisible(true);

        // стартуем автопартию
        scheduleBotMove(currentTurn);
    }

public SwingGameUI(Board board) {
        this(board, PieceColor.WHITE, 5 * 60);
    }

/**
 * Выполняет действие: onTick.
 */
    private void onTick() {
        if (gameOver) {
            return;
        }
        remainingSeconds--;
        updateTimerLabel();

        if (remainingSeconds <= 0) {
            finishByTimeout();
        }
    }

/**
 * Выполняет действие: updateTimerLabel.
 */
    private void updateTimerLabel() {
        int minutes = Math.max(0, remainingSeconds) / 60;
        int seconds = Math.max(0, remainingSeconds) % 60;
        timerLabel.setText(String.format("Оставшееся время: %02d:%02d", minutes, seconds));
    }

/**
 * Выполняет действие: finishByTimeout.
 */
    private void finishByTimeout() {
        gameOver = true;
        swingTimer.stop();
        if (botMoveTimer != null) {
            botMoveTimer.stop();
        }

        if (observerMode) {
            JOptionPane.showMessageDialog(this, "Время вышло. Ничья.");
        } else {
            JOptionPane.showMessageDialog(this, "Время вышло. Вы проиграли.");
        }
        System.exit(0);
    }

/**
 * Выполняет действие: initializeBoard.
 * @param boardPanel параметр метода
 */
    private void initializeBoard(JPanel boardPanel) {
        for (int y = Board.SIZE - 1; y >= 0; y--) {
            for (int x = 0; x < Board.SIZE; x++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.PLAIN, 32));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setOpaque(true);
                button.setBorderPainted(false);

                button.setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.GRAY);

                final int fx = x;
                final int fy = y;
                button.addActionListener(e -> onCellClick(fx, fy));

                buttons[y][x] = button;
                boardPanel.add(button);
            }
        }
        updateBoard();
    }

/**
 * Выполняет действие: onCellClick.
 * @param x параметр метода
 * @param y параметр метода
 */
    private void onCellClick(int x, int y) {
        if (gameOver) return;

        if (observerMode) {
            JOptionPane.showMessageDialog(this, "Режим наблюдателя: управление отключено.");
            return;
        }
        if (currentTurn != humanColor) {
            JOptionPane.showMessageDialog(this, "Сейчас ходит бот.");
            return;
        }

        Cell clicked = board.getCell(x, y);
        Piece clickedPiece = clicked.getPiece();

        if (selectedCell == null) {
            if (clickedPiece == null) {
                JOptionPane.showMessageDialog(this, "Выберите фигуру для хода!");
                return;
            }
            if (clickedPiece.getColor() != humanColor) {
                JOptionPane.showMessageDialog(this, "Сейчас ваш ход. Выберите свою фигуру.");
                return;
            }
            selectedCell = clicked;
            updateBoard();
            return;
        }

        // второй клик: пытаемся сделать ход
        boolean success = board.move(selectedCell, clicked, humanColor);
        if (!success) {
            JOptionPane.showMessageDialog(this, "Некорректный ход!");
            selectedCell = null;
            updateBoard();
            return;
        }

        selectedCell = null;
        updateBoard();

        // проверка завершения после хода человека
        if (checkEndAfterMove(humanColor)) {
            return;
        }

        currentTurn = bot.getColor();
        scheduleBotMove(currentTurn);
    }

    
    /**
     * Планирует ход бота так, чтобы UI не зависал (ход выполняется через Swing Timer).
     *
     * @param side чей ход нужно выполнить
     */
/**
 * Выполняет действие: scheduleBotMove.
 * @param side параметр метода
 */
    private void scheduleBotMove(PieceColor side) {
        if (gameOver) {
            return;
        }

        int delay = observerMode ? observerMoveDelayMillis : 200;

        if (botMoveTimer != null && botMoveTimer.isRunning()) {
            botMoveTimer.stop();
        }

        botMoveTimer = new javax.swing.Timer(delay, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            performBotMove(side);
        });
        botMoveTimer.setRepeats(false);
        botMoveTimer.start();
    }

    /**
     * Возвращает бота, который должен ходить данным цветом.
     *
     * @param side цвет (сторона)
     * @return бот или null, если бота для стороны нет
     */
/**
 * Выполняет действие: getBotForSide.
 * @param side параметр метода
 * @return результат выполнения
 */
    private BotPlayer getBotForSide(PieceColor side) {
        if (observerMode) {
            return (side == PieceColor.WHITE) ? whiteBot : blackBot;
        }
        return bot;
    }

    /**
     * Выполняет один ход бота заданной стороны и переключает очередь хода.
     *
     * @param side сторона, которая делает ход
     */
/**
 * Выполняет действие: performBotMove.
 * @param side параметр метода
 */
    private void performBotMove(PieceColor side) {
        if (gameOver) return;

        if (checkEndBeforeTurn(side)) {
            return;
        }

        BotPlayer moverBot = getBotForSide(side);
        if (moverBot == null) {
            return;
        }

        List<move.Move> legalMoves = board.getLegalMoves(side);
        if (legalMoves.isEmpty()) {
            // мат/пат уже обработали выше
            return;
        }

        move.Move m = moverBot.makeMove(board);
        if (m == null) return;

        boolean ok = board.move(
                board.getCell(m.getFromRow(), m.getFromCol()),
                board.getCell(m.getToRow(), m.getToCol())
        );

        if (!ok) {
            // на всякий случай — попробуем еще раз на следующем тике
            scheduleBotMove(side);
            return;
        }

        updateBoard();

        if (checkEndAfterMove(side)) {
            return;
        }

        if (observerMode) {
            currentTurn = (side == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
            scheduleBotMove(currentTurn);
        } else {
            currentTurn = humanColor;
        }
    }


/**
 * Выполняет действие: checkEndBeforeTurn.
 * @param sideToMove параметр метода
 * @return результат выполнения
 */
    private boolean checkEndBeforeTurn(PieceColor sideToMove) {
        if (board.isCheckmate(sideToMove)) {
            PieceColor winner = (sideToMove == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
            finishGame("Мат! Победили " + winner);
            return true;
        }
        if (board.isStalemate(sideToMove)) {
            finishGame("Пат. Ничья.");
            return true;
        }
        return false;
    }

/**
 * Выполняет действие: checkEndAfterMove.
 * @param mover параметр метода
 * @return результат выполнения
 */
    private boolean checkEndAfterMove(PieceColor mover) {
        PieceColor opponent = (mover == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        if (board.isCheckmate(opponent)) {
            finishGame("Мат! Победили " + mover);
            return true;
        }
        if (board.isStalemate(opponent)) {
            finishGame("Пат. Ничья.");
            return true;
        }

        if (board.isKingInCheck(opponent)) {
            JOptionPane.showMessageDialog(this, "Шах " + opponent + "!");
        }
        return false;
    }

/**
 * Выполняет действие: finishGame.
 * @param message параметр метода
 */
    private void finishGame(String message) {
        gameOver = true;
        swingTimer.stop();

        int option = JOptionPane.showOptionDialog(
                this,
                message,
                "Игра окончена",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Выход", "Перезапустить"},
                "Выход"
        );

        if (option == 0) {
            System.exit(0);
        } else {
            restartGame();
        }
    }

/**
 * Выполняет действие: restartGame.
 */
    private void restartGame() {
        board.setupDefaultPosition();
        selectedCell = null;
        gameOver = false;
        currentTurn = PieceColor.WHITE;
        remainingSeconds = maxDurationSeconds;
        updateTimerLabel();
        swingTimer.restart();
        updateBoard();

        if (observerMode) {
            scheduleBotMove(currentTurn);
        } else if (currentTurn != humanColor) {
            scheduleBotMove(bot.getColor());
        }
    }

    /**
     * Обновляет тексты кнопок в соответствии с текущим состоянием доски.
     */
/**
 * Выполняет действие: updateBoard.
 */
    public void updateBoard() {
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                Cell cell = board.getCell(x, y);
                JButton button = buttons[y][x];
                Piece piece = cell.getPiece();

                button.setText(piece != null ? UnicodePieceSymbols.getSymbol(piece) : "");
                button.setBorder(null);
                button.setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }

        if (selectedCell != null) {
            JButton selectedButton = buttons[selectedCell.getY()][selectedCell.getX()];
            selectedButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        }
    }
}
