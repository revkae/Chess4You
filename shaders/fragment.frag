#version 460 core

out vec4 FragColor;

in vec2 vTexCoord;
in vec4 vColor;
in float vTexID;

uniform sampler2D textures[32];

void main()
{
    int texID = int(vTexID);

    vec4 texColor = texture(textures[texID], vTexCoord) * vColor;
    if (texColor.a < 0.1) {
        discard;
    }
    FragColor = texColor;
}