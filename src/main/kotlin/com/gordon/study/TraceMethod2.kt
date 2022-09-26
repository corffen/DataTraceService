package com.gordon.study

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class TrackMethod2(
    /**
     * 调用的方法名，包含包名+类名+方法名
     *
     */
    val methodName: String,
    /**
     * 事件的名字，如果未空者会取方法名字，也可以避免一个类如果有两个同名的方法，可以用此属性来指定事件名字
     */
    val event: String = ""
)

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.VALUE_PARAMETER
)
annotation class TrackMethodPara2
