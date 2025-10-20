#version 410 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoord;
layout (location = 3) in float aTexID;

out vec2 vTexCoord;
out vec4 vColor;
out float vTexID;

uniform mat4 ortho;
uniform mat4 model;
uniform mat4 view;

void main()
{
    gl_Position = ortho * view * model * vec4(aPos, 1.0);
    vTexCoord = aTexCoord;
    vColor = aColor;
    vTexID = aTexID;
}