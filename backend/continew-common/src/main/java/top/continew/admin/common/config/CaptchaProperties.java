/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.admin.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码配置属性
 *
 * @author Charles7c
 * @since 2022/12/11 13:35
 */
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    /**
     * 图形验证码过期时间
     */
    @Value("${continew-starter.captcha.graphic.expirationInMinutes}")
    private long expirationInMinutes;

    /**
     * 邮箱验证码配置
     */
    private CaptchaMail mail;

    /**
     * 短信验证码配置
     */
    private CaptchaSms sms;

    public long getExpirationInMinutes() {
        return this.expirationInMinutes;
    }

    public void setExpirationInMinutes(long expirationInMinutes) {
        this.expirationInMinutes = expirationInMinutes;
    }

    public CaptchaMail getMail() {
        return this.mail;
    }

    public void setMail(CaptchaMail mail) {
        this.mail = mail;
    }

    public CaptchaSms getSms() {
        return this.sms;
    }

    public void setSms(CaptchaSms sms) {
        this.sms = sms;
    }

    /**
     * 邮箱验证码配置
     */
    public static class CaptchaMail {
        /**
         * 内容长度
         */
        private int length;

        /**
         * 过期时间
         */
        private long expirationInMinutes;

        /**
         * 模板路径
         */
        private String templatePath;

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public long getExpirationInMinutes() {
            return this.expirationInMinutes;
        }

        public void setExpirationInMinutes(long expirationInMinutes) {
            this.expirationInMinutes = expirationInMinutes;
        }

        public String getTemplatePath() {
            return this.templatePath;
        }

        public void setTemplatePath(String templatePath) {
            this.templatePath = templatePath;
        }
    }

    /**
     * 短信验证码配置
     */
    public static class CaptchaSms {
        /**
         * 内容长度
         */
        private int length;

        /**
         * 过期时间
         */
        private long expirationInMinutes;

        /**
         * 验证码字段模板键名
         */
        private String codeKey = "code";

        /**
         * 失效时间字段模板键名
         */
        private String timeKey = "expirationInMinutes";

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public long getExpirationInMinutes() {
            return this.expirationInMinutes;
        }

        public void setExpirationInMinutes(long expirationInMinutes) {
            this.expirationInMinutes = expirationInMinutes;
        }

        public String getCodeKey() {
            return this.codeKey;
        }

        public void setCodeKey(String codeKey) {
            this.codeKey = codeKey;
        }

        public String getTimeKey() {
            return this.timeKey;
        }

        public void setTimeKey(String timeKey) {
            this.timeKey = timeKey;
        }
    }
}
