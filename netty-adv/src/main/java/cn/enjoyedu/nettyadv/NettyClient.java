package cn.enjoyedu.nettyadv;

import cn.enjoyedu.nettyadv.client.ClientInit;
import cn.enjoyedu.nettyadv.vo.MessageType;
import cn.enjoyedu.nettyadv.vo.MyHeader;
import cn.enjoyedu.nettyadv.vo.MyMessage;
import cn.enjoyedu.nettyadv.vo.NettyConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：Netty客户端的主入口
 */
public class NettyClient implements Runnable{

    private static final Log LOG = LogFactory.getLog(NettyClient.class);

    private ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(1);
    private Channel channel;
    private EventLoopGroup group = new NioEventLoopGroup();

    /*是否用户主动关闭连接的标志值*/
    private volatile boolean userClose = false;
    /*连接是否成功关闭的标志值*/
    private volatile boolean connected = false;


    public boolean isConnected() {
        return connected;
    }

    /*连接服务器*/
    //TODO

    @Override
    public void run() {
        //TODO
    }

    /*------------测试NettyClient--------------------------*/
    public static void main(String[] args) throws Exception {
//        NettyClient nettyClient = new NettyClient();
//        nettyClient.connect(NettyConstant.REMOTE_PORT
//                , NettyConstant.REMOTE_IP);
    }

    /*------------以下方法供业务方使用--------------------------*/
    public void send(String message) {
        if(channel==null||!channel.isActive()){
            throw new IllegalStateException("和服务器还未未建立起有效连接！" +
                    "请稍后再试！！");
        }
        MyMessage msg = new MyMessage();
        MyHeader myHeader = new MyHeader();
        myHeader.setType(MessageType.SERVICE_REQ.value());
        msg.setMyHeader(myHeader);
        msg.setBody(message);
        channel.writeAndFlush(msg);
    }

    public void close() {
        userClose = true;
        channel.close();
    }


}