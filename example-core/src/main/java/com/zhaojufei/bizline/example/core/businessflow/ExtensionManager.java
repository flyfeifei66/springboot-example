package com.zhaojufei.bizline.example.core.businessflow;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 拓展点管理器
 */
public class ExtensionManager {
    private Map<Class, Object> extensionMap = Maps.newHashMap();

    /**
     * 批量注册拓展点
     *
     * @param extensionMap
     */
    public void batchRegister(Map<Class, Object> extensionMap) {
        this.extensionMap.putAll(extensionMap);
    }


    /**
     * 注册拓展点
     */
    public <T> void registerExtensionImpl(Class protocal, T impl) {
        extensionMap.put(protocal, impl);
    }


    /**
     * 获取拓展点实现
     *
     * @param protocal
     * @param <T>
     * @return
     */
    public <T> T getExtensionImpl(Class protocal) {
        return (T) extensionMap.get(protocal);
    }

}
