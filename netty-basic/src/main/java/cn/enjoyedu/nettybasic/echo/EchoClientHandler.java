package cn.enjoyedu.nettybasic.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /*读取到网络数据后进行业务处理*/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client Accept"+msg.toString(CharsetUtil.UTF_8));
        //ctx.close();
        ctx.close();

    }

    /*channel活跃后，做业务处理*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer(
                "Hello,Netty",CharsetUtil.UTF_8));
    }
}
