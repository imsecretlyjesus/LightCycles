#version 330 core
in vec2 TexCoord;
out vec4 color;

uniform sampler2D ourTexture;

void main()
{
    color = texture(ourTexture, vec2(TexCoord.x, 1.0f - TexCoord.y));//  texture
    if (color.w < 1.0f)
        discard;
}
