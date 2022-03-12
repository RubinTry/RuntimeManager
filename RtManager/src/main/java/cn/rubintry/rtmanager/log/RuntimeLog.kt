package cn.rubintry.rtmanager.log

object RuntimeLog {
    private val logger: ILogger = DefaultLogger()

    @JvmStatic
    fun d(msg: String){
        logger.debug(msg)
    }
}