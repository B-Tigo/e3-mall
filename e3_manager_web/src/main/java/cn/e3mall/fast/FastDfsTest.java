package cn.e3mall.fast;

import cn.e3mall.common.pojo.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;

public class FastDfsTest {

    @Test
    public void testUpload() throws Exception {
        //创建一个配置文件。文件名任意。内容就是tracker服务器的地址。
        //使用全局对象加载配置文件。
        ClientGlobal.init("E://e3mall/e3mallV1/e3_manager_web/src/main/resources/conf/client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建一个StorageServer的引用，可以是null
        StorageServer storageServer = null;
        //创建一个StorageClient，参数需要TrackerServer和StorageServer
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //使用StorageClient上传文件
        String[] strings = storageClient.upload_file("C://Users/Administrator/Pictures/Saved Pictures/无标题1.png", "jpg", null);
        for (String string:strings) {
            System.out.println(string);
        }
    }

    @Test
    public  void testFastDFSClient() throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("E://e3mall/e3mallV1/e3_manager_web/src/main/resources/conf/client.conf");
        String string = fastDFSClient.uploadFile("C://Users/Administrator/Pictures/Saved Pictures/微信图片_20190422222921.png");
        System.out.println(string);
    }
}
