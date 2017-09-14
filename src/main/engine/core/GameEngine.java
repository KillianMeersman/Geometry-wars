package main.engine.core;

import main.engine.input.MouseInput;
import main.engine.utils.Timer;

public class GameEngine implements Runnable {
    public static final int TARGET_FPS = 60;
    public static final int TARGET_UPS = 60;
    private IGameLogic gameLogic;
    private Window window;
    private final Timer timer, fpsTimer;
    private final Thread gameLoopThread;
    private final MouseInput mouseInput;

    public GameEngine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
        fpsTimer = new Timer();
    }

    private void init() throws Exception {
        window.init();
        window.show();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
    }

    private void gameLoop() {
        int fps = 0;
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            timer.reset();
            accumulator += elapsedTime;


            fps++;
            if (fpsTimer.getElapsedTime() >= 1.0f) {
                System.out.println("FPS: " + fps);
                fps = 0;
                fpsTimer.reset();
            }

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }


            render();

            if (!window.isvSync()) {
                sync();
            }
        }
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {

            }
        }
    }

    protected void input() {
        mouseInput.input(window);
        gameLogic.input(window, mouseInput);
    }

    protected void update(float interval) {
        gameLogic.update(interval, mouseInput);
    }

    protected void cleanup() {
        gameLogic.cleanup();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {
            gameLoopThread.run();
        }
        else {
            gameLoopThread.start();
        }
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
}