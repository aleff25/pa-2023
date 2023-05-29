interface JsonVisitor {
    fun visit(jsonObject: JsonObject)
    fun visit(jsonArray: JsonArray)
    fun visit(jsonValue: JsonValue)
}

sealed class JsonValue {
    abstract fun accept(visitor: JsonVisitor)
}

class JsonObject(private val properties: Map<String, JsonValue>) : JsonValue() {
    constructor() : this(mutableMapOf())

    fun addProperty(key: String, value: JsonValue) {
        (properties as MutableMap<String, JsonValue>)[key] = value
    }

    fun getProperty(key: String): JsonValue? {
        return properties[key]
    }

    fun getProperties(): Map<String, JsonValue> {
        return properties.toMap()
    }

    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        val entries = properties.entries.joinToString(", ") { "\"${it.key}\": ${it.value}" }
        return "{$entries}"
    }
}

class JsonArray(private val elements: MutableList<JsonValue> = mutableListOf()) : JsonValue() {

    constructor(vararg elements: JsonValue) : this(elements.toMutableList())

    fun addElement(value: JsonValue) {
        elements.add(value)
    }

    fun getElements(): List<JsonValue> {
        return elements.toList()
    }

    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        val elementsString = elements.joinToString(", ")
        return "[$elementsString]"
    }
}

class JsonString(private val value: String) : JsonValue() {
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return "\"$value\""
    }
}

class JsonNumber(private val value: Number) : JsonValue() {
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return value.toString()
    }
}

class JsonBoolean(private val value: Boolean) : JsonValue() {
    override fun accept(visitor: JsonVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return value.toString()
    }
}

class JsonVisitorImpl : JsonVisitor {
    private val searchResults: MutableList<JsonValue> = mutableListOf()

    fun getSearchResults(): List<JsonValue> {
        return searchResults.toList()
    }

    override fun visit(jsonObject: JsonObject) {
        jsonObject.getProperties().values.forEach { it.accept(this) }
    }

    override fun visit(jsonArray: JsonArray) {
        jsonArray.getElements().forEach { it.accept(this) }
    }

    override fun visit(jsonValue: JsonValue) {
        // Implementar ações de pesquisa aqui
        searchResults.add(jsonValue)
    }
}
