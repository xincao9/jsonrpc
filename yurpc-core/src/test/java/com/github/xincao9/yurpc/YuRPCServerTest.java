/*
 * Copyright 2019 xincao9@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xincao9.yurpc;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.xincao9.yurpc.core.YuRPCClient;
import com.github.xincao9.yurpc.core.YuRPCServer;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 服务组件测试
 *
 * @author xincao9@gmail.com
 */
public class YuRPCServerTest {

    private static YuRPCServer yuRPCServer;

    /**
     * 启动服务组件
     *
     * @throws Throwable
     */
    @BeforeClass
    public static void setUpClass() throws Throwable {
        yuRPCServer = YuRPCServer.defaultYuRPCServer();
        yuRPCServer.register(new SayServiceImpl());
        yuRPCServer.start();
    }

    /**
     * 关闭服务组件
     *
     * @throws Throwable
     */
    @AfterClass
    public static void tearDownClass() throws Throwable {
        yuRPCServer.shutdown();
    }

    /**
     * 客户端发送请求给服务端
     *
     * @throws Throwable
     */
    @Test
    public void testMethod() throws Throwable {
        YuRPCClient yuRPCClient = YuRPCClient.defaultYuRPCClient();
        yuRPCClient.start();
        SayService sayService = yuRPCClient.proxy(SayService.class);
        int size = 100000;
        long startTime = System.currentTimeMillis();
        for (int no = 0; no < size; no++) {
            String value = RandomStringUtils.randomAscii(128);
            Say say = new Say(no, value);
            System.out.println(sayService.perform(say));
        }
        long costTime = System.currentTimeMillis() - startTime;
        System.out.println(String.format("性能测试 RT = %f, TPS = %d", costTime * 1.0 / size, size / (costTime / 1000)));
        yuRPCClient.shutdown();
    }

    public static class Say {

        private Integer id;
        private String body;

        public Say(Integer id, String body) {
            this.id = id;
            this.body = body;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return JSONObject.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    public interface SayService {

        Say perform(Say say);
    }

    public static class SayServiceImpl implements SayService {

        @Override
        public Say perform(Say say) {
            return say;
        }

    }
}
