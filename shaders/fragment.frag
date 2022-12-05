#version 450 core
#extension GL_EXT_nonuniform_qualifier : require

out vec4 FragColor;

in vec2 vTexCoord;
in vec4 vColor;
in float vTexID;

uniform sampler2D textures[32];

void main()
{
    int texID = int(vTexID);

    vec4 texColor = texture(textures[nonuniformEXT(texID)], vTexCoord) * vColor;
    if (texColor.a < 0.1) {
        discard;
    }
    FragColor = texColor;
}