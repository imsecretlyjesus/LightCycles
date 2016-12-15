#version 330 core
out vec4 color;

uniform vec3 trail_color;

void main()
{
    color = vec4(trail_color, 1.0f);
}
