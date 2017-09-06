
import java.util.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

class AnnotationExpression (val obj:Any){

    fun expression(){

        val clazz=obj::class
        clazz.declaredMemberProperties.forEach { prop->
            val mutableProp= try{
                prop as KMutableProperty<*>
            }catch (e:Exception){
                null
            } ?: return@forEach

            mutableProp.annotations.forEach { annotation->
                val propClassName=mutableProp.returnType.toString().removePrefix("kotlin.")
                when(propClassName) {
                    in numtypeSet->mutableProp.setter.call(obj,
                            (readProp(annotation as Value) as kotlin.String).toNum(propClassName))
                    "String"->mutableProp.setter.call(obj,
                            (readProp(annotation as Value) as kotlin.String))
                    "Boolean"->mutableProp.setter.call(obj,
                            (readProp(annotation as Value) as kotlin.String).toBoolean())
                }
            }
        }

    }

    companion object {
        val typeMap= mapOf<String,String>("Int" to "Integer",
                "Double" to "Double",
                "Float" to "Float",
                "Byte" to "Byte",
                "Short" to "Short",
                "Long" to "Long")

        val numtypeSet= typeMap.keys
    }

    fun String.toNum(className:String):Any{
        val clazz=Class.forName("java.lang.${typeMap[className]}")
        return clazz.getMethod("parse$className",String::class.java).invoke(null,this)
    }

    private fun readProp(value:Value): Any? {
        val prop=Properties()
        prop.load( AnnotationExpression::class.java.getResource("app.properties").openStream())

        return prop.get(value.value)

    }

}
