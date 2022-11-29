#version 450 core

out vec4 FragColor;

in vec2 vTexCoord;
in vec4 vColor;
in float vTexID;

uniform sampler2D fTexture0;
uniform sampler2D fTexture1;

void main()
{
    if (vTexID == 0.0f) {
        FragColor = texture(fTexture0, vTexCoord);
    } else if (vTexID == 1.0f) {
        FragColor = texture(fTexture1, vTexCoord);
    }
}