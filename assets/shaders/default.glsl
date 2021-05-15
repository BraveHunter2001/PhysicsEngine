#type vertex
#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;


uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fragColor;
out vec3 fragCoord;


void main() {
    fragColor = aColor;
    fragCoord = aPos;
    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core
    uniform float uTime;
    uniform vec2 resolution;
    in vec4 fragColor;
    in vec3 fragCoord;
    out vec4 color;

void main() {
    vec2 uv = fragCoord.xy/ resolution.xy;
    vec3 col = 0.5 + 0.5*cos(uTime + uv.xyx + vec3(0,2,4));
    color = vec4(col, 1.0);
}