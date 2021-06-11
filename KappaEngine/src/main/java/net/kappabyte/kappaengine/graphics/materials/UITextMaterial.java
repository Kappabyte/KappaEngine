package net.kappabyte.kappaengine.graphics.materials;

import net.kappabyte.kappaengine.graphics.FragmentShader;
import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.graphics.ShaderProgram;
import net.kappabyte.kappaengine.graphics.Texture;
import net.kappabyte.kappaengine.graphics.VertexShader;

public class UITextMaterial extends TexturedMaterial {

    static ShaderProgram program = new ShaderProgram(new VertexShader(UITextMaterial.class.getResourceAsStream("/assets/shaders/uitext.vert")), new FragmentShader(UITextMaterial.class.getResourceAsStream("/assets/shaders/uitext.frag")));
    static {
        program.createUniform("colour");
        program.createUniform("projModelMatrix");
    }

    public UITextMaterial(Texture texture) {
        super(texture);
    }

    @Override
    public ShaderProgram getShaderProgram() {
        return program;
    }

    @Override
    public void setRenderData(RenderData data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(RenderData data) {
        // TODO Auto-generated method stub

    }

}
