#version 330 core
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

out vec2 TexCoord;

uniform mat4 ml_matrix = mat4(1.0f);

void main()
{
    gl_Position = ml_matrix * vec4(position, 1.0f);
    TexCoord = texCoord;
}
