import com.bt.GObj;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class exc {
    public static void main(String[] args) throws IOException {
//        String s = Trans("en", "vi", "How old are you?");
//        System.out.println(s);
        xxx();
    }

    static void xxx() {
        String path = "D:\\Workspaces\\Java\\Original";
        String f1 = "Resources.properties";
        String f2 = "JSResources.properties";
        Path start = Paths.get(path);
        try (Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE)) {
            List<Path> collect = stream
                    .map(Path::toAbsolutePath)
                    .sorted()
                    .collect(Collectors.toList());
            int i = 1;
            for (Path p : collect) {
                if (Files.isDirectory(p))
                    continue;
                else {
                    String fn = p.getFileName().toString();
                    if (fn.equals(f1) || fn.equals(f2)) {
                        //xu ly decode
                        i++;
                        process(p, false);
                    }
                }
            }
            System.out.println("Done total: " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String Trans(String src, String tar, String s) {
        String u = String.format("https://clients5.google.com/translate_a/t?client=dict-chrome-ex&sl=%s&tl=%s&q=%s", src, tar, s);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(u)
                .method("GET", null)
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .build();
        try {
            Response response = client.newCall(request).execute();
            int code = response.code();
            if (code == 200) {
                String sss = response.body().string();
                System.out.println(sss);
                GObj obj = new Gson().fromJson(sss, GObj.class);
                if (obj != null && obj.getSentences().size() > 0)
                    return obj.getSentences().get(0).getTrans();
                else return s;
            } else {
                System.out.println(s + " - Trans err: " + code);
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return s;
        }
    }

    static void process(Path p, Boolean encode) {
        String ext = "";
        if (encode)
            ext = "en";
        else
            ext = "de";
        try {
            List<String> lst = Files.readAllLines(p);
            List<String> lstResult = new ArrayList<>();
            for (String s : lst) {
                if (!s.equals("") && !s.contains("#")) {
                    String[] arr = s.split("=");
                    if (arr.length != 2)
                        continue;
                    StringBuilder sb = new StringBuilder();
                    sb.append(arr[0]);
                    sb.append("=");
                    if (encode) {
                        sb.append(StringEscapeUtils.escapeJava(arr[1]));
                    } else {
                        String uns = StringEscapeUtils.unescapeJava(arr[1]);
                        sb.append(Trans("en", "vi", uns));
                    }
                    lstResult.add(sb.toString());
                } else {
                    lstResult.add(s);
                }
            }
            File fs = new File(p + "." + ext);
            Files.write(fs.toPath(), lstResult);
            System.out.println("File result: " + fs.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Errr <<<<<<<<<");
            e.printStackTrace();
        }
    }

    static void ci(String[] args) {
        if (args.length != 2) {
            System.out.println("Syntax: java -jar xxx.jar [file] [mode]");
            System.out.println("Mode: 1: encode, !1: decode");
            return;
        }

        File f = new File(args[0]);

        boolean encode = args[1].equals("1");

        process(f.toPath(), encode);
    }
}
