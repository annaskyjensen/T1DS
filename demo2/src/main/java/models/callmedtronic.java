package models;

public class callmedtronic {

    public static void main(String[] args) {
        try {

            // print a message
            System.out.println("Executing notepad.exe");

            // create a process and execute notepad.exe
            Process process = Runtime.getRuntime().exec("C:/Users/Bruger/Downloads/springboot-mosquitto-master/T1DS/Demo 2/src/main/java/models/medtronic.bat ,2,90,1,10,3,5,108");

            // print another message
            System.out.println("Notepad should now open.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
