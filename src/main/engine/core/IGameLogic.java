package main.engine.core;

import main.engine.input.MouseInput;

public interface IGameLogic {
    void init(Window window) throws Exception;

    void input(Window window, MouseInput mouseInput);

    void update(float interval, MouseInput mouseInput);

    void render(Window window);

    void cleanup();
}
