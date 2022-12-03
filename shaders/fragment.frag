#version 450 core

out vec4 FragColor;

in vec2 vTexCoord;
in vec4 vColor;
in float vTexID;

uniform sampler2D textures[32];

void main()
{
    int texID = int(vTexID);
    FragColor = texture(textures[texID], vTexCoord) * vColor;
}