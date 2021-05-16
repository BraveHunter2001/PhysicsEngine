package engine;

import engine.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f projectionMatrix, viewMatrix;
    public Vector2f position;
    private float zoom;
    float widthWindow;
    float heightWindow;


    public Camera(Vector2f position) {
        this.widthWindow = Window.getWidth();
        this.heightWindow = Window.getHeight();
        this.zoom = 0;

        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        projectionMatrix.identity();

        //32*32 pixel by 9 * 2 grid RIGHT
        //32*32 pixel by 6 * 2 grid UP
        projectionMatrix.ortho(zoom, widthWindow - zoom , zoom* heightWindow/widthWindow, heightWindow - zoom* heightWindow/widthWindow, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public void setZoom(float zoom){
        this.zoom = zoom;
    }

    public void setPositionCamera(float xPos, float yPos){
        this.position.x += xPos;
        this.position.y -= yPos;
    }

}