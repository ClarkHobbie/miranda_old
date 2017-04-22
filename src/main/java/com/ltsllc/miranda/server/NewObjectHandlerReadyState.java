package com.ltsllc.miranda.server;

import com.ltsllc.miranda.Consumer;
import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.file.Perishable;
import com.ltsllc.miranda.file.SingleFile;
import com.ltsllc.miranda.http.HttpPostMessage;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;

/**
 * Created by Clark on 2/18/2017.
 */
abstract public class NewObjectHandlerReadyState<T extends SingleFile, E extends Perishable, W extends NewObjectPostHandler> extends State {
    private static Logger logger = Logger.getLogger(NewObjectHandlerReadyState.class);

    abstract public Type getBasicType();

    private W handler;
    private T file;

    public NewObjectHandlerReadyState (Consumer consumer, T file, W handler) {
        super(consumer);

        this.file = file;
        this.handler = handler;
    }

    public T getFile() {
        return file;
    }

    /*
    public State processHttpPostMessage (HttpPostMessage httpPostMessage) {
        if (httpPostMessage.getChannelHandlerContext().isRemoved()) {
            logger.info("ChannelHandlerContext.isRemoved is returning true");
        }

        State nextState = this;

        Type basicType = getBasicType();
        String json = httpPostMessage.getContent();
        E newObject = (E) getHandler().decodeContent(basicType, json);
        DefaultHttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CREATED);
        if (!getFile().contains(newObject)) {
            getFile().add(newObject);
        } else {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONFLICT);
        }

        httpPostMessage.getChannelHandlerContext().writeAndFlush(response);
        httpPostMessage.getChannelHandlerContext().close();

        return nextState;
    }
    */

    public W getHandler() {
        return handler;
    }

    @Override
    public State processMessage(Message message) {
        State nextState = getContainer().getCurrentState();

        switch (message.getSubject()) {
            /*
            case HttpPost: {
                HttpPostMessage httpPostMessage = (HttpPostMessage) message;
                nextState = processHttpPostMessage(httpPostMessage);
                break;
            }
            */

            default:
                nextState = super.processMessage(message);
                break;
        }

        return nextState;
    }
}
