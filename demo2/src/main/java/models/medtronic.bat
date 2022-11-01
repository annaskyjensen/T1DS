@ECHO OFF

matlab -noFigureWindows -nosplash -nodesktop -nodisplay -r "try; cd('%1'); mvpsim(%2,%3,%4,%5,%6,%7,%8); catch; end; quit"
PAUSE
