package com.example.netty.demo06;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 *
 */
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    // 重写 channelRead0() ,可以被所有的接收到的 FullHttpRequest 调用
    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception { //1
        // 检查如果接下来的响应是预期的，就写入
        if (HttpUtil.is100ContinueExpected(request)) {
            send100Continue(ctx); //2
        }

        // 新建 FullHttpResponse,用于对请求的响应
        FullHttpResponse response = new DefaultFullHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK); //3
        // 生成响应的内容，将它写入 payload
        response.content().writeBytes(getContent().getBytes(CharsetUtil.UTF_8));  //4
        // 设置头文件，这样客户端就能知道如何与 响应的 payload 交互
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");  //5

        boolean keepAlive = HttpHeaders.isKeepAlive(request);

        // 检查请求设置是否启用了 keepalive;如果是这样, 将标题设置为符合HTTP RFC
        if (keepAlive) {  //6
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        // 写响应给客户端，并获取到 Future 的引用，用于写完成时，获取到通知
        ChannelFuture future = ctx.writeAndFlush(response);  //7

        if (!keepAlive) {
            // 如果响应不是 keepalive，在写完成时关闭连接
            future.addListener (ChannelFutureListener.CLOSE); //8
        }
    }

    // 返回内容作为响应的 payload
    protected String getContent() {  //9
        return "This content is transmitted via HTTP\r\n";
    }

    // Helper 方法生成了100 持续的响应，并写回给客户端
    private static void send100Continue(ChannelHandlerContext ctx) {  //10
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    // 若执行阶段抛出异常，则关闭管道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  //11
        cause.printStackTrace();
        ctx.close();
    }
}