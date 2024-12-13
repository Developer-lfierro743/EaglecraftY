#version 330 core

in vec2 TexCoord; // Texture coordinates from the vertex shader

out vec4 FragColor; // Output color

uniform sampler2D textureSampler; // Texture sampler

void main() {
    FragColor = texture(textureSampler, TexCoord);
}