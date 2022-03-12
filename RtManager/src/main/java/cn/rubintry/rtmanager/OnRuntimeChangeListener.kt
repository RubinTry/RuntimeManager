package cn.rubintry.rtmanager

interface OnRuntimeChangeListener {
    fun onChange(newHost: String , runtimeType: RuntimeType)
}

enum class RuntimeType{
    TEST,
    PRODUCT,
    CUSTOM
}