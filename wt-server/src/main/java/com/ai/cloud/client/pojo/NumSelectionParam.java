package com.ai.cloud.client.pojo;


/**
 * @author xiaoniu
 * 选号服务bean
 */
public class NumSelectionParam {

    /**统计类参数:访问者ip**/
    private String monitorIp;

    /**统计类参数：调用方编码 ,必传**/
    private String monitorChannel;

    /**统计类参数：用途分类**/
    private String monitorPurpose;



    /**省份编码，必传**/
    private String provinceCode;

    /**地市编码,必传**/
    private String cityCode;

    /** 查询类型: 01:列表查询  02:组内号码查询,必传**/
    private String qryType;


    /**
     * 查询类型 1.普通选号 2靓号选号 3特色靓号选号 4普号、靓号、特色靓号
     * 默认为1
     */
    private String searchCategory;


    /**
     * 号码组ID：多个号码组ID之间使用","号分隔
     * （备注：符号为英文半角）
     */
    private String groupKey;

    /**
     * 商品ID
     * */
    private String goodsId;


    /**
     *  操作系统编码: 商城, 电子沃电, 码上购
     *  默认为商城
     */
    private String sysCode;



    //区县编码
    private String districtCode;

    //部门编码(多个部门直接使用,分隔)
    private String stockId;


    //操作员工号
    private String staffId;

    //渠道编码
    private String channelId;

    /**
     * 池类型
     * 00:全部池 01:公共池  02:专属池子 03:共享池
     */
    private String poolType;

    //预存款下限（分）
    private String advancePayLower;

    //预存款上限（分）
    private String advancePayTop;
    /**
     * 号码类型：
     * 支持 AAAAA、AAAA、ABCDE、ABCD、AAA、AABB、ABAB、ABC、AA，从末尾匹配
     */
    private String codeTypeCode;
    //业务分类:01:公众 02:集团
    private String businessType;

    // 网号 如:186
    private String numNet;
    /**
     *条件查询类型
     * 01： NUM_NET%SEARCH_VALUE%（ SEARCH_VALUE  匹配后八位）
     * 02： NUM_NET%SEARCH_VALU  （ SEARCH_VALUE  匹配最后几位）
     * 03： 只有网号
     */
    private String searchType;

    //查询关键字 2-4位数字
    private String searchValue;

    /**
     * 排序类型
     * 1：numAsc（号码升序）   2：numDesc（号码降序）
     * 3：priceAsc（价格升序） 4：priceDesc（价格降序）
     */
    private String sortType;

    //业务号码
    private String serialNumber;


    /**
     * 组号码不足时是否查询公共池号码
     *  0:是   1：否
     */
    private String judgeType;

    /**
     * 返回号码个数： 默认100
     */
    private String amounts;

    /**
     * 特色靓号类型
     * 01：爱情  02：吉祥  03：事业  04：全部
     */
    private String featureType;
    /**
     * 等级金额:
     * 月承诺通信费（分）,传递最低金额,返回该值以及以上
     */
    private String monthFeeLimit;

    /**
     * 生日靓号
     * 传输月份: 01,02 ... 12
     * 如01月,则显示尾号0101-0131,查询后4位
     */
    private String monthNum;

    /**
     * 协议期（月） 如果输入24个月，返回大于等于24个月协议期
     */

    private String monthLimit;
    /**
     * 是否查询靓号：
     * 值为1:返回靓号(包括特色靓号和靓号) ; 值为0:返回普号
     * 该值为空时返回随机号码
     */
    private String niceTag;

    /**
     * 年代号码
     * 年代（根据年代来匹配后四位）
     *00：2000-2050
     *50：1901-1959
     *60：1960-1969
     *70：1970-1979
     *80：1980-1989
     *90：1990-1999
     */
    private String yearNum;

    /**
     * 返回数据方式是否为jsonp格式  默认为false
     */
    private String jsonpTag;

    /**
     * callback
     */
    private String callback;

    /**
     * 号码状态
     */
    private String codeState;


    /**惠字标识 0.非惠子标  1.惠子标**/
    private String tailNumTag;

    public String getMonitorIp() {
        return monitorIp;
    }

    public void setMonitorIp(String monitorIp) {
        this.monitorIp = monitorIp;
    }

    public String getMonitorChannel() {
        return monitorChannel;
    }

    public void setMonitorChannel(String monitorChannel) {
        this.monitorChannel = monitorChannel;
    }

    public String getMonitorPurpose() {
        return monitorPurpose;
    }

    public void setMonitorPurpose(String monitorPurpose) {
        this.monitorPurpose = monitorPurpose;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getQryType() {
        return qryType;
    }

    public void setQryType(String qryType) {
        this.qryType = qryType;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getPoolType() {
        return poolType;
    }

    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

    public String getAdvancePayLower() {
        return advancePayLower;
    }

    public void setAdvancePayLower(String advancePayLower) {
        this.advancePayLower = advancePayLower;
    }

    public String getAdvancePayTop() {
        return advancePayTop;
    }

    public void setAdvancePayTop(String advancePayTop) {
        this.advancePayTop = advancePayTop;
    }

    public String getCodeTypeCode() {
        return codeTypeCode;
    }

    public void setCodeTypeCode(String codeTypeCode) {
        this.codeTypeCode = codeTypeCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getNumNet() {
        return numNet;
    }

    public void setNumNet(String numNet) {
        this.numNet = numNet;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(String judgeType) {
        this.judgeType = judgeType;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getMonthFeeLimit() {
        return monthFeeLimit;
    }

    public void setMonthFeeLimit(String monthFeeLimit) {
        this.monthFeeLimit = monthFeeLimit;
    }

    public String getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(String monthNum) {
        this.monthNum = monthNum;
    }

    public String getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(String monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getNiceTag() {
        return niceTag;
    }

    public void setNiceTag(String niceTag) {
        this.niceTag = niceTag;
    }

    public String getYearNum() {
        return yearNum;
    }

    public void setYearNum(String yearNum) {
        this.yearNum = yearNum;
    }

    public String getJsonpTag() {
        return jsonpTag;
    }

    public void setJsonpTag(String jsonpTag) {
        this.jsonpTag = jsonpTag;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getCodeState() {
        return codeState;
    }

    public void setCodeState(String codeState) {
        this.codeState = codeState;
    }

    public String getTailNumTag() {
        return tailNumTag;
    }

    public void setTailNumTag(String tailNumTag) {
        this.tailNumTag = tailNumTag;
    }
}
