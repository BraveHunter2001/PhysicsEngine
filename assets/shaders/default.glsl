#type vertex
#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoords;
layout (location = 3) in float aTexId;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fragColor;
out vec3 fragCoord;
out vec2 fTexCoords;
out float fTexId;


void main() {
    fragColor = aColor;
    fragCoord = aPos;
    fTexCoords = aTexCoords;
    fTexId = aTexId;

    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core
    in vec4 fragColor;

    in vec2 fTexCoords;
    in float fTexId;



    uniform sampler2D uTexures[8];

    out vec4 color;

void main() {
    int id = int(fTexId);
    if (fTexId > 0){
        color = fragColor * texture(uTexures[id], fTexCoords) ;

    }else{
        color = fragColor;
    }
}