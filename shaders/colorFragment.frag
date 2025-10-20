#version 410 core

out vec4 FragColor;

in vec4 vColor;

void main()
{
    FragColor = vColor;
}