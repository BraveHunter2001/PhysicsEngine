package components;

import engine.Component;

public class SpriteRenderer extends Component {
    boolean fisttime =false;
    @Override
    public void update(float dt) {
        if (!fisttime) {
            System.out.println("I am updating");
            fisttime = true;
        }
    }

    @Override
    public void start() {
        System.out.println("I am starting");
    }
}
