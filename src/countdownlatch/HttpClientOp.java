package countdownlatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * 简要http工具类
 * @author 夸克
 * @date 11/10/2018 10:37
 */
public class HttpClientOp {

    /**
     * Get方法
     *
     * @param httpUrl 请求的url
     * @return
     */
    public static String doGet(String httpUrl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式: get
            connection.setRequestMethod("GET");
            // 设置连接主服务器的超时时间 15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间 6000毫秒
            connection.setReadTimeout(6000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuilder stringBuilder = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    stringBuilder.append(temp);
                    stringBuilder.append("\r\n");
                }
                result = stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(connection)) {
                // 关闭远程连接
                connection.disconnect();
            }
        }
        return result;
    }

//    public static String doPost(String httpUrl, String param) {
//
//    }
}
