i=1;
while i==1
    fid = fopen('testfile.txt');
    if fseek(fid, 1, 'bof') ~= -1
        disp("non-empty");
    else
        disp("empty");
    end  
    fclose(fid);
    pause(3);
end