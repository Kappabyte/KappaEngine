package net.kappabyte.kappaengine.graphics.materials;

import net.kappabyte.kappaengine.graphics.ShaderProgram;

public interface I3DMaterial {
    public default void init3D(ShaderProgram program) {
        program.createUniform("projectionMatrix");
        program.createUniform("modelViewMatrix");
    }
}
