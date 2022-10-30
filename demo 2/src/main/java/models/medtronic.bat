@ECHO OFF
matlab -noFigureWindows -r "try; cd('C:\Users\Bruger\Downloads\springboot-mosquitto-master\T1DS\demo 2\src\main\java\models\bin'); mvpsim(%1,%2,%3,%4,%5,%6,%7); catch; end; quit"
PAUSE
PAUSE