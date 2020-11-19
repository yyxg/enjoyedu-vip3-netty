package cn.enjoyedu.nettyadv.server;

import cn.enjoyedu.nettyadv.vo.MessageType;
import cn.enjoyedu.nettyadv.vo.MyHeader;
import cn.enjoyedu.nettyadv.vo.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：登录检查
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

	private final static Log LOG
            = LogFactory.getLog(LoginAuthRespHandler.class);

	//用以检查用户是否重复登录的缓存
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    //用户登录的白名单
    private String[] whitekList = { "127.0.0.1"};

    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
		//TODO
    }

    private MyMessage buildResponse(byte result) {
		MyMessage message = new MyMessage();
		MyHeader myHeader = new MyHeader();
		myHeader.setType(MessageType.LOGIN_RESP.value());
		message.setMyHeader(myHeader);
		message.setBody(result);
		return message;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
		cause.printStackTrace();
        // 删除缓存
		nodeCheck.remove(ctx.channel().remoteAddress().toString());
		ctx.close();
		ctx.fireExceptionCaught(cause);
    }
}
