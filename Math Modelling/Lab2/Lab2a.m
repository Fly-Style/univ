clear

% Mur - Penrose

Source = double(imread('x1.bmp'));
Out = double(imread('y7.bmp'));

Source = Source / 256.0;
Out = Out / 256.0;
filler = [];
for i = 1:1:size(Source, 2)
    filler = [filler, 1];
end
Source = [Source; filler];
eps = 0.0001;

SP = (Source' * Source + eps * eye(188, 188)) \ Source';
%SP = pinv(Source);

b = eye(141, 141) - Source * SP;
A = Out * SP + eye(120, 141) * (eye(141, 141) - Source * SP)';

Res = A * Source;

errorRes = sum(sum(abs(Res - Out)))

imwrite(Res, 'result.bmp', 'bmp');