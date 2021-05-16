package engine;


import components.SpriteRenderer;
import org.joml.Vector2f;
import util.AssetPool;

public class LevelEditorScene extends  Scene{




    public LevelEditorScene(){

    }

    @Override
    public void init()
    {
        this.camera = new Camera( new Vector2f(0, 0));




        GameObject obj1 = new GameObject("obj1", new Transform(new Vector2f(0, 0), new Vector2f(200, 200)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/image/test.jpg")));
        this.addGameObjectToScene(obj1);


        loadResources();
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
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
