
#type vertex
#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoords;
layout (location = 3) in float aTexId;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec3 fCoord;
out vec2 fTexCoords;
out float fTexId;


void main() {
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId;

    fCoord = aPos;
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core
    in vec4 fColor;
    in vec2 fTexCoords;
    in float fTexId;
    in vec3 fCoord;



    uniform sampler2D uTextures[8];
    uniform float uTime;
    uniform vec2 uResolution;

    out vec4 color;

void main() {

    vec2 uv = fCoord.xy/uResolution.xy;
    vec3 grad = 0.5 + 0.5*cos(uTime + uv.xyx + vec3(0f,2f,4f));



    if (fTexId > 0){
        int id = int(fTexId);
        color = fColor * texture(uTextures[id], fTexCoords) *vec4(grad, 1);

    }else{
        color = fColor;
    }

}