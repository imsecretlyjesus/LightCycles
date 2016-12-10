#version 330 core
in vec2 TexCoord;
out vec4 color;

uniform sampler2D ourTexture;

void main()
{
    color = texture(ourTexture, TexCoord);//  texture
    //color = vec4(0.5f, 0.5f, 0.5f, 1.0f);
}
