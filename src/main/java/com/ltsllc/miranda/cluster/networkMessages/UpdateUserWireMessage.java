package com.ltsllc.miranda.cluster.networkMessages;

import com.ltsllc.miranda.Message;
import com.ltsllc.miranda.node.networkMessages.WireMessage;
import com.ltsllc.miranda.servlet.objects.UserObject;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Clark on 4/13/2017.
 */
public class UpdateUserWireMessage extends WireMessage {
    private UserObject userObject;

    public UserObject getUserObject() {
        return userObject;
    }

    public UpdateUserWireMessage (UserObject userObject) {
        super(WireSubjects.UpdateUser);

        this.userObject = userObject;
    }
}
