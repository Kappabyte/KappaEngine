package net.kappabyte.kappaengine.scenes.components;

import net.kappabyte.kappaengine.graphics.FragmentShader;
import net.kappabyte.kappaengine.graphics.Mesh;
import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.graphics.Shader;
import net.kappabyte.kappaengine.graphics.ShaderProgram;
import net.kappabyte.kappaengine.graphics.VertexShader;
import net.kappabyte.kappaengine.graphics.materials.Material;
import net.kappabyte.kappaengine.graphics.materials.RainbowMaterial;
import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.util.Log;

public class SquareRender extends Renderable {
    
    private Mesh mesh;

    public SquareRender(GameObject gameObject, Material material) {
        super(gameObject, material, true);
        
        float[] positions = new float[]{
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,};
        int[] indices = new int[]{
            0, 1, 3, 3, 1, 2,};
        mesh = new Mesh(positions, indices, new float[0]);
    }

    @Override
    public RenderData supplyRenderData() {
        return new RenderData(getTransform(), mesh, material, getGameObject());
    }

}
