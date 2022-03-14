package cn.rubintry.rtmanager.callback;

import cn.rubintry.rtmanager.core.RuntimeType;

public interface OnRuntimeChangeListener {
    void onChange(String newHost , RuntimeType runtimeType);
}
