package engine;


import components.FontRenderer;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;
import until.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends  Scene{

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            //position                  //color                //UV coordinates
            100f, 0f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f,     1,1,// bottom right 0
            0f, 100.5f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f,   0,0,//top left      1
            100f, 100.5f, 0.0f,   1.0f, 0.0f, 1.0f, 1.0f,     1,0,// top right    2
            0, 0f, 0.0f,         1.0f, 1.0f, 0.0f, 1.0f,     0,1  // bottom left 3
    };
    //Important: Must be in counter-clockwise order
    private int[] elementArray = {


            2, 1, 0, // top right triangle
            0, 1, 3 // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Shader defaultShader;
    private Texture testTexture;
    private GameObject testObj;
    boolean firstTime = false;


    public LevelEditorScene(){

    }

    @Override
    public void init()
    {
        System.out.println("Creat 'Test object'");
        this.testObj = new GameObject("Test object");
        this.testObj.addComponent(new SpriteRenderer());
        this.testObj.addComponent(new FontRenderer());
        this.addGameObjectToScene(this.testObj);

        this.camera =  new Camera( new Vector2f(-200, -300));
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        this.testTexture = new Texture("assets/image/test.jpg");
        //==========================================================
        //Generate VAO, VBO and EBO buffer objects, and send to GPU
        //===========================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //create float buffer of vertex

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        //Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        //Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int uvSize = 2;

        int vertexSizeByte = (positionsSize + colorSize + uvSize) * Float.BYTES;

        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeByte, 0);
        glEnableVertexAttribArray(0); // this index - is index layout location in shader

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeByte, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize,GL_FLOAT, false, vertexSizeByte, (positionsSize + colorSize)*Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float dt) {

        //System.out.println(1 / dt); //fps
        defaultShader.use();

        //upload texture to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();

        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());
        //bind the VAO that using
        glBindVertexArray(vaoID);

        //enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length,GL_UNSIGNED_INT, 0);


        //Unbind everything;
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();

        if(!firstTime) {
            System.out.println("Tets2");
            GameObject go = new GameObject("Test2");
            go.addComponent(new SpriteRenderer());
            this.addGameObjectToScene(go);
            firstTime = true;
        }
        for(GameObject go: gameObjects)
        {
            go.update(dt);
        }
    }
}
