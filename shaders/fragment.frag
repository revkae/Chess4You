#version 330 core

out vec4 FragColor;

in vec2 vTexCoord;
in vec4 vColor;

uniform sampler2D fTexture;
uniform sampler2D test;

void main()
{
    FragColor = mix(texture(fTexture, vTexCoord), texture(test, vTexCoord), 0.5) * vColor;
}