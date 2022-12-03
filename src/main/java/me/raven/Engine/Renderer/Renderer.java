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

    private Queue<TexturedBatchRenderer> texturedBatchRenderers = new ConcurrentLinkedQueue<>();
    private Queue<ColoredBatchRenderer> coloredBatchRenderers = new ConcurrentLinkedQueue<>();

    public Renderer() {

    }

    public void onRender() {
        for (TexturedBatchRenderer renderer : texturedBatchRenderers) {
            renderer.updateData();
            renderer.draw();
        }
        for (ColoredBatchRenderer renderer : coloredBatchRenderers) {
            renderer.updateData();
            renderer.draw();
        }
    }

    public void texturedDraw(Quad quad) {
        float[] data = quad.getData();
        TexturedBatchRenderer batchRenderer = null;
        for (TexturedBatchRenderer renderer : texturedBatchRenderers) {
            if (renderer.hasEnoughSpace(data) || renderer.hasEnoughSlot()) {
                batchRenderer = renderer;
            }
        }

        if (batchRenderer == null) {
            batchRenderer = new TexturedBatchRenderer();
            batchRenderer.putData(quad);
            texturedBatchRenderers.add(batchRenderer);
        } else {
            batchRenderer.putData(quad);
        }
    }

    public void coloredDraw(Drawable drawable) {
        float[] data = drawable.getData();
        ColoredBatchRenderer batchRenderer = null;
        for (ColoredBatchRenderer renderer : coloredBatchRenderers) {
            if (renderer.hasEnoughSpace(data)) {
                batchRenderer = renderer;
            }
        }

        if (batchRenderer == null) {
            batchRenderer = new ColoredBatchRenderer();
            batchRenderer.putData(data);
            coloredBatchRenderers.add(batchRenderer);
        } else {
            batchRenderer.putData(data);
        }
    }

    public void shutdown() {
        for (TexturedBatchRenderer renderer : texturedBatchRenderers) {
            renderer.end();
        }
        for (ColoredBatchRenderer renderer : coloredBatchRenderers) {
            renderer.end();
        }
    }

    public void createTextureBatchRenderer() {
        texturedBatchRenderers.add(new TexturedBatchRenderer());
    }

    public void createColoredBatchRenderer() {
        coloredBatchRenderers.add(new ColoredBatchRenderer());
    }

    public void removeTexturedBatchRenderer() {
        // later gonna add it
    }

    public void removeColoredBatchRenderer() {
        // later gonna do it
    }

    public Queue<TexturedBatchRenderer> getTexturedBatchRenderers() {
        return texturedBatchRenderers;
    }

    public Queue<ColoredBatchRenderer> getColoredBatchRenderers() {
        return coloredBatchRenderers;
    }
}
