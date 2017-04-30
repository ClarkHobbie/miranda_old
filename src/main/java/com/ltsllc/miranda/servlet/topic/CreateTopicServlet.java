package com.ltsllc.miranda.servlet.topic;

import com.ltsllc.miranda.MirandaException;
import com.ltsllc.miranda.Results;
import com.ltsllc.miranda.servlet.MirandaServlet;
import com.ltsllc.miranda.servlet.holder.TopicHolder;
import com.ltsllc.miranda.servlet.objects.RequestObject;
import com.ltsllc.miranda.servlet.objects.ResultObject;
import com.ltsllc.miranda.topics.Topic;
import com.ltsllc.miranda.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Clark on 4/9/2017.
 */
public class CreateTopicServlet extends TopicServlet {
    @Override
    public boolean allowAccess() {
        return getSession().getUser().getCategory() == User.UserTypes.Publisher;
    }

    public ResultObject createResultObject() {
        return new ResultObject();
    }

    public ResultObject basicPerformService(HttpServletRequest req, HttpServletResponse resp,
                                            TopicRequestObject requestObject)
            throws ServletException, IOException, TimeoutException {
        ResultObject resultObject = new ResultObject();
        requestObject.getTopic().setOwner(getSession().getUser().getName());
        Results result = TopicHolder.getInstance().createTopic(requestObject.getTopic());
        resultObject.setResult(result);

        return resultObject;
    }
}
