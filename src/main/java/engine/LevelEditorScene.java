package engine;


import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorScene extends  Scene{




    public LevelEditorScene(){

    }

    @Override
    public void init()
    {
        loadResources();
        this.camera = new Camera( new Vector2f(0, 0));


        Spritesheet sprites = AssetPool.getSpritesheet("assets/image/spritesheet.png");

        GameObject obj1 = new GameObject("obj1", new Transform(new Vector2f(0, 0), new Vector2f(200, 200)));
        obj1.addComponent(new SpriteRenderer(new Sprite( AssetPool.getTexture("assets/image/niko.png"))));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("obj2", new Transform(new Vector2f(210, 0), new Vector2f(200, 200)));
        obj2.addComponent(new SpriteRenderer(new Sprite( AssetPool.getTexture("assets/image/test.jpg"))));
        this.addGameObjectToScene(obj2);


    }

    private void loadResources(){

        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/image/spritesheet.png",new Spritesheet(AssetPool.getTexture("assets/image/spritesheet.png"),130, 130, 4,1));
    }

    @Override
    public void update(float dt) {
        Window.setNameWindow((int)(1/dt));


        for(GameObject go: gameObjects)
        {
            go.update(dt);
        }

        this.renderer.render();

    }
}
