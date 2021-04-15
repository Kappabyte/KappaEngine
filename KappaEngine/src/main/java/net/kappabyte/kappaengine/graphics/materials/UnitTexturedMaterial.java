package net.kappabyte.kappaengine.graphics.materials;

import net.kappabyte.kappaengine.graphics.FragmentShader;
import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.graphics.ShaderProgram;
import net.kappabyte.kappaengine.graphics.Texture;
import net.kappabyte.kappaengine.graphics.VertexShader;

public class UnitTexturedMaterial extends TexturedMaterial {

    static ShaderProgram program;

    static {
        program = new ShaderProgram(new VertexShader(RainbowMaterial.class.getResourceAsStream("/unlittextured.vert")), new FragmentShader(RainbowMaterial.class.getResourceAsStream("/unlittextured.frag")));
    }

	public UnitTexturedMaterial(Texture texture) {
		super(texture);
	}

	@Override
	public ShaderProgram getShaderProgram() {
		return program;
	}

}
