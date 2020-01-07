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
package com.github.xincao9.jsonrpc.benchmark.provider.dubbo;

import com.github.xincao9.jsonrpc.benchmark.SleepService;
import java.util.concurrent.TimeUnit;
import org.apache.dubbo.config.annotation.Service;

/**
 * 延时服务
 *
 * @author xincao9@gmail.com
 */
@Service(version = "1.0.0")
public class SleepServiceImpl implements SleepService {

    /**
     * 执行
     *
     * @param ms 秒数
     */
    @Override
    public void perform(Integer ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
        }

    }

}
