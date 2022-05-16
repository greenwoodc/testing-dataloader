/*
 * Copyright (c) 2015, salesforce.com, inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.salesforce.dataloader.model;

import com.salesforce.dataloader.config.Config;

/**
 * all possible criteria for login into the api
 */
public class LoginCriteria {

    public static final int OAuthLoginDefault = 0;
    public static final int UsernamePasswordLoginStandard = 1;
    public static final int UsernamePasswordLoginAdvanced = 2;
    public static final int OAuthLoginFromBrowser = 3;

    String instanceUrl;
    String userName;
    String password;
    String sessionId;
    String environment;

    final int mode;

    public LoginCriteria(int mode) {
        this.mode = mode;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getMode() {
        return mode;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void updateConfig(Config config) {
        config.setValue(Config.USERNAME, config.STRING_DEFAULT);
        config.setValue(Config.PASSWORD, config.STRING_DEFAULT);
        config.setValue(Config.SFDC_INTERNAL_IS_SESSION_ID_LOGIN, false);
        config.setValue(Config.SFDC_INTERNAL_SESSION_ID, config.STRING_DEFAULT);
        config.setValue(Config.OAUTH_ENVIRONMENT, config.STRING_DEFAULT);

        switch (getMode()){
            case LoginCriteria.UsernamePasswordLoginStandard:
                config.setValue(Config.USERNAME, getUserName().trim());
                config.setValue(Config.PASSWORD, getPassword().trim());
                config.setValue(Config.ENDPOINT, getInstanceUrl().trim());
                break;
            case LoginCriteria.UsernamePasswordLoginAdvanced:
                config.setValue(Config.USERNAME, getUserName().trim());
                config.setValue(Config.SFDC_INTERNAL_IS_SESSION_ID_LOGIN, true);
                config.setValue(Config.SFDC_INTERNAL_SESSION_ID, getSessionId().trim());
                config.setValue(Config.ENDPOINT, getInstanceUrl().trim());
                break;
            case LoginCriteria.OAuthLoginDefault:
            case LoginCriteria.OAuthLoginFromBrowser:
                config.setOAuthEnvironment(getEnvironment());
                break;
        }
    }
}
