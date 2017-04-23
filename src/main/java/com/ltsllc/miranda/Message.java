package com.ltsllc.miranda;

import com.google.gson.Gson;
import com.ltsllc.miranda.file.Perishable;
import com.ltsllc.miranda.miranda.Miranda;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Clark on 12/30/2016.
 */
public class Message implements Perishable {
    public enum Subjects {
        Auction,
        AddObjects,
        AddSubscriber,
        AddSession,
        Ballot,
        Closed,
        ClusterFile,
        ClusterFileChanged,
        ClusterHealthCheck,
        ClusterHealthCheckUpdate,
        Connect,
        ConnectFailed,
        ConnectSucceeded,
        ConnectionError,
        ConnectTo,
        ConnectionClosed,
        CreateSession,
        CreateSessionResponse,
        CreateSubscription,
        CreateSubscriptionResponse,
        CreateTopic,
        CreateTopicResponse,
        CreateUser,
        CreateUserResponse,
        DecrementPanicCount,
        DeleteTopic,
        DeleteTopicResponse,
        DeleteUser,
        DeleteUserResponse,
        DeleteSubscription,
        DeleteSubscriptionResponse,
        Disconnect,
        Disconnected,
        DoneSynchronizing,
        DropNode,
        DuplicateUser,
        Election,
        Expired,
        FileChanged,
        FileLoaded,
        GarbageCollection,
        GetDeliveries,
        GetFile,
        GetSubscriptionsFile,
        GetClusterFile,
        GetFileResponse,
        GetSession,
        GetSessionResponse,
        GetStatus,
        GetStatusResponse,
        GetSystemMessages,
        GetTopic,
        GetTopicResponse,
        GetTopics,
        GetTopicsResponse,
        GetTopicsFile,
        GetSubcription,
        GetSubscriptionResponse,
        GetSubscriptions,
        GetSubscriptionsResponse,
        GetUser,
        GetUsers,
        GetUsersResponse,
        GetUserResponse,
        GetUsersFile,
        GetVersions,
        GetVersion,
        HealthCheck,
        HealthCheckUpdate,
        HttpPost,
        Join,
        JoinSuccess,
        Listen,
        Load,
        LoadResponse,
        Login,
        LoginResponse,
        NetworkError,
        NewNode,
        NewUser,
        NewTopic,
        NewTopicResponse,
        NewClusterFile,
        NewConnection,
        NetworkMessage,
        NewDelivery,
        NewMessage,
        NewNodeElement,
        NewProperties,
        NewSubscription,
        NoConnection,
        NodeAdded,
        NodesLoaded,
        NodeStopped,
        NodesUpdated,
        NodeUpdated,
        Notification,
        OwnerQuery,
        OwnerQueryResponse,
        Panic,
        RemoveObjects,
        RemoveSubscriber,
        RemoteVersion,
        Retry,
        Results,
        ScheduleOnce,
        SchedulePeriodic,
        SessionsExpired,
        SendError,
        SendMessage,
        SendNetworkMessage,
        Shutdown,
        ShutdownResponse,
        SetupServlets,
        StartHttpServer,
        Starting,
        Stop,
        Synchronize,
        Timeout,
        UnknownHandle,
        UnwatchFile,
        UpdateObjects,
        UpdateTopic,
        UpdateTopicResponse,
        UpdateUser,
        UpdateUserResponse,
        UpdateSubscription,
        UpdateSubscriptionResponse,
        UserCreated,
        UserAdded,
        UserUpdated,
        UserDeleted,
        Version,
        Versions,
        Watch,
        Write,
        WriteSucceeded,
        WriteFailed,
        Error
    }

    private static Gson ourGson = new Gson();
    private static Logger logger = Logger.getLogger(Message.class);

    private Subjects subject;
    private BlockingQueue<Message> sender;
    private Object senderObject;
    private Exception where;

    public BlockingQueue<Message> getSender () {
        return sender;
    }

    public Subjects getSubject() {
        return subject;
    }

    public Object getSenderObject() { return senderObject; }

    public Exception getWhere() {
        return where;
    }


    public Message (Subjects subject, BlockingQueue<Message> sender, Object senderObject) {
        this.subject = subject;
        this.sender = sender;
        this.senderObject = senderObject;

        this.where = new Exception();
    }

    public void respond (Message m) throws InterruptedException {
        getSender().put(m);
    }

    public boolean expired(long time) {
        return false;
    }


    public String toJson() {
        return ourGson.toJson(this);
    }

    public void reply (Message message) {
        try {
            getSender().put(message);
        } catch (InterruptedException e) {
            Panic panic = new Panic("Interrupted trying to send reply.", e, Panic.Reasons.ExceptionSendingMessage);
            Miranda.getInstance().panic(panic);
        }
    }

    public boolean equals (Object o) {
        if (null == o || !(o instanceof Message))
            return false;

        Message other = (Message) o;

        return getSubject().equals(other.getSubject())
                && getSender() == other.getSender()
                && getSenderObject() == other.getSenderObject();
    }

    public void setSender (BlockingQueue<Message> queue) {
        this.sender = queue;
    }

    public void setSenderObject (Object object) {
        this.senderObject = object;
    }
}
