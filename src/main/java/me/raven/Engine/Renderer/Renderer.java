package me.raven.Engine.Renderer;

import me.raven.Sandbox.Managers.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Renderer {

    private Queue<BatchRenderer> batchRenderers = new ConcurrentLinkedQueue<>();

    public Renderer() {

    }

    public void onRender() {
        for (BatchRenderer renderer : batchRenderers) {
            renderer.updateData();
            renderer.draw();
        }
    }

    public void putData(float[] data) {
        for (BatchRenderer renderer : batchRenderers) {
            if (!renderer.hasEnoughSpace(data)) {
                createBatchRenderer();
            } else {
                renderer.putData(data);
            }
        }
    }

    public void createBatchRenderer() {
        batchRenderers.add(new BatchRenderer());
    }

    public void removeBatchRenderer() {
        // later gonna add it
    }

    public Queue<BatchRenderer> getBatchRenderers() {
        return batchRenderers;
    }
}
