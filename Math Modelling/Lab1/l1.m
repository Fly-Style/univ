% y(t) = a1 * t^3 + a2 * t^2 + a3 * t 
%      + a4 * sin(2 * Pi * f1 * t) + a6

%На половине графика - один пик  - на отметке 25.f;
input = importdata('lab1.txt');

%plot(input);

Step = 0.01;
Temp = 5.0;
[f] = DFT(input);
fabs = abs(f);
%plot(abs(fft(input)));
f1 = 25.0 / Temp;


g1 = @(x) x * x * x;
g2 = @(x) x * x;
g3 = @(x) x;
g4 = @(x) sin(2.0 * pi * f1 * x);
g5 = @(x) 1;

vec1 = []; vec2 = []; vec3 = []; vec4 = []; vec5 = [];

for i = 0.0:Step:Temp
    vec1 = [vec1, g1(i)];
    vec2 = [vec2, g2(i)];
    vec3 = [vec3, g3(i)];
    vec4 = [vec4, g4(i)];
    vec5 = [vec5, g5(i)];
end;

res = [sum(input .* vec1); sum(input .* vec2); sum(input .* vec3); sum(input .* vec4); sum(input .* vec5)];

mtrx = [sum(vec1 .* vec1) sum(vec1 .* vec2) sum(vec1 .* vec3) sum(vec1 .* vec4) sum(vec1 .* vec5);
        sum(vec2 .* vec1) sum(vec2 .* vec2) sum(vec2 .* vec3) sum(vec2 .* vec4) sum(vec2 .* vec5);
        sum(vec3 .* vec1) sum(vec3 .* vec2) sum(vec3 .* vec3) sum(vec3 .* vec4) sum(vec3 .* vec5);
        sum(vec4 .* vec1) sum(vec4 .* vec2) sum(vec4 .* vec3) sum(vec4 .* vec4) sum(vec4 .* vec5);
        sum(vec5 .* vec1) sum(vec5 .* vec2) sum(vec5 .* vec3) sum(vec5 .* vec4) sum(vec5 .* vec5);
	   ];

divided = mtrx \ res;

resultFunc = @(x) divided(1) * g1(x) + divided(2) * g2(x) + divided(3) * g3(x) + divided(4) * g4(x) + divided(5) * g5(x);

result = [];
for i = 0.0:Step:Temp
    result = [result, resultFunc(i)];
end;

different = 0;
for i = 1:1:500
    different = different + (input(i) - result(i)) ^ 2;
end;

different

plot(0.0:Step:Temp, result, 0.0:Step:Temp, input, '.-'), legend('transformed', 'input');

%disp(f1)

%plot(abs(b));