package com.ltsllc.miranda.node;

import com.ltsllc.miranda.Message;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Clark on 2/8/2017.
 */

/**
 * Indicates that the sender has changed.
 */
public class NodeUpdatedMessage extends Message {
    private NodeElement oldNode;
    private NodeElement newNode;

    public NodeUpdatedMessage (BlockingQueue<Message> senderQueue, Object sender, NodeElement oldNode, NodeElement newNode) {
        super(Subjects.NodeUpdated, senderQueue, sender);

        this.oldNode = oldNode;
        this.newNode = newNode;
    }

    public NodeElement getNewNode() {
        return newNode;
    }

    public NodeElement getOldNode() {

        return oldNode;
    }
}
