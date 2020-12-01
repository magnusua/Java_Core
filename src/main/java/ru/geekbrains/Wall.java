package ru.geekbrains;

public class Wall implements Object {

    private int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void pass(Subject subject) {
        subject.heightJump(height);
    }
}
