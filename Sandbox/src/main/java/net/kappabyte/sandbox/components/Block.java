package net.kappabyte.sandbox.components;

import net.kappabyte.kappaengine.graphics.Mesh;
import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.graphics.materials.Material;
import net.kappabyte.kappaengine.scenes.components.Renderable;

public class Block extends Renderable {

    private static Mesh mesh;

    static {
        float[] vertices = new float[] {
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
        };

        int[] indices = new int[] {
            //back face
            0, 1, 2,
            4, 2, 1,
            //front face
            3, 5, 6,
            7, 6, 5,
            //left face
            0, 2, 3,
            5, 3, 2,
            //right face
            1, 6, 4,
            7, 4, 6,
            //top face
            2, 4, 5,
            7, 5, 4,
            //bottom face
            0, 3, 1,
            6, 1, 3
        };

        float[] normals = new float[] {};

        float[] uvs = new float[] {
            1, 1,
            0, 1,
            1, 0,
            1, 1,
            0, 0,
            0, 0,
            1, 1,
            1, 0
        };

        mesh = new Mesh(vertices, indices, normals, uvs);
    }

    public Block(Material material) {
        super(material, true);
    }

    public RenderData supplyRenderData() {
        return new RenderData(getTransform(), mesh, material, getGameObject());
    }
}
