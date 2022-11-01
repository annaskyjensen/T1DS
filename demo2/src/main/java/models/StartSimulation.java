package models;

import org.apache.commons.lang3.SystemUtils;
import java.io.File;

public class StartSimulation {

    public static void start(int index){
        String id = Integer.toString(index);
        File simmvp = new File("demo2/src/main/java/models/bin/");
        String abssimmvp = simmvp.getAbsolutePath();
        try{
            if (SystemUtils.IS_OS_WINDOWS == true){
                File simwin = new File("demo2/src/main/java/models/medtronic.bat");
                String absolute = simwin.getAbsolutePath();
                Process process = Runtime.getRuntime().exec(new String[]{absolute,abssimmvp,id, "[]", "[]", "[]", "5", "5", "108"});

            } else {
                File simlin = new File("demo2/src/main/java/models/mvp.sh");
                String absolute = simlin.getAbsolutePath();
                Process process = Runtime.getRuntime().exec(new String[]{absolute, abssimmvp,id,"[]", "[]", "[]", "5", "5", "108" });
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
