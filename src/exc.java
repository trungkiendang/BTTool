import com.mgnt.utils.StringUnicodeEncoderDecoder;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class exc {
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Syntax: java -jar xxx.jar [file] [mode]");
            System.out.println("Mode: 1: encode, !1: decode");
            return;
        }

        File f = new File(args[0]);

        boolean encode = args[1].equals("1");
        String ext = "";
        if (encode)
            ext = "en";
        else
            ext = "de";
        try {
            List<String> lst = Files.readAllLines(f.toPath());
            List<String> lstResult = new ArrayList<>();
            for (String s : lst) {
                if (!s.equals("") && !s.contains("#")) {
                    String[] arr = s.split("=");
                    StringBuilder sb = new StringBuilder();
                    sb.append(arr[0]);
                    sb.append("=");
                    if (encode) {
                        sb.append(StringEscapeUtils.escapeJava(arr[1]));
                    } else {
                        sb.append(StringEscapeUtils.unescapeJava(arr[1]));
                    }
                    lstResult.add(sb.toString());
                }
            }
            File fs = new File(f.getAbsolutePath() + "." + ext);
            Files.write(fs.toPath(), lstResult);
            System.out.println("File result: " + fs.getAbsolutePath());
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Errr <<<<<<<<<");
            e.printStackTrace();
        }
    }
}
