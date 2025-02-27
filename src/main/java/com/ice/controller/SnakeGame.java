package com.ice.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    private final int WIDTH = 800; // Width of the game board
    private final int HEIGHT = 600; // Height of the game board
    private final int BLOCK_SIZE = 20; // Size of each block (snake segment and food)
    private final int ALL_BLOCKS = (WIDTH * HEIGHT) / (BLOCK_SIZE * BLOCK_SIZE); // Maximum number of blocks on the board
    private final int DELAY = 140; // Delay for the game loop timer (speed of the snake)

    private final int x[] = new int[ALL_BLOCKS]; // Array to store x-coordinates of the snake's body
    private final int y[] = new int[ALL_BLOCKS]; // Array to store y-coordinates of the snake's body

    private int snakeLength; // Current length of the snake
    private int foodX; // x-coordinate of the food
    private int foodY; // y-coordinate of the food

    private boolean leftDirection = false; // Direction flags for snake movement
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true; // Flag to check if the game is still running

    private Timer timer; // Timer for game loop
    private Random random; // Random object to generate food locations

    public SnakeGame() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter()); // Add key listener for user input
        setBackground(Color.BLACK); // Set background color of the game board
        setFocusable(true); // Set focusable to capture key events
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Set the preferred size of the game board
        random = new Random();
        initGame(); // Initialize game components
        System.out.println("Board initialized.");
    }

    private void initGame() {
        snakeLength = 3; // Initial length of the snake

        // Initialize the starting position of the snake
        for (int i = 0; i < snakeLength; i++) {
            x[i] = 100 - i * BLOCK_SIZE;
            y[i] = 100;
        }

        locateFood(); // Place the first food on the board

        timer = new Timer(DELAY, this); // Set up the timer for the game loop
        timer.start();
        System.out.println("Game initialized. Snake length: " + snakeLength);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            // Draw the food
            g.setColor(Color.RED);
            g.fillRect(foodX, foodY, BLOCK_SIZE, BLOCK_SIZE);
            System.out.println("Food drawn at: (" + foodX + ", " + foodY + ")");

            // Draw the snake
            for (int i = 0; i < snakeLength; i++) {
                g.setColor(i == 0 ? Color.GREEN : Color.YELLOW); // Head is green, body is yellow
                g.fillRect(x[i], y[i], BLOCK_SIZE, BLOCK_SIZE);
            }
            System.out.println("Snake drawn. Length: " + snakeLength);

            Toolkit.getDefaultToolkit().sync(); // Sync the graphics for smooth rendering
        } else {
            gameOver(g); // Display game over message if the game is not active
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2); // Center the game over message
        System.out.println("Game Over.");
    }

    private void checkFood() {
        // Check if the snake's head is at the same position as the food
        if ((x[0] == foodX) && (y[0] == foodY)) {
            snakeLength++; // Increase the length of the snake
            locateFood(); // Place new food on the board
            System.out.println("Food eaten. New snake length: " + snakeLength);
        }
    }

    private void move() {
        // Move the snake by updating the position of each segment
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Update the position of the head based on the direction
        if (leftDirection) {
            x[0] -= BLOCK_SIZE;
        }

        if (rightDirection) {
            x[0] += BLOCK_SIZE;
        }

        if (upDirection) {
            y[0] -= BLOCK_SIZE;
        }

        if (downDirection) {
            y[0] += BLOCK_SIZE;
        }

        System.out.println("Snake moved. Head position: (" + x[0] + ", " + y[0] + ")");
    }

    private void checkCollision() {
        // Check if the snake has collided with itself
        for (int i = snakeLength; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
                System.out.println("Collision detected with body. Game over.");
            }
        }

        // Check if the snake has collided with the walls
        if (y[0] >= HEIGHT || y[0] < 0 || x[0] >= WIDTH || x[0] < 0) {
            inGame = false;
            System.out.println("Collision detected with wall. Game over.");
        }

        if (!inGame) {
            timer.stop(); // Stop the timer if the game is over
        }
    }

    private void locateFood() {
        // Randomly place the food on the board
        foodX = random.nextInt(WIDTH / BLOCK_SIZE) * BLOCK_SIZE;
        foodY = random.nextInt(HEIGHT / BLOCK_SIZE) * BLOCK_SIZE;
        System.out.println("New food located at: (" + foodX + ", " + foodY + ")");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkFood(); // Check if the snake has eaten the food
            checkCollision(); // Check if the snake has collided with itself or walls
            move(); // Move the snake
        }

        repaint(); // Repaint the game board
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            // Update direction based on the key pressed
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                System.out.println("Left direction set.");
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                System.out.println("Right direction set.");
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
                System.out.println("Up direction set.");
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
                System.out.println("Down direction set.");
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SnakeGame game = new SnakeGame();
        frame.add(game); // Add the game panel to the frame
        frame.setResizable(false); // Prevent resizing the frame
        frame.pack(); // Pack the frame to fit the preferred size
        frame.setTitle("Snake Game"); // Set the title of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the close operation
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible
        System.out.println("Game started.");
    }
}