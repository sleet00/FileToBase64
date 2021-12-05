import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class fileToBase64Test {
    public static void main(String[] args) {
        String imagePath = "D:/IDEA_Worksplace/FileToBase64/img/01.jpeg";
        String base64 = ImageToBase64(imagePath);
        String Base64ToImgPath = "D:/IDEA_Worksplace/FileToBase64/img";
        String ext = "jpg";
        BASE64CodeToBeImage(base64,Base64ToImgPath,ext);
    }

    private static String ImageToBase64(String imgPath){
        byte[] data = null;
        // 读取图片字节数组
        try {
            FileInputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(Objects.requireNonNull(data));
        System.out.println("本地图片转换BAse64:" +
                encoder.encode(Objects.requireNonNull(data)));
        return encode;
    }

    /**
     *
     * @param BASE64str bas64字符串
     * @param path 存储地址
     * @param ext 图片后缀
     * @return 存储地址
     */
    private static String BASE64CodeToBeImage(String BASE64str,String path,String ext){
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //文件名称
        String uploadFileName = UUID.randomUUID().toString() + "."+ext;
        File targetFile = new File(path, uploadFileName);
        BASE64Decoder decoder = new BASE64Decoder();
        try(OutputStream out = new FileOutputStream(targetFile)){
            byte[] b = decoder.decodeBuffer(BASE64str);
            for (int i = 0; i <b.length ; i++) {
                if (b[i] <0) {
                    b[i]+=256;
                }
            }
            out.write(b);
            out.flush();
            return  path+"/"+uploadFileName+"."+ext;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
