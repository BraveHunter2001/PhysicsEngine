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

vec4 get (int x, int y){
    return texture(uTextures[int(fTexId)], fTexCoords + vec2(x/uResolution.x, y/uResolution.y));
}

void main() {

    float self = get(0,0).a;

    int neigh = int(
    get(-1,-1).a+
    get(-1,0).a+
    get(0,-1).a+
    get(1,0).a+
    get(0,1).a+
    get(1,1).a+
    get(1,-1).a+
    get(-1, 1).a
    );

    if (fTexId> 0){
        if(self > 0.5) {
            if (neigh < 2)
            color = vec4(0, 0, 0, 0);
            else if (neigh == 2|| neigh==3)
            color = vec4(1, 1, 1, 1);
            else
            color = vec4(0, 0, 0, 0);

        }else if (self <= 0.5 && neigh == 3){
            color = vec4(1,1,1,1);
        }else{
            color = vec4(0, 0, 0, 0);
        }
    }else{
        color = vec4(1, 0.678, 0.870, 1);
    }

}
