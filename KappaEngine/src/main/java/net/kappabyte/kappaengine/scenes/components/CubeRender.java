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

public class CubeRender extends Renderable {
    
    private Mesh mesh;

    public CubeRender(GameObject gameObject, Material material) {
        super(gameObject, material, true);
        
        float[] positions = new float[]{
            -1, -1, -1,
            1, -1, -1,
            1, 1, -1,
            -1, 1, -1,
            -1, -1, 1,
            1, -1, 1,
            1, 1, 1,
            -1, 1, 1
        };
        int[] indices = new int[]{
            0, 1, 3, 3, 1, 2,
            1, 5, 2, 2, 5, 6,
            5, 4, 6, 6, 4, 7,
            4, 0, 7, 7, 0, 3,
            3, 2, 7, 7, 2, 6,
            4, 5, 0, 0, 5, 1
        };
        float[] uvs = new float[]{
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1
        };
        mesh = new Mesh(positions, indices, uvs);
    }

    @Override
    public RenderData supplyRenderData() {
        return new RenderData(getTransform(), mesh, material, getGameObject());
    }

}
