package net.kappabyte.kappaengine.scenes.components;

import net.kappabyte.kappaengine.graphics.FragmentShader;
import net.kappabyte.kappaengine.graphics.Mesh;
import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.graphics.Shader;
import net.kappabyte.kappaengine.graphics.VertexShader;
import net.kappabyte.kappaengine.graphics.materials.Material;
import net.kappabyte.kappaengine.graphics.materials.RainbowMaterial;
import net.kappabyte.kappaengine.scenes.GameObject;

public class TriangleRender extends Renderable {
    
    private Mesh mesh;

    public TriangleRender(GameObject gameObject, Material material) {
        super(gameObject, material, true);
        
        mesh = new Mesh(new float[] {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
        }, new int[]{0, 1, 2}, new float[0]);
    }

    @Override
    public RenderData supplyRenderData() {
        return new RenderData(getTransform(), mesh, material, getGameObject());
    }

}
