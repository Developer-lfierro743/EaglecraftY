#version 330 core

layout(location = 0) in vec3 aPos; // Vertex position
layout(location = 1) in vec2 aTexCoord; // Texture coordinates

uniform mat4 model; // Model matrix
uniform mat4 view; // View matrix
uniform mat4 projection; // Projection matrix

out vec2 TexCoord; // Pass texture coordinates to the fragment shader

void main() {
    gl_Position = projection * view * model * vec4(aPos, 1.0);
    TexCoord = aTexCoord;
}