% Discrete Fourier Transform
function [f] = DFT(input)
    
    sz = size(input);
    f = zeros(sz);
    for l = 1:1:500
        for j = 1:1:500
            f(l) = f(l) + input(j) * exp(-2.0 * pi * 1i * (j-1) * (l-1) / 500);
        end;
    end;
end
    

