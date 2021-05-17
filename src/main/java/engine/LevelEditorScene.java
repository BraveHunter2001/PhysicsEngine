package engine;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderer.Texture;
import util.AssetPool;

import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(0, 0));


        //Spritesheet sprites = AssetPool.getSpritesheet("assets/image/spritesheet.png");

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(0, 0), new Vector2f(900, 600)));
        obj1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/image/niko.png"))));
        this.addGameObjectToScene(obj1);


    }


    int zoom = 0;
    @Override
    public void update(float dt) {
        Window.setNameWindow((int)(1/dt));


        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        camera.setZoom(zoom++);
        camera.adjustProjection();




        this.renderer.render();
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/image/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/image/spritesheet.png"),
                        130, 130, 4, 1));
    }
}