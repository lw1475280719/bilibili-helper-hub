package io.cruii.feign;

import io.cruii.pojo.dto.PushMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author cruii
 * Created on 2022/9/7
 */
@FeignClient(name = "hh-push-service", path = "/push")
public interface PushFeignService {
    @PostMapping()
    boolean push(@RequestBody PushMessageDTO message);

    @PostMapping("expired")
    boolean notifyExpired(@RequestBody List<String> expiredIdList);
}
