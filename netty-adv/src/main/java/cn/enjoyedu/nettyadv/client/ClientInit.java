package cn.enjoyedu.nettyadv.client;

import cn.enjoyedu.nettyadv.kryocodec.KryoDecoder;
import cn.enjoyedu.nettyadv.kryocodec.KryoEncoder;
import cn.enjoyedu.nettyadv.server.HeartBeatRespHandler;
import cn.enjoyedu.nettyadv.server.LoginAuthRespHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：客户端Handler的初始化
 */
public class ClientInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        /**粘包半包问题*/
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,
                0,2,0,
                2));
        ch.pipeline().addLast(new LengthFieldPrepender(2));

        /**序列化相关*/
        ch.pipeline().addLast(new KryoDecoder());
        ch.pipeline().addLast(new KryoEncoder());

        /**处理心跳超时*/
        ch.pipeline().addLast(new ReadTimeoutHandler(15));

        ch.pipeline().addLast(new LoginAuthReqHandler());
        ch.pipeline().addLast(new HeartBeatReqHandler());
    }
}
