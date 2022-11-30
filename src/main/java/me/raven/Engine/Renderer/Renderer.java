package me.raven.Engine.Renderer;

import me.raven.Engine.Drawable;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Vertex;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

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

    public void draw(Drawable drawable) {
        float[] data = drawable.getData();
        BatchRenderer batchRenderer = null;
        for (BatchRenderer renderer : batchRenderers) {
            if (renderer.hasEnoughSpace(data)) {
                batchRenderer = renderer;
            }
        }

        if (batchRenderer == null) {
            batchRenderer = new BatchRenderer();
            batchRenderer.putData(data);
            batchRenderers.add(batchRenderer);
        } else {
            batchRenderer.putData(data);
        }
    }

    public void drawQuad(Vector3f position, Vector4f color, Vector2f scale, float texID) {
        float[] data = Vertex.createQuad(position, color, scale, texID);
        BatchRenderer batchRenderer = null;
        for (BatchRenderer renderer : batchRenderers) {
            if (renderer.hasEnoughSpace(data)) {
                batchRenderer = renderer;
            }
        }

        if (batchRenderer == null) {
            batchRenderer = new BatchRenderer();
            batchRenderer.putData(data);
            batchRenderers.add(batchRenderer);
        } else {
            batchRenderer.putData(data);
        }
    }

    public void shutdown() {
        for (BatchRenderer renderer : batchRenderers) {
            renderer.end();
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
