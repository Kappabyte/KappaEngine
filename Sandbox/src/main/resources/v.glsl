#version 130

in vec3 position;
in vec3 normals;

void main() {
  gl_Position = vec4(position, 1.0);
}
