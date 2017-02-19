package com.ltsllc.miranda.messagesFile;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.file.*;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Clark on 1/8/2017.
 */
public class SystemMessages extends Directory {
    public SystemMessages (String directory, BlockingQueue<Message> writerQueue)
    {
        super(directory, writerQueue);

        SystemMessagesReadyState readyState = new SystemMessagesReadyState(this);
        setCurrentState(readyState);
    }

    @Override
    public boolean isFileOfInterest(String filename) {
        return filename.endsWith("msg");
    }

    @Override
    public MirandaFile createMirandaFile(String filename) {
        return new MessagesFile(filename, getWriterQueue());
    }
}
