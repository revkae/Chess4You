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

    FragColor = texture(textures[nonuniformEXT(texID)], vTexCoord);

//    switch (texID) {
//        case 0:
//            FragColor = texture(textures[0], vTexCoord) * vColor;
//            break;
//        case 1:
//            FragColor = texture(textures[1], vTexCoord) * vColor;
//            break;
//        case 2:
//            FragColor = texture(textures[2], vTexCoord) * vColor;
//            break;
//    }
}