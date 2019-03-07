package org.dashevo.schema

import org.dashevo.schema.util.JsonSchemaUtils
import org.jsonorg.JSONObject

object Object {

    const val ACT = "act"
    const val ALL_OF = "allOf"
    const val DAPOBJECTS = "dapobjects"
    const val DEFINITIONS = "definitions"
    const val DAPMETASCHEMA = "dapmetaschema"
    const val INDEX = "index"
    const val IS_ROLE = "_isrole"
    const val OBJECTS = "objects"
    const val OBJTYPE = "objtype"
    const val PROPERTIES = "properties"
    const val REF = "\$ref"
    const val REV = "rev"
    const val STPACKET = "stpacket"
    const val STHEADER = "stheader"
    const val TITLE = "title"
    const val TYPE = "type"
    const val USER_ID = "userId"
    const val BUID = "buid"
    const val SCHEMA_ID = "\$id"

    const val CREATE_OBJECT_ACTION = 0
    const val UPDATE_OBJECT_ACTION = 2
    const val REMOVE_OBJECT_ACTION = 3

    fun setID(obj: JSONObject, dapSchema: JSONObject? = null): String {
        val id = toHash(obj, dapSchema)
        setMeta(obj, "id", id)
        return id
    }

    /**
     * Set metadata property value in a Schema object instance
     * @param obj Schema object instance
     * @param key Meta section keyword
     * @param value
     */
    fun setMeta(obj: JSONObject?, key: String, value: Any): JSONObject? {
        val typeProp: JSONObject = if (isSysObject(obj)) {
            obj!![obj.keys().next()] as JSONObject
        } else {
            obj!!
        }

        if (!typeProp.has("meta")) {
            typeProp.put("meta", JSONObject())
        }

        typeProp.getJSONObject("meta").put(key, value)

        return obj
    }

    /**
     * Classify an object as a System Object without validation
     * @param obj
     * @returns {boolean}
     */
    fun isSysObject(obj: JSONObject?): Boolean {
        if (obj == null) {
            return false
        }

        // first property should be the subschema
        if (obj.keys().hasNext()) {
            val subSchemaName = obj.keys().next()
            val keys = Schema.system.optJSONObject("properties")?.keys()
            keys?.forEach { key ->
                if (subSchemaName == key) {
                    return true
                }
            }
        }

        return false
    }

    /**
     * Clone a Schema instance by extracting only Schema defined properties
     * Optionally specify a dapSchema to clone a DAP Object
     * @param obj Schema object instance
     * @param dapSchema DapSchema (optional)
     * @returns {*}
     */
    fun fromObject(obj: JSONObject, dapSchema: JSONObject? = null): JSONObject {
        val objCopy = JSONObject(obj.toString())
        return JsonSchemaUtils.extractSchemaObject(objCopy, dapSchema)
    }

    /**
     * Return a hash of a Schema object instance
     * @param obj {object} Schema object instance
     * @param dapSchema {object} DapSchema
     * @returns {*}
     */
    fun toHash(obj: JSONObject, dapSchema: JSONObject?): String {
        return when (obj.keys().next()) {
            "subtx" -> Hash.subtx(obj)
            "blockchainuser" -> Hash.blockchainuser(obj)
            "stheader" -> Hash.stheader(obj)
            "stpacket" -> Hash.stpacket(obj, dapSchema!!)
            "dapcontract" -> Hash.dapcontract(obj)
            "dapschema" -> Hash.dapschema(obj);
            else -> Hash.dapobject(obj, dapSchema!!)
        }
    }

    /**
     * Set's removal action on a Schema Object
     */
    fun prepareForRemoval(schemaObject: JSONObject): JSONObject {
        schemaObject.put(ACT, REMOVE_OBJECT_ACTION)
        schemaObject.put(REV, schemaObject.getInt(REV) + 1)
        return schemaObject
    }

    fun getMeta(obj: JSONObject, key: String): String? {
        return obj.getJSONObject("meta").getString(key)
    }

}