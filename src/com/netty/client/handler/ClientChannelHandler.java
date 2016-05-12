package com.netty.client.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.netty.client.NettyClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**  
 * ����ʱ�䣺2016��2��1�� ����8:29:58  
 * ��Ŀ���ƣ�  
 * @author Sue  
 * @version 1.0   
 * @since JDK 1.8.0_60  
 * �ļ����ƣ�ClientChannelHandler.java
 * ��˵����  
 */
public class ClientChannelHandler extends ChannelHandlerAdapter {

	public void channelActive(ChannelHandlerContext ctx) {
		
		ExecutorService pool = Executors.newFixedThreadPool(1);
		
		System.out.println("客户端: channelActive");
		
		String msg = NettyClient.msg;
		
		pool.submit(new Runnable() {
			
			boolean stop = false;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!stop){
					for(int i=0;i<100;i++){
						ByteBuf buf = Unpooled.buffer(msg.getBytes().length);
						buf.writeBytes(msg.getBytes());
						ctx.writeAndFlush(buf);
					}
					try {
						Thread.currentThread().sleep(5000);
					} catch (InterruptedException e) {
						stop = true;
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
				}
			}
		});
		
		
	}
	
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception{
		
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		String msgStr = new String(req, "UTF-8");
//		System.out.println("客户端: "+msgStr);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		System.out.println("客户端: "+cause.getMessage());
		ctx.close();
	}
	
}
  
