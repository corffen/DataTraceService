package com.gordon.study

import java.lang.IllegalArgumentException
import java.lang.reflect.Method
import java.util.concurrent.Executor

class DataServiceMethod(private val dataRequest: TraceDataRequest, private val executor: Executor) {

    fun invoke(args: Array<Any?>) {
        executor.execute(SensorTask(dataRequest, args))
    }

    companion object {
        fun parseMethod(method: Method): TraceDataRequest {
            val annotation = method.getAnnotation(TrackMethod2::class.java)
                ?: throw IllegalStateException("${method.name} miss @TrackMethod")
            val eventType = annotation.methodName
            println("annotationName=${eventType}")
            val parameters = method.parameterTypes
            val eventName = annotation.event.ifBlank {
                method.name
            }
            val listEntity = mutableListOf<EventEntity>()
            parameters.forEach {
                if (it.isArray) {
                    listEntity.add(
                        EventEntity(
                            it.name,
                            isList = false,
                            isArray = true,
                            componentType = it.componentType
                        )
                    )
                } else if (it.isAssignableFrom(List::class.java)) {
                    listEntity.add(
                        EventEntity(
                            it.name,
                            isList = true,
                            isArray = false,
                            componentType = it.componentType
                        )
                    )
                } else {
                    if (!it.isPrimitive && !it.isAssignableFrom(String::class.java)) {
                        throw IllegalStateException("illegal parameter ${it.name}")
                    }
                    listEntity.add(
                        EventEntity(
                            it.name,
                            isList = false,
                            isArray = false,
                            null
                        )
                    )
                }
            }
            return TraceDataRequest(
                eventType = eventType,
                eventName = eventName,
                list = listEntity
            )
        }
    }

    private class SensorTask(val dataRequest: TraceDataRequest, val args: Array<Any?>) : Runnable {
        override fun run() {
            val map = HashMap<String, Any>()
            if (dataRequest.list.size != args.size) {
                throw IllegalArgumentException("param numbers is not match")
            }
            dataRequest.list.forEachIndexed { index, eventEntity ->
                if (eventEntity.isArray) {
                    map[eventEntity.name] = handleArrayParam(eventEntity.componentType!!, args[index])
                } else if (eventEntity.isList) {
                    map[eventEntity.name] = args[index] as List<Any?>
                } else {
                    map[eventEntity.name] = args[index]!!
                }
            }
            println(map)
        }

        private fun handleArrayParam(type: Class<*>, any: Any?): ArrayList<Any> {
            val dataList = ArrayList<Any>()
            when {
                Boolean::class.javaPrimitiveType == type -> {
                    any as BooleanArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                Byte::class.javaPrimitiveType == type -> {

                    any as ByteArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                Char::class.javaPrimitiveType == type -> {

                    any as CharArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                Double::class.javaPrimitiveType == type -> {
                    any as DoubleArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                Float::class.javaPrimitiveType == type -> {

                    any as FloatArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                Int::class.javaPrimitiveType == type -> {

                    any as IntArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                Long::class.javaPrimitiveType == type -> {

                    any as LongArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                Short::class.javaPrimitiveType == type -> {

                    any as ShortArray
                    any.forEach {
                        dataList.add(it)
                    }
                    return dataList
                }

                else -> {
                    any as Array<*>
                    any.forEach {
                        dataList.add(it!!)
                    }
                    return dataList
                }
            }
        }
    }
}