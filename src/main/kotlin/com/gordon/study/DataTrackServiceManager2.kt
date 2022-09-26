package com.gordon.study

import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

object DataTrackServiceManager2 {

    private val serviceMethodMap = ConcurrentHashMap<Method, DataServiceMethod?>()
    private val executors = Executors.newCachedThreadPool()

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { _, method, args ->
            loadServiceMethod(method).invoke(args)
        } as T
    }

    private fun loadServiceMethod(method: Method): DataServiceMethod {
        var result: DataServiceMethod? = serviceMethodMap[method]
        if (result != null) return result

        synchronized(serviceMethodMap) {
            result = serviceMethodMap[method]
            if (result == null) {
                val dataRequest = DataServiceMethod.parseMethod(method)
                result = DataServiceMethod(dataRequest, executors)
                serviceMethodMap[method] = result
            }
        }
        return result!!
    }
}