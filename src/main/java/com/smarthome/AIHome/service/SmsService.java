package com.smarthome.AIHome.service;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;



import com.aliyun.teaopenapi.models.Config;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import static com.aliyun.teautil.Common.toJSONString;

@Service
@Slf4j
public class SmsService {
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.signName}")
    private String signName;

    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    private final StringRedisTemplate redisTemplate;

    public SmsService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    public boolean sendSms(String phoneNumber) {
        try {
            // 生成 6 位随机验证码
            String code = generateCode();

            // 1. 创建阿里云短信 Client
            Config config = new Config()
                    .setAccessKeyId(accessKeyId)
                    .setAccessKeySecret(accessKeySecret);
            config.endpoint = "dysmsapi.aliyuncs.com";
            Client client = new Client(config);

            // 2. 组装请求
            SendSmsRequest request = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumber)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam("{\"code\":\"" + code + "\"}");

            // 3. 发送短信
            SendSmsResponse response = client.sendSms(request);

            // 4. 处理响应
            if ("OK".equals(response.body.code)) {
                log.info("验证码 {} 发送成功", code);
                // 将验证码存入 Redis，5 分钟有效
                redisTemplate.opsForValue().set("sms:" + phoneNumber, code, 5, TimeUnit.MINUTES);
                return true;
            } else {
                log.error("短信发送失败，原因：{}", response.body.message);
                return false;
            }
        } catch (Exception e) {
            log.error("短信发送异常", e);
            return false;
        }
    }

    public boolean verifyCode(String phoneNumber, String inputCode) {
        String key = "sms:" + phoneNumber;
        String correctCode = redisTemplate.opsForValue().get(key);

        if (correctCode != null && correctCode.equals(inputCode)) {
            redisTemplate.delete(key); // 验证通过后删除验证码
            return true;
        }
        return false;
    }
}
