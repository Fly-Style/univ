clear

% Grevil

Source = double(imread('x1.bmp'));
Out = double(imread('y7.bmp'));

Source = Source / 256.0;
Out = Out / 256.0;

b = [];
b = [b; Source(1,:)];
bPLUS = b' /(b * b');
condition1 = 0;
condition2 = 0;

Z = @(A, APL) eye(188, 188) - APL * A;
R = @(APL) APL * (APL');

eps = 0.0001;
for i = 2:size(Source, 1); 
   a = Source(i,:);
   condition1 = a * Z(b, bPLUS)* a';
   temp = b;
   tempPlus = bPLUS;
  
    if condition1 > eps
        m1 =  tempPlus - (Z (temp, tempPlus) * (a') * a * tempPlus) / (a * Z(temp, tempPlus) * a');
        m2 =  Z(temp, tempPlus) * a' / (a * Z(temp,tempPlus) * a');
        m3 = [m1, m2]';
        bPLUS = m3';
        b = [b; Source(i,:)];
 
    else 
        m1 =  tempPlus -  (R (tempPlus) * (a') * a * tempPlus) / (1 + (a * R(tempPlus) * a') );
        m2 =   R(tempPlus) * a' / (1 + (a * R(tempPlus) * a'));
        m3 = [m1, m2]';
        bPLUS = m3';
        b = [b; Source(i,:)];  
   end;
 
end;

transform = Out * bPLUS + eye(120, 140) * (eye(140, 140) - Source * bPLUS)';
Res = transform * Source;

errorRes = sum(sum(abs(Res - Out)))

imwrite((Res), 'result2.bmp', 'bmp');



