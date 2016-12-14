#version 330 core
in vec2 TexCoord;
out vec4 color;

uniform sampler2D bikeTexture;

void main()
{
    color = texture(bikeTexture, TexCoord);//  texture
    if (color.w < 1.0f)
        discard;
}
