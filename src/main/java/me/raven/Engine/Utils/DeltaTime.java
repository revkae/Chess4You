package me.raven.Engine.Utils;

public class DeltaTime {

    static float deltaTime;
    static float beginTime = Time.getTime();
    static float endTime = Time.getTime();

    public static void calculate() {
        endTime = Time.getTime();
        deltaTime = endTime - beginTime;
        beginTime = endTime;
    }

    public static float get() {
        return deltaTime;
    }
}
