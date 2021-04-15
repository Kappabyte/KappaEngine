package net.kappabyte.kappaengine.graphics;

public class Mesh {
    private float[] verticies;
    private float[] uvs;
    private int[] indicies;

    public Mesh(float[] verticies, int[] indicies, float[] uvs) {
        this.verticies = verticies;
        this.indicies = indicies;
        this.uvs = uvs;
    }

    public void setVerticiesAndIndicies(float[] verticies, int[] indicies) {
        this.verticies = verticies;
        this.indicies = indicies;
    }

    public float[] getVerticies() {
        return verticies;
    }

    public int[] getIndicies() {
        return indicies;
    }

    public float[] getUVs() {
        return uvs;
    }
}
