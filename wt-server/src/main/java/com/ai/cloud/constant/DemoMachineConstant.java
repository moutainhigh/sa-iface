package com.ai.cloud.constant;

/**
 * 演示机常量
 *
 * @author zhaanping
 * @date 2020-06-11
 */
public interface DemoMachineConstant {

    /**
     * 演示机厅长 redis key
     */
    String IS_DIRECTOR_REDIS_KEY = "IS_DEMO_MACHINE_DIRECTOR_";
    /**
     * 是演示机厅长
     */
    String IS_DIRECTOR = "1";

    /**
     * redis缓存时间（单位：秒）
     */
    int CACHE_TIME = 600;

    /**
     * 比较久的redis缓存时间（单位：天）
     */
    int LONG_CACHE_TIME = 365;

    /**
     * 商品品牌列表 redis key
     */
    String GOODS_BRAND_REDIS_KEY = "DEMO_MACHINE_GOODS_BRAND_";
    /**
     * 商品型号列表 redis key
     */
    String GOODS_MODEL_REDIS_KEY = "DEMO_MACHINE_GOODS_MODEL_";

    /**
     * 购物车 redis key 模版
     */
    String CART_REDIS_KEY = "msgo:demonstrator:cart:{0}";
    /**
     * 购物车 商品最小数量
     */
    int CART_MIN_NUM = 1;
    /**
     * 购物车 商品最大数量
     */
    int CAT_MAX_NUM = 50;
    /**
     * 购物车 示例 是否展示标记
     */
    String DEMO_MACHINE_SHOW_ILLUSTRATE = "DEMO_MACHINE_SHOW_ILLUSTRATE_";

    /**
     * 每页默认展示数量
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * 商品上市最大天数
     */
    int GOODS_UP_MAX_DAY = 180;

    /**
     * 返回编码
     * 0000：操作成功；9999：系统异常；
     * 3001：请求参数错误；4001：权限不足，当前账户不是所属渠道的演示机厅长；
     * 3002：订单状态已改变，请确认该订单信息!
     * 3003：商品已失效
     * 3004：存在有效的（非省份驳回、非地市驳回）A类（厂商配置）申请单
     * 3005：该商品已经加入过购物车
     * 3006：购物车内只能有一种模式
     * 3007：申请数量有误
     */
    interface RspCode {
        /**
         * 操作成功
         */
        String SUCCESS = "0000";
        /**
         * 系统异常
         */
        String SYS_ERR = "9999";
        /**
         * 请求参数错误
         */
        String REQ_PARAM_ERR = "3001";
        /**
         * 权限不足，当前账户不是所属渠道的演示机厅长
         */
        String DIRECTOR_AUTH_ERR = "4001";

        /**
         * 订单状态已改变，请确认该订单信息!
         */
        String ORDER_STATE_ERR = "3002";

        /**
         * 商品已失效
         */
        String GOODS_INVALID_ERR = "3003";
        /**
         * 存在有效的（非省份驳回、非地市驳回）A类（厂商配置）申请单
         */
        String HAVE_VALID_A_ORDER_ERR = "3004";
        /**
         * 该商品已经加入过购物车
         */
        String CART_REPEAT_ERR = "3005";
        /**
         * 当前购物车内只能有一种模式
         */
        String CART_APPLY_MODE_ERR = "3006";
        /**
         * 申请数量有误
         */
        String CART_COUNT_ERR = "3007";
    }

    /**
     * 申请模式 01 厂家配置  02 折扣买断  03 租赁租用
     */
    interface ApplyMode {
        String FIRM_CONFIG = "01";
        String DISCOUNT_BUYOUT = "02";
        String RENT = "03";
    }

    /**
     * 是否可以确认完成：0：不可以；1：可以
     */
    interface CanConfirm {
        String NO = "0";
        String YES = "1";
    }

    /**
     * 是否可申请状态：0：不可申请；1：可申请 ；
     */
    interface CanApply {
        String NO = "0";
        String YES = "1";
    }

    /**
     * 展示标记：0：不展示；1：展示；
     */
    interface ShowFlag {
        String NO = "0";
        String YES = "1";
    }

    /**
     * 有效标记：0：已失效；1：有效 ；
     */
    interface ValidFlag {
        String NO = "0";
        String YES = "1";
    }

    /**
     * 订单状态
     * <p>
     * 1：地市待审核；2：地市审核通过；3：地市审核驳回；4：省份审核通过；5：省份审核驳回；6：已完成；
     */
    interface OrderState {

        /**
         * 地市待审核
         */
        int WAIT_CITY_AUDIT = 1;
        /**
         * 地市审核通过
         */
        int CITY_AUDIT_PASS = 2;
        /**
         * 地市审核驳回
         */
        int CITY_AUDIT_REFUSE = 3;
        /**
         * 省份审核通过
         */
        int PROVINCE_AUDIT_PASS = 4;
        /**
         * 省份审核驳回
         */
        int PROVINCE_AUDIT_REFUSE = 5;
        /**
         * 已完成
         */
        int COMPLETE = 6;
    }

    /**
     * 商品状态 00 初始值 01 已发货 02 已签收 03 已驳回
     */
    interface GoodsState {
        String DEFAULT_STATE = "00";
        String DELIVERED = "01";
        String SIGNED_IN = "02";
        String REJECT = "03";
    }

    /**
     * 购物车操作类型：0：加入购物车；2：购物车商品数量变更；3：删除购物车商品
     */
    interface CartOperType {
        String ADD = "1";
        String UPDATE_COUNT = "2";
        String DELETE = "3";
    }

    /**
     * 处理类型 01 提交申请单；02 确认完成；11 状态回传；
     */
    interface LogDealType {
        String APPLY_SUBMIT = "01";
        String CONFIRM = "02";
        String STATE_RETURN = "11";
    }

    /**
     * 日志操作类型：01：客户操作 02：操作员操作 03：系统操作
     */
    interface LogOperateType {
        String CUSTOM = "01";
        String STAFF = "02";
        String SYSTEM = "03";
    }

    /**
     * 展示标记：0：前台后台都不显示 1：前台显示 2：后台显示 3：前台后台都显示
     */
    interface LogShowFlag {
        String NOT_SHOW = "0";
        String FRONT_SHOW = "1";
        String BACK_SHOW = "2";
        String ALL_SHOW = "3";
    }
}
