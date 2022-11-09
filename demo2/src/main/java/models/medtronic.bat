@ECHO OFF
"C:\Program Files\MATLAB\R2022b\bin\matlab.exe" -noFigureWindows -nosplash -nodesktop -nodisplay -r "try; cd('%1'); mvpsim(%2,%3,%4,%5); catch; end; quit"
PAUSE
