package io.cruii.push.pusher.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.cruii.push.pusher.Pusher;
import io.cruii.util.OkHttpUtil;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author cruii
 * Created on 2021/10/14
 */
@Log4j2
public class BarkPusher implements Pusher {
    private final String deviceKey;

    public BarkPusher(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    @Override
    public boolean push(String content) {
        JSONObject body = JSONUtil.createObj();
        body.set("body", content)
                .set("device_key", deviceKey)
                .set("title", "Bilibili Helper Hub任务日志");
        Request request = new Request.Builder()
                .url("https://api.day.app/push")
                .post(RequestBody.create(body.toJSONString(0).getBytes(StandardCharsets.UTF_8),
                        MediaType.parse("application/json")))
                .build();

        try (Response response = OkHttpUtil.executeWithRetry(request)) {
            if (response.isSuccessful()) {
                log.info("Bark推送成功");
                return true;
            } else {
                log.error("Bark推送失败: {}", response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
