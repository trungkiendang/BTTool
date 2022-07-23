import com.bt.GObj;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class exc {
    public static void main(String[] args) throws IOException {
        String s = Trans("en", "vi", "authored 1 year ago");
        System.out.println(s);

       // prepareCache();
       // xxx();
    }

    static void xxx() {
        String path = "D:\\3. Workspace\\wso2\\Original\\carbon-multitenancy-4.8.1";
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
                if (!Files.isDirectory(p)) {
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

    static Hashtable<String, String> CACHE = new Hashtable<>();
    static File cacehFile = new File("trans.cache");

    static void prepareCache() {
        if (!cacehFile.exists()) {
            try {
                cacehFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            List<String> lst = Files.readAllLines(cacehFile.toPath());
            for (String s : lst) {
                String[] arr = s.split("=");
                if (arr.length == 2)
                    CACHE.put(arr[0], arr[1]);
            }
            System.out.println("Cache size: " + CACHE.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String Trans(String src, String tar, String s) {

        //Xu ly cache
        if (CACHE.containsKey(s))
            return CACHE.get(s);

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
                String sss = Objects.requireNonNull(response.body()).string();
                try {
                    GObj obj = new Gson().fromJson(sss, GObj.class);
                    if (obj != null && obj.getSentences().size() > 0) {
                        String trans = obj.getSentences().get(0).getTrans();

                        //Luu vao cache memory
                        CACHE.put(s, trans);
                        System.out.println("Cache size: " + CACHE.size());
                        //Luu xuong file, nho no bi tat dot ngot
                        Files.write(cacehFile.toPath(), (s + "=" + trans + "\r\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

                        return trans;
                    } else return s;
                }
                catch (Exception e) {
                    return JsonParser.parseString(sss).getAsJsonArray().get(0).toString();
                }

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
                        String uns = StringEscapeUtils.unescapeJava(arr[1]).trim();
                        String trans = Trans("en", "vi", uns).trim();

                        sb.append(trans);
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
