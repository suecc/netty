package com.netty.server.handler;

import java.nio.charset.Charset;
import java.util.BitSet;

import com.netty.server.Server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;

/**  
 * 2016年5月9日11:10:54
 * @author Sue  
 * @version 1.0   
 * @since JDK 1.8.0_60  
 * ServerHandler.java
 */
public class ServerHandler extends ChannelHandlerAdapter  {

	private int counter=0;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

		
		System.out.println("服务端: 开始读取数据");
//		ByteBuf buf = (ByteBuf)msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		buf.toString(Charset.forName("utf8"));
//		String msgStr = new String(req, "UTF-8");
		
		String msgStr = (String)msg;//客户端 channel.pipeline().addLast("stringDecode", new StringDecoder());
		
		System.out.println("服务端: "+msgStr);
		System.out.println("服务端: 验证服消息 ...."+ (validateMsg(msgStr) ?"验证成功！":"消息验证失败，发生拆粘包问题!"));
		System.out.println("counter :" + Server.counter.addAndGet(1));
				
//		System.out.println("服务端: "+msgStr+" counter :"+ ++counter);
		
//		ByteBuf reBuf = Unpooled.copiedBuffer("success receive!".getBytes());
//		ctx.write(reBuf);
		
	}
	
	private boolean validateMsg(String msg){
		
		return msg !=null && ("丘以书").equals(msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
		System.out.println("服务端: channelReadComplete， 共读取 "+Server.counter.get()+" 条数据");
		ctx.flush();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		System.out.println(cause.toString());
		ctx.close();
	}
	
	
}
  
