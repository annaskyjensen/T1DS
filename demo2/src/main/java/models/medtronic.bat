@ECHO OFF

matlab -noFigureWindows -nosplash -nodesktop -nodisplay -r "try; cd('%1'); mvpsim(%2,%3,%4); catch; end; quit"
PAUSE
