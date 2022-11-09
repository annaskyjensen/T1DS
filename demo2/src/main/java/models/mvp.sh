#cd "/Users/anna/Desktop/T1DS/demo2/src/main/java/models/bin" || exit
cd "$1" || exit
/Applications/MATLAB_R2022b.app/bin/matlab -nodisplay -r "mvpsim($2,$3,$4,$5,$6,$7,$8)"
#mvpsim(id,meal,mtime,insulin,ttime,stime,Gs)