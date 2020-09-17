package com.ai.cloud.service.scan;

import com.ai.cloud.base.exception.BusinessException;
import com.ai.cloud.base.lang.Rmap;
import com.ai.cloud.bean.scan.DevInfoBean;
import com.ai.cloud.bean.scan.ScanInfoBean;
import com.ai.cloud.bean.scan.ScanMessageBean;
import com.ai.cloud.bean.scan.StScanInfoBean;
import com.ai.cloud.service.common.WtCommonService;
import com.ai.cloud.service.index.WtIndexService;
import com.ai.cloud.tool.RabbitMqSender;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import static com.ai.cloud.constant.MqQueueConstant.SCAN_MESSAGE_NOTICE;
import static com.ai.cloud.constant.ScanConstant.*;

/**
 * 保存扫码用户信息 Service
 *
 * @author zhaanping
 * @date 2020-03-20
 */
@Slf4j
@Service
public class SaveScanInfoService {

    @Autowired
    WtIndexService wtIndexService;

    @Autowired
    WtCommonService wtCommonService;

    @Autowired
    RabbitMqSender rabbitMqSender;

    /**
     * 保存扫码用户信息
     *
     * @param reqBean
     * @return
     */
    public ScanMessageBean saveScanInfo(ScanInfoBean reqBean) {

        // 校验 请求参数
        String checkReqStr = checkReqStr(reqBean);
        if (StringUtils.isNotEmpty(checkReqStr)) {
            throw new BusinessException(RspCode.REQ_PARAM_ERR, checkReqStr);
        }

        // 解密 发展人信息
        DevInfoBean devInfo = wtCommonService.decryptDevInfo(reqBean.getA());
        if (StringUtils.isEmpty(devInfo.getDeveloperId())) {
            throw new BusinessException(RspCode.DEV_INFO_ERR, "发展人参数 发展人id不能为空");
        }
        String provinceCode = devInfo.getProvinceCode();
        String cityCode = devInfo.getCityCode();
        String channelId = devInfo.getChannelId();
        String developerId = devInfo.getDeveloperId();

        // 判断渠道是否已配置沃厅
        if (!wtIndexService.isWotingOpen(provinceCode, cityCode, channelId)) {
            log.error("渠道未配置沃厅,参数:{}", reqBean.getA());
            throw new BusinessException(RspCode.WT_AUTH_ERR, "渠道未配置云厅");
        }

        // 判断所属地市是否已发布推广页面
        if (!wtIndexService.isWtPageOpen(provinceCode, cityCode)) {
            log.error("所属地市未发布推广页面,参数:{}", reqBean.getA());
            throw new BusinessException(RspCode.WT_AUTH_ERR, "未开通云厅推广页面");
        }

        // 组装 msgBean
        String popularizeType = reqBean.getPopularizeType();
        ScanMessageBean msgBean = ScanMessageBean.builder()
                .scanMessageTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .qrCodeType(QrCodeType.WO_MART)
                // 推送消息时，需要转换下
                .popularizeType(Rmap.getStr(PopularizeType.MSG_POPULARIZE_TYPE_MAP, popularizeType))
                .scanChannelType(reqBean.getScanChannelType())

                .provinceCode(provinceCode)
                .cityCode(cityCode)
                .channelId(channelId)
                .scanDeveloperId(developerId)
                .build();

        // 组装 设备信息
        if (StringUtils.isNotEmpty(reqBean.getPhoneBrand())) {
            msgBean.setCustPhoneBrand(reqBean.getPhoneBrand());
        }
        if (StringUtils.isNotEmpty(reqBean.getPhoneType())) {
            msgBean.setCustPhoneType(reqBean.getPhoneType());
        }
        if (StringUtils.isNotEmpty(reqBean.getPhoneImei())) {
            msgBean.setCustPhoneImei(reqBean.getPhoneImei());
        }

        // 分享 后台生成 进店码
        if (PopularizeType.SHARE.equals(popularizeType)) {
            msgBean.setScanMessageId(obtainMessageId());
        }

        // 处理 手厅扫码
        if (PopularizeType.QR.equals(popularizeType)) {
            // 手厅参数
            StScanInfoBean stInfo = wtCommonService.decryptStScanInfo(reqBean.getP());
            if (StringUtils.isEmpty(stInfo.getScanMessageId())) {
                throw new BusinessException(RspCode.ST_INFO_ERR, "手厅参数 进店码不能为空");
            }
            if (!LoginState.LOGIN_STATE_LIST.contains(stInfo.getLoginState())) {
                throw new BusinessException(RspCode.ST_INFO_ERR, "手厅参数 登录状态有误");
            }
            msgBean.setLoginState(stInfo.getLoginState());
            msgBean.setScanMessageId(stInfo.getScanMessageId());
            msgBean.setCustMobilePhone(stInfo.getCustMobilePhone());
            msgBean.setCustNickName(stInfo.getCustNickName());
        }

        // 发送消息
        sendScanMessage(msgBean);

        return msgBean;
    }

    /**
     * 校验请求参数
     *
     * @param reqBean
     * @return
     */
    public String checkReqStr(ScanInfoBean reqBean) {
        String popularizeType = reqBean.getPopularizeType();
        if (!PopularizeType.POPULARIZE_TYPE_LIST.contains(popularizeType)) {
            return "推广类型 有误";
        }
        String scanChannelType = reqBean.getScanChannelType();
        if (!ScanChannelType.SCAN_CHANNEL_TYPE_LIST.contains(scanChannelType)) {
            return "消息渠道类型 有误";
        }
        if (StringUtils.isEmpty(reqBean.getA())) {
            return "发展人参数 不能为空";
        }
        if (PopularizeType.QR.equals(popularizeType)) {
            if (StringUtils.isEmpty(reqBean.getP())) {
                return "手厅参数 不能为空";
            }
            if (StringUtils.isEmpty(reqBean.getPhoneImei())) {
                return "扫码设备Imei 不能为空";
            }
        }
        return "";
    }

    /**
     * 生成进店码
     *
     * @return
     */
    public String obtainMessageId() {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"));
        int ramdom = 1000 + (int) (Math.random() * (9999 - 1000 + 1));
        // 15位时间戳+4位随机数
        return timeStamp + ramdom;
    }

    /**
     * 推送消息
     *
     * @param msgBean
     */
    public void sendScanMessage(ScanMessageBean msgBean) {
        try {
            String scanMessageStr = JSON.toJSONString(msgBean);
            Map map = JSON.parseObject(scanMessageStr, Map.class);
            rabbitMqSender.sendDefaultRabbitMq(SCAN_MESSAGE_NOTICE, map);
            log.info("码上购-沃厅-保存扫码用户信息，进店码：{}，推送消息：{}，成功。。。", msgBean.getScanMessageId(), JSON.toJSONString(map));
        } catch (Exception e) {
            log.info("码上购-沃厅-保存扫码用户信息，进店码：{}，推送消息：{}，异常", msgBean.getScanMessageId(), JSON.toJSONString(msgBean), e);
            throw new BusinessException(RspCode.SEND_MSG_ERR, "推送消息失败，请您稍后再试");
        }
    }
}
