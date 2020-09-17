package com.ai.cloud.service.common;

import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.config.ScanConfig;
import com.ai.cloud.bean.scan.DevInfoBean;
import com.ai.cloud.bean.scan.StScanInfoBean;
import com.ai.cloud.service.index.WtIndexService;
import com.ai.cloud.service.scan.SaveScanInfoService;
import com.ai.cloud.tool.AesUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static com.ai.cloud.constant.ScanConstant.*;

/**
 * 沃厅 公共服务
 *
 * @author zhaanping
 * @date 2020-03-21
 */
@Slf4j
@Component
public class WtCommonService {

    @Autowired
    WtIndexService wtIndexService;

    @Autowired
    ScanConfig scanConfig;

    /**
     * 解密发展人信息
     *
     * @param str
     * @return
     */
    public DevInfoBean decryptDevInfo(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new BusinessException(RspCode.DEV_INFO_ERR, "发展人参数 不能为空");
        }
        Map<String, String> map = decrypt(str, DecryptType.DEV_INFO);
        if (Rmap.isEmpty(map)) {
            throw new BusinessException(RspCode.DEV_INFO_ERR, "发展人参数 格式有误");
        }
        // a=aes.encode(d=发展人id&c=渠道id&p=省份&e=地市)
        DevInfoBean devInfoBean = DevInfoBean.builder()
                .developerId(Rmap.getStr(map, "d"))
                .channelId(Rmap.getStr(map, "c"))
                .provinceCode(Rmap.getStr(map, "p"))
                .cityCode(Rmap.getStr(map, "e"))
                .build();
        if (StringUtils.isEmpty(devInfoBean.getProvinceCode())) {
            throw new BusinessException(RspCode.DEV_INFO_ERR, "发展人参数 省份编码不能为空");
        }
        if (StringUtils.isEmpty(devInfoBean.getCityCode())) {
            throw new BusinessException(RspCode.DEV_INFO_ERR, "发展人参数 地市编码不能为空");
        }
        return devInfoBean;
    }

    /**
     * AES 解密
     *
     * @param str 密文，举个栗子：aes.encode(d=发展人id&c=渠道id&p=省份&e=地市)
     * @return
     */
    public String aesDecrypt(String str) throws Exception {
        AesUtils aesCryptor = new AesUtils(scanConfig.getAesKey());
        String aesStr = aesCryptor.decrypt(URLDecoder.decode(str, "UTF-8").replace(" ", "+"));
        return aesStr;
    }

    /**
     * URL 参数转 map
     *
     * @param paramStr 举个栗子：d=发展人id&c=渠道id&p=省份&e=地市
     * @return
     */
    public Map<String, String> paramToMap(String paramStr) {
        Map<String, String> map = Maps.newHashMap();
        if (StringUtils.isEmpty(paramStr)) {
            return map;
        }

        List<String> strList = Splitter.on("&").trimResults().splitToList(paramStr);

        for (String str : strList) {
            if (StringUtils.isEmpty(str)) {
                continue;
            }

            List<String> list = Splitter.on("=").splitToList(str);
            if (list.size() < 2) {
                continue;
            }

            String key = list.get(0);
            String value = list.get(1);
            for (int i = 2; i < list.size(); i++) {
                value += "=" + list.get(i);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * 解密手厅扫码信息
     *
     * @param str
     * @return
     */
    public StScanInfoBean decryptStScanInfo(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new BusinessException(RspCode.ST_INFO_ERR, "手厅参数 不能为空");
        }
        Map<String, String> map = decrypt(str, DecryptType.ST_SCAN_INFO);
        if (Rmap.isEmpty(map)) {
            throw new BusinessException(RspCode.ST_INFO_ERR, "手厅参数 格式有误");
        }
        // p=AES.encode(lg=1&sq=77166616636&no=18511838352&nm=测试)
        return StScanInfoBean.builder()
                .loginState(Rmap.getStr(map, "lg"))
                .scanMessageId(Rmap.getStr(map, "sq"))
                .custMobilePhone(Rmap.getStr(map, "no"))
                .custNickName(Rmap.getStr(map, "nm"))
                .build();
    }

    /**
     * 解密
     *
     * @param str
     * @param type
     * @return
     */
    public Map<String, String> decrypt(String str, String type) {
        String typeDesc = Rmap.getStr(DecryptType.TYPE_MAP, type);
        try {
            if (DecryptType.ST_SCAN_INFO.equals(type)) {
                // 手厅参数，需要decode两次
                str = URLDecoder.decode(str, "UTF-8").replace(" ", "+");
            }
            String aesStr = aesDecrypt(str);
            log.info("码上购-沃厅-解密，类型：{}，密文：{}，明文：{}", typeDesc, str, aesStr);

            return paramToMap(aesStr);
        } catch (Exception e) {
            log.error("码上购-沃厅-解密，类型：{}，密文：{}，异常", typeDesc, str, e);
            throw new BusinessException(RspCode.DECRYPT_ERR, "解密" + typeDesc + "异常");
        }
    }

    public static void main(String[] args) throws Exception {
        AesUtils aesCryptor = new AesUtils("is9Ubm0iH4uMQ0wE");
        SaveScanInfoService saveScanInfoService = new SaveScanInfoService();

        System.out.println("------------------------------------- 加密 ---------------------------------------------------");
        // 发展人加密
        // a=aes.encode(d=发展人id&c=渠道id&p=省份&e=地市)
//        String a = "d=18511838352&c=1111af117&p=11&e=110";
//        String a = "d=15611028838&c=1111b0pdd&p=11&e=110";
        String a = "d=1102310032&c=1111b063w&p=11&e=110";
        String devInfo = URLEncoder.encode(aesCryptor.encrypt(a), "UTF-8");
        System.out.println("发展人信息：" + a + "，加密后：" + devInfo);
        System.out.println("---------------------- 手厅加密 已登录---------------------");
        // 手厅扫码信息加密 已登录
        // p=AES.encode(lg=1&sq=77166616636&no=18511838352&nm=测试)
        String sq = saveScanInfoService.obtainMessageId();
        String p = "lg=1&sq=" + sq + "&no=15910341192&nm=测试";
        String loginStInfo = URLEncoder.encode(URLEncoder.encode(aesCryptor.encrypt(p), "UTF-8"), "UTF-8");
        System.out.println("进店码：" + sq + "，手厅登录用户信息：" + p);
        System.out.println("加密后：" + loginStInfo);
        System.out.println("---------------------- 手厅加密 未登录---------------------");
        // 手厅扫码信息加密 未登录
        String nsq = saveScanInfoService.obtainMessageId();
        String np = "lg=0&sq=" + nsq;
        String noLoginStInfo = URLEncoder.encode(URLEncoder.encode(aesCryptor.encrypt(np), "UTF-8"), "UTF-8");
        System.out.println("进店码：" + nsq + "，手厅未登录用户信息：" + np);
        System.out.println("加密后：" + noLoginStInfo);

        System.out.println();
        System.out.println("------------------------------------- 解密 ---------------------------------------------------");
        // 发展人解密
        String aStr = "PEyXCgAooOkzWXhP8sdqqIY4e4LNrfxe3vKDnBFpw8QDLQyD8n1GkVaFtP4GpT4h";
        String dev = aesCryptor.decrypt(URLDecoder.decode(aStr, "UTF-8").replace(" ", "+"));
        System.out.println("发展人信息：" + aStr + "，解密后：" + dev);
        // 手厅扫码信息解密 已登录
        String pStr = "A0dKrGnDFjfyp%252Ft2iySgxsxWXOg0i1SVqMwfrUO4WmScZQP95Wz5naPrqDKk8Iwo9yLEGY8vLfEHLIZPkyHbZEqGyeSYQ9Rc5BOnE8cysNY%253D";
        String stScan = aesCryptor.decrypt(URLDecoder.decode(URLDecoder.decode(pStr, "UTF-8").replace(" ", "+"), "UTF-8").replace(" ", "+"));
        System.out.println("手厅用户信息：" + pStr + "，解密后：" + stScan);
        // 手厅扫码信息解密 未登录
        String npStr = "xxllzP8DzTGDpAsCuPPUPKtyU9O4I18PT4Zk81FOGqafY7nfJdxHKKwVC4V67ANy";
        String nstScan = aesCryptor.decrypt(URLDecoder.decode(URLDecoder.decode(npStr, "UTF-8").replace(" ", "+"), "UTF-8").replace(" ", "+"));
        System.out.println("手厅未登录用户信息：" + npStr + "，解密后：" + nstScan);


        System.out.println("202003261134284891000".length());
    }
}
