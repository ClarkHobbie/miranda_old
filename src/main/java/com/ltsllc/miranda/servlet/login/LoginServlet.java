/*
 * Copyright 2017 Long Term Software LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ltsllc.miranda.servlet.login;

import com.ltsllc.miranda.EncryptedMessage;
import com.ltsllc.miranda.MirandaException;
import com.ltsllc.miranda.Results;
import com.ltsllc.miranda.servlet.miranda.MirandaServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Clark on 3/31/2017.
 */
public class LoginServlet extends MirandaServlet {
    public void doOptions (HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Allow", "*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, Access-Control-Allow-Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.setHeader("Access-Control-Max-Age", "1209600");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginResultObject result = new LoginResultObject();

        try {
            LoginObject loginObject = fromJson(req.getInputStream(), LoginObject.class);
            LoginHolder.LoginResult loginResult = LoginHolder.getInstance().login(loginObject.getName());
            result.setResult(loginResult.result);

            if (loginResult.session != null) {
                result.setCategory(loginResult.session.getUser().getCategory().toString());
                String sessionIdString = Long.toString(loginResult.session.getId());
                byte[] plainText = sessionIdString.getBytes();
                EncryptedMessage encryptedMessage = loginResult.session.getUser().getPublicKey().encrypt(plainText);
                result.setEncryptedMessage(encryptedMessage);
            }
        } catch (MirandaException | GeneralSecurityException e) {
            result.setResult(Results.Exception);
            result.setException(e);
        } catch (TimeoutException e) {
            result.setResult(Results.Timeout);
        }

        resp.setHeader("Access-Control-Allow-Origin", "*");
        respond(resp.getOutputStream(), result);
        resp.setStatus(200);
    }
}
