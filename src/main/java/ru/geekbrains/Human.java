package ru.geekbrains;

public class Human implements Subject {

    private String name;
    private int maxDistance;
    private int maxJump;
    private boolean Alive;

    public Human(String name, int maxDistance, int maxJump) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.maxJump = maxJump;
        this.Alive = true;
    }

    @Override
    public void run(int distance) {
        if (distance <= maxDistance) {
            System.out.println(name + " успешно бежал на 2 ногах " + distance + " м");
        } else {
            System.out.println(name + " не смог бежать " + distance + " м");
            Alive = false;
        }
    }

    @Override
    public void heightJump(int height) {
        if (height <= maxJump) {
            System.out.println(name + " успешно перепрыгнул " + height + " cм стену");
        } else {
            System.out.println(name + " не смог перепрыгнуть " + height + " cм стену");
            Alive = false;
        }
    }

    @Override
    public boolean isAlive() {
        return Alive;
    }
}
