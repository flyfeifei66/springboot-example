package com.zhaojufei.bizline.example.core.constant;

/**
 * redis key都必须在此类中定义，使用：做命名空间分割符，确保自己定义的Key不会覆盖别人的Key。
 *
 * @author zhaojufei
 */
public class RedisKeyUtil {
    public static final String PREFIX = "bizline:example:";

    /**
     * 获取业务单号分布式锁
     */
    public static String getBillNoLockKey(int billType) {
        StringBuilder builder = new StringBuilder(PREFIX);
        builder.append("billNo:");
        builder.append(billType);
        return builder.toString();
    }

    /**
     * 获取支付回流锁key
     * 
     * @return
     */
    public static String getPayNotifyLockKey(String tradeNo) {
        return PREFIX + "pay-notify:" + tradeNo;
    }

    /**
     * 电子发票开票状态回流
     */
    public static String getInvoiceKey(String threeCodeId) {
        return PREFIX + "invoice-notify:" + threeCodeId;
    }

    /**
     * 获取重新开票分布式锁
     *
     * @param tenantId
     * @param requestNo
     * @return
     */
    public static String getReCreateLock(String tenantId, String requestNo) {
        return PREFIX + "re-create:" + tenantId + ":" + requestNo;
    }

    /**
     * 拉单订单ID分布式锁
     *
     * @param tenantId
     * @param storeCode
     * @param orderId
     * @return
     */
    public static String getOrderKey(String tenantId, String storeCode, String orderId) {
        return PREFIX + "pull-order:" + tenantId + ":" + storeCode + ":" + orderId;
    }

    /**
     * 获取用户form表单操作分布式锁key
     */
    public static String getUserFormOpsLockKey(String tenantId, String userId) {
        return PREFIX + "userFormOps:" + tenantId + ":" + userId;
    }

    /**
     * 获取用户form表单操作TokenMap分布式锁key
     */
    public static String getUserFormOpsTokenMapLockKey(String tenantId, String userId) {
        return PREFIX + "UserFormOpsTokenMap:" + tenantId + ":" + userId;
    }

    /**
     * 获取用户form表单操作TokenMap分布式锁key
     */
    public static String getUserFormOpsIsLockedLockKey(String tenantId, String userId) {
        return PREFIX + "UserFormOpsIsLocked:" + tenantId + ":" + userId;
    }
}
