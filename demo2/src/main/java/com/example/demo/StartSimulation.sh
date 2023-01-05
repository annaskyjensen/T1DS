simPath=find "demo2/src/main/java/models/bin"
PFPath=find "demo2/src/main/java/$1.txt"
cd simPath || exit
matlab -nodisplay -nosplash -nodesktop -r "mvpsim($1,$2,"+PFPath+")"


