package ru.geekbrains;

public class Distance implements Object{
    private int distance;

    public Distance(int distance) {
        this.distance = distance;
    }

    @Override
    public void pass(Subject subject) {
       subject.run(distance);
    }

}
