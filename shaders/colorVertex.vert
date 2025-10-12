#version 460 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;

out vec4 vColor;

uniform mat4 ortho;
uniform mat4 model;
uniform mat4 view;

void main()
{
    gl_Position = ortho * view * model * vec4(aPos, 1.0);
    vColor = aColor;
}