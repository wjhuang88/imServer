package com.hhjt.medicine.im.core;

import com.hhjt.medicine.im.gears.handlers.ImClientHandler;
import com.hhjt.medicine.im.gears.handlers.MessageDecoder;
import com.hhjt.medicine.im.gears.handlers.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created on 2014/12/23.
 *
 * @author Huang Weijie.
 */
public class ImServer {

    private int port;
    private Logger l = LoggerFactory.getLogger(ImServer.class);

    private ImServer(){}

    private ImServer(int port) {
        this.port = port;
    }

    //Singleton instance getter.
    private static ImServer instance = new ImServer();

    public static ImServer getInstance() {
        return instance;
    }

    public ImServer setPort(int port) {
        this.port = port;
        return this;
    }

    public void run() {
        l.info("Server initializing.");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new MessageDecoder(), new MessageEncoder(), new ImClientHandler());
                }
            });
            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            l.info("Server started, socket = /" + InetAddress.getLocalHost().getHostAddress() + ":" + port);

            f.channel().closeFuture().sync();
            l.info("Server closing.");

        } catch (Exception e) {
            l.error(e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            l.info("Server shut down.");
        }
    }

    public static void main(String[] args) throws Exception {

//        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession session = sqlSessionFactory.openSession();
//
//        User user = new User();
//        user.setId(1);
//        user.setUsername("test1");
//        user.setHashedPassword("111");
//        int rst = session.insert("insert", user);
//        session.commit();
//        session.close();
//        System.out.println(rst);

        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        instance.setPort(port).run();
    }
}
