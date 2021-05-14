package engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import until.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    String title;
    private long glfwWindow;

    private static Window window = null;
    private float r, g, b, a;
    private boolean fadeToBack = false;


    private Window() {
        this.width = 900;
        this.height = 600;
        this.title = "PhysicsEngine";
        r = 1;
        b = 1;
        g = 1;
        a = 1;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        //free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and the free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        //Setup an error callBack
        GLFWErrorCallback.createPrint(System.err).set();

        //init glfw
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Config GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        //glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // start max

        //create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create GLFW window");
        }
        // add callback for MouseListener
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

    }

    public void loop() {
        float beginTime = Time.getTime();
        float endTime;

        while (!glfwWindowShouldClose(glfwWindow)) {
            // poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (fadeToBack) {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }


            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                fadeToBack = true;
            }

            glfwSwapBuffers(glfwWindow);

            // time calc
            endTime = Time.getTime();
            float dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
