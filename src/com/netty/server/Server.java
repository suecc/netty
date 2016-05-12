package com.netty.server;

import sun.misc.Unsafe;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import com.netty.server.handler.BossChannelHandler;
import com.netty.server.handler.ServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**  
 * 2016年5月9日11:03:21

 * @author Sue  
 * @version 1.0   
 * @since JDK 1.8.0_60  
 * Server.java
 *   
 */
public class Server {

	public static AtomicInteger counter = new AtomicInteger(0);
	
	/**
	 * 
	 */
	public void bind(int port){
		
//		Unsafe a = Unsafe.getUnsafe();
		
		
//		ByteBuffer byteBuffer = ByteBuffer.allocate(2);
//		String value = "sue";
//		byteBuffer.put(value.getBytes());
//		//ByteBuffer需要手动操作指针，否则无法正确读取数据
//		byteBuffer.flip();
//		byte[] array = new byte[byteBuffer.remaining()];
//		byteBuffer.get(array);
//		try {
//			
//			System.out.println(new String(array, "UTF-8"));
//			
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .option(ChannelOption.SO_BACKLOG, 1024)
					 .childHandler(new ChildChannelHandler());
//					 .handler(new BossChannelHandler());
						
			//当前线程等待绑定完成
			ChannelFuture future = bootstrap.bind(port);

//			future.addListener(new ChannelFutureListener() {
//				
//				@Override
//				public void operationComplete(ChannelFuture future) throws Exception {
//					
//					System.out.println("bound: "+future.channel().id());s
//					
//				}
//			});
//			
//			System.out.println("channel id: "+future.channel().id());
			
			future.sync();
			
			//当前线程等待通道关闭
			future.channel().closeFuture().sync();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel channel) throws Exception {
				System.out.println("初始化 initChannel");
				
				channel.pipeline().addLast("lineBased", new LineBasedFrameDecoder(1024));
				channel.pipeline().addLast("stringDecoder", new StringDecoder());
				channel.pipeline().addLast(new ServerHandler());
		}
		
	}
	
	
	public static void main(String[] args) {

		int port = 8089;
		
		if(args!=null && args.length>0){
			
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				
			}
		}
		
		new Server().bind(port);
		
	}

}
  
