package com.uniquedu.myurlconnecttion;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void doPost() {
        StringBuffer buffer = null;
        try {
            URL url = new URL("http://192.168.0.44:8080/MyServiceTest/MyTestServlet");
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("POST");
            //设置编码格式
            //设置接受的数据类型
            httpConnection.setRequestProperty("Accept-Charset", "utf-8");
            //设置可以序列化的java对象
            httpConnection.setRequestProperty("Context-Type", "application/x-www-form-urlencoded");

            // 设置可以读取服务器返回的内容
            //httpConnection.setDoInput(true);
            //设置服务器接收客户端串入的内容。
            httpConnection.setDoOutput(true);
            //设置不可已接受缓存内容。
            httpConnection.setUseCaches(false);
            //设置传入的参数内容
            String params = "username=I want you";
            //提交数据
            httpConnection.getOutputStream().write(params.getBytes());
            int code = httpConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                System.out.println("获得的连接状态码是：" + code);

                //从服务器读数据
                InputStream is = httpConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine();
                buffer = new StringBuffer();
                while (line != null) {
                    buffer.append(line);
                    line = br.readLine();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doGet() {
        StringBuffer buffer = null;
        try {
            URL url = new URL("http://192.168.0.44:8080/MyServiceTest/MyTestServlet?username=Ineedyou");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpurlconn = (HttpURLConnection) urlConnection;
            httpurlconn.setRequestMethod("GET");
            //设置编码格式
            //设置接受的数据类型
            httpurlconn.setRequestProperty("Accept-Charset", "utf-8");
            //设置可以序列化的java对象
            httpurlconn.setRequestProperty("Context-Type", "application/x-www-form-urlencoded");

            int code = httpurlconn.getResponseCode();
            Log.d("data", "获得的状态码是：" + code);
            if (code == HttpsURLConnection.HTTP_OK) {
                InputStream is = httpurlconn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine();
                buffer = new StringBuffer();
                while (line != null) {
                    buffer.append(line);
                    line = br.readLine();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void doHttpClientGet() {
        StringBuffer buffer = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://192.168.0.44:8080/MyServiceTest/MyTestServlet?username=hahahahhaha");
        try {

            get.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            //执行get方法获得服务器返回的所有数据
            HttpResponse response = client.execute(get);
            //HttpClient获得服务器返回的表头。
            StatusLine statusLine = response.getStatusLine();
            //获得状态码
            int code = statusLine.getStatusCode();
            if (code == HttpURLConnection.HTTP_OK) {
                //得到数据的实体。
                HttpEntity entity = response.getEntity();
                //得到数据的输入流。
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine();
                buffer = new StringBuffer();
                while (line != null) {
                    System.out.println(line);
                    buffer.append(line);
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doHttpClientPost() {
        StringBuffer buffer = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.0.44:8080/MyServiceTest/MyTestServlet");
        //设置传入的数据
        NameValuePair param = new BasicNameValuePair("username", "i need you");
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(param);
        try {
            //设置传递的参数格式
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            //执行get方法获得服务器返回的所有数据
            HttpResponse response = client.execute(post);
            //HttpClient获得服务器返回的表头。
            StatusLine statusLine = response.getStatusLine();
            //获得状态码
            int code = statusLine.getStatusCode();
            if (code == HttpURLConnection.HTTP_OK) {
                //得到数据的实体。
                HttpEntity entity = response.getEntity();
                //得到数据的输入流。
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine();
                buffer = new StringBuffer();
                while (line != null) {
                    System.out.println(line);
                    buffer.append(line);
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doOkHttpGet() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//创建一个Request
        final Request request = new Request.Builder()
                .url("https://github.com/Android")
                .build();
//new call
        Call call = mOkHttpClient.newCall(request);
//请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                //String htmlStr =  response.body().string();
            }
        });
    }

    private void doOkHttpPost() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("username", "张鸿洋");
        Request request = new Request.Builder()
                .url("")
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }

    private String getJsonStringFromGZIP(HttpResponse response) {
        String jsonString = null;
        try {
            InputStream is = response.getEntity().getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int headerData = getShort(header);
            // Gzip 流 的前两个字节是 0x1f8b
            if (result != -1 && headerData == 0x1f8b) {
//                LogUtil.d("HttpTask", " use GZIPInputStream  ");
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            InputStreamReader reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            StringBuffer sb = new StringBuffer();
            while ((readSize = reader.read(data)) > 0) {
                sb.append(data, 0, readSize);
            }
            jsonString = sb.toString();
            bis.close();
            reader.close();
        } catch (Exception e) {
        }

        return jsonString;
    }

    private int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    private void doPostFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "balabala.mp4");
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM).addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"mFile\";filename=\"wjd.mp4\""), fileBody)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.103:8080/okHttpServer/fileUpload")
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
            //...
        });
    }
}
