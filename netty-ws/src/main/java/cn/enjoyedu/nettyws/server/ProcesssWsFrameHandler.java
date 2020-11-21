package cn.enjoyedu.nettyws.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：对websocket的数据进行处理
 */
public class ProcesssWsFrameHandler
        extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final ChannelGroup group;

    public ProcesssWsFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    private static final Logger logger
            = LoggerFactory.getLogger(ProcesssWsFrameHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                WebSocketFrame frame) throws Exception {

        /**
         * 判断是否是文本帧
         */
        if(frame instanceof TextWebSocketFrame){
            String request = ((TextWebSocketFrame)frame).text();
            ctx.channel().writeAndFlush(
                    new TextWebSocketFrame(request.toUpperCase(Locale.CHINA)));
            /**
             * 群发，group：通道的集合
             */
            group.writeAndFlush(
                    new TextWebSocketFrame(
                            "Client"+ctx.channel()+"say:"+request.toUpperCase(Locale.CHINA)
                    ));

        }else{
            throw new UnsupportedOperationException("unsupport data frame");
        }
    }

    /**重写 userEventTriggered()方法以处理自定义事件*/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx,
                                   Object evt) throws Exception {
        /**检查事件类型，如果是ws握手成功事件，群发通知*/
       if(evt == WebSocketServerProtocolHandler.
               ServerHandshakeStateEvent.HANDSHAKE_COMPLETE){
           group.writeAndFlush(
                   new TextWebSocketFrame("Client"+ctx.channel()+" joined"));
           group.add(ctx.channel());

       }
    }
}
