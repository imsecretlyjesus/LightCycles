#version 330 core
in vec3 ourColor;
in vec2 TexCoord;
out vec4 color;

uniform sampler2D ourTexture;

void main()
{
    //color = vec4(1.0f, 0.5f, 0.2f, 1.0f); //  orange color
    color = vec4(ourColor, 1.0f);           //  custom color
    //color = texture(ourTexture, TexCoord);//  texture
}
