package models;

import org.apache.commons.lang3.SystemUtils;
import java.io.File;

public class StartSimulation {

    public static void start(int index, int ssbg){
        String id = Integer.toString(index);
        String bg = Integer.toString(ssbg);
        File simmvp = new File("demo2/src/main/java/models/bin/");
        String abssimmvp = simmvp.getAbsolutePath();
        try{
            if (SystemUtils.IS_OS_WINDOWS == true){
                File simwin = new File("demo2/src/main/java/models/medtronic.bat");
                String absolute = simwin.getAbsolutePath();
                Process process = Runtime.getRuntime().exec(new String[]{absolute,abssimmvp,id, "5", "5", bg});


            } else {
                File simlin = new File("demo2/src/main/java/models/mvp.sh");
                String absolute = simlin.getAbsolutePath();
                Process process = Runtime.getRuntime().exec(new String[]{absolute, abssimmvp,id, "5", "5", bg });
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
