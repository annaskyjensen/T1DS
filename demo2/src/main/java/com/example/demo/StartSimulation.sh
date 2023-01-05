path1=find "demo2/src/main/java/models/bin"
path2=find "demo2/src/main/java/PatientFiles/$1.txt"
cd path1 || exit
matlab -nodisplay -nosplash -nodesktop -r "mvpsim($1,$2,"+path2+")"

