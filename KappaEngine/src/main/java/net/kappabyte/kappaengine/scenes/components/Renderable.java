package net.kappabyte.kappaengine.scenes.components;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.graphics.materials.Material;
import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.util.Log;
import net.kappabyte.kappaengine.util.Profiling;
import org.joml.Matrix4f;

import static org.lwjgl.system.MemoryUtil.*;

public abstract class Renderable extends Component {

    private int vao, verticiesVBO, indiciesVBO = 0;
    private boolean staticGeometry = false;

    protected Material material;

    public Renderable(GameObject gameObject, Material material, boolean staticGeometry) {
        super();
        this.material = material;
        
        this.staticGeometry = staticGeometry;
    }

    protected abstract RenderData supplyRenderData();

    public void Init() {
        //Get the render data and bind the shader
        RenderData data = supplyRenderData();
        data.getShaderProgram().bind();

        //Create the vao - stores a bunch of vbos
        vao = GL30.glGenVertexArrays();
        //GL30.glBindVertexArray(vao);
        Log.debug("VAO generated and bound!");

        //Init uniform variables
        //VBOs is a memory buffer of the GPU which stores vertex information. We create the buffer, bind it so we can use it, set the data and free the memory used by our data as we no longer need it.
        verticiesVBO = GL30.glGenBuffers();
        indiciesVBO = GL30.glGenBuffers();

        updateVBOs(data);
    }

    protected void updateVBOs(RenderData data) {
        Profiling.startTimer();
        
        //Modify data to be readble by openGL
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(data.getMesh().getVerticies().length);
        verticesBuffer.put(data.getMesh().getVerticies()).flip();
        IntBuffer indiciesBuffer = MemoryUtil.memAllocInt(data.getMesh().getIndicies().length);
        indiciesBuffer.put(data.getMesh().getIndicies()).flip();

        //Bind all the stuff
        GL30.glBindVertexArray(vao);

        //Set Data for Verticies
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, verticiesVBO);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, verticesBuffer, GL30.GL_STATIC_DRAW);
        memFree(verticesBuffer);

        //Define the structure of our VBO, and store it in the VAO
        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);

        //Set Data for Indicies
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, indiciesVBO);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL30.GL_STATIC_DRAW);
        memFree(indiciesBuffer);
        Profiling.stopTimer("Update VBOs");

        //Material data
        Profiling.startTimer();
        data.getMaterial().setRenderData(data);
        Profiling.stopTimer("Material Render");

        //Unbind everything, as we dont need it anymore
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void Render() {
        //Get the render data and bind the shader
        RenderData data = supplyRenderData();
        data.getShaderProgram().bind();

        //Update the data to be rendered
        if(staticGeometry) {
            updateVBOs(data);
        }
        
        Profiling.startTimer();
        //Update matricies
        data.getShaderProgram().setUniform("modelViewMatrix", data.getTransform().getModelViewMatrix(data.getCamera().getViewMatrix()));
        data.getShaderProgram().setUniform("projectionMatrix", data.getCamera().getProjectionMatrix());
        //Bind our vao (dont need to bind vbo, as it is stored in the vao)
        GL30.glBindVertexArray(vao);
        GL30.glEnableVertexAttribArray(0);

        data.getMaterial().render(data);
        data.getMaterial().enableVertexAttribArrays();

        //Draw the stuff
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, indiciesVBO);
        GL30.glFrontFace(GL30.GL_CW);
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        GL30.glDrawElements(GL30.GL_TRIANGLES, data.getMesh().getIndicies().length, GL30.GL_UNSIGNED_INT, 0);

        //Unbind vao
        GL30.glDisableVertexAttribArray(0);
        data.getMaterial().disableVertexAttribArrays();
        GL30.glBindVertexArray(0);

        //Unbind the shader, which will be used next render call
        data.getShaderProgram().unbind();
        Profiling.stopTimer("Render");
    }

    public final void Cleanup() {
        //Get the render data and bind the shader
        RenderData data = supplyRenderData();
        data.getShaderProgram().bind();

        if (data.getShaderProgram() != null) {
            data.getShaderProgram().cleanup();
        }

        GL30.glDisableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(verticiesVBO);
        GL30.glDeleteBuffers(indiciesVBO);

        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vao);
    }

    public void onStart() {
        //Init
        Init();
    }

    public void onUpdate() {
        //Do nothing
    }

    public void onDestroy() {
        //Cleanup
        Cleanup();
    }
}
