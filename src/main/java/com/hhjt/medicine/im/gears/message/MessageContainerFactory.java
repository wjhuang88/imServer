package com.hhjt.medicine.im.gears.message;

import com.hhjt.medicine.im.gears.message.impl.*;

/**
 * Created by GHuang on 15/1/5.
 */
public abstract class MessageContainerFactory {

    private enum CommandEnum {
        ERRO,   //错误
        CORQ,   //连接申请
        COAS,   //连接申请回复
        APRQ,   //会话申请
        APAS,   //会话申请回复
        EXRQ,   //会话退出申请
        EXAS,   //会话退出申请回复
        RCRQ,   //会话重连
        JORQ,   //会话要求
        JOAS,   //会话要求回复
        SEND,   //会话发送
        STAT    //状态发送
    }

    public static AbMessageContainer getMessageContainer(byte[] source) {

        String cmd = String.valueOf((char) source[6]) + (char) source[7] + (char) source[8] + (char) source[9];
        CommandEnum cmdEnum = CommandEnum.valueOf(cmd);

        switch (cmdEnum) {
            case CORQ:
                return new ConnectRequestMessage(source);
            case COAS:
                return new ConnectResponseMessage(source);
            case APRQ:
                return new SessionRequestMessage(source);
            case APAS:
                return new SessionResponseMessage(source);
            case EXRQ:
                return new SessionQuitRequestMessage(source);
            case EXAS:
                return new SessionQuitResponseMessage(source);
            case RCRQ:
                return new SessionReconnectMessage(source);
            case JORQ:
                return new SessionConfirmRequestMessage(source);
            case JOAS:
                return new SessionConfirmResponseMessage(source);
            case SEND:
                return new ContextSendMessage(source);
            case STAT:
                return new StatusSendMessage(source);
            default:
                return null;
        }
    }

    public static AbMessageContainer getMessageContainer(String source) {
        return getMessageContainer(source.getBytes());
    }
}
