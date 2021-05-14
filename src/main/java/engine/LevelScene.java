package engine;

public class LevelScene extends Scene{

    public LevelScene(){
        System.out.println("Inside level scene");
        Window.get().r = 1f;
        Window.get().g = 1f;
        Window.get().b = 1f;
    }

    @Override
    public void update(float dt) {

    }
}
