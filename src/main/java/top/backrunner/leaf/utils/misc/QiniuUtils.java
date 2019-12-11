package top.backrunner.leaf.utils.misc;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import top.backrunner.leaf.LeafApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QiniuUtils {

    private static String access_key;
    private static String access_secret;
    private static String bucket_icon;
    private static String bucket_app;
    private static String prefix_app;
    private static String prefix_icon;

    // qiniu objects
    private static Auth auth;
    private static Configuration cfg = new Configuration(Region.autoRegion());
    private static UploadManager uploadManager = new UploadManager(cfg);

    private static String getUpToken(String bucketName){
        return QiniuUtils.auth.uploadToken(bucketName);
    }

    public static void init() throws IOException {
        Properties properties = new Properties();
        InputStream in = LeafApplication.class.getClassLoader().getResourceAsStream("qiniu/config.properties");
        properties.load(in);
        QiniuUtils.access_key = properties.getProperty("qiniu.appkey");
        QiniuUtils.access_secret = properties.getProperty("qiniu.appsecret");
        QiniuUtils.bucket_icon = properties.getProperty("qiniu.appicon.bucket");
        // bucket 设置
        QiniuUtils.bucket_app = properties.getProperty("qiniu.app.bucket");
        QiniuUtils.prefix_icon = properties.getProperty("qiniu.appicon.prefix");
        QiniuUtils.prefix_app = properties.getProperty("qiniu.app.prefix");
        // 创建 auth
        QiniuUtils.auth = Auth.create(QiniuUtils.access_key, QiniuUtils.access_secret);
    }

    public static String upload(InputStream inputStream, String fileName){
        try {
            Response res = uploadManager.put(inputStream, QiniuUtils.prefix_icon + fileName, getUpToken(QiniuUtils.bucket_icon), null, null);
            return res.bodyString();
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        }
    }
}
