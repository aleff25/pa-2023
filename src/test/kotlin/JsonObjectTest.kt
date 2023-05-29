
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class JsonObjectTest {

    @Test
    fun testJsonObjectAddPropertyGetPropertyReturnsAddedValue() {
        val jsonObject = JsonObject()
        val propertyKey = "property"
        val propertyValue = JsonString("value")

        jsonObject.addProperty(propertyKey, propertyValue)

        val retrievedValue = jsonObject.getProperty(propertyKey)
        assertEquals(propertyValue, retrievedValue)
    }

    @Test
    fun testJsonObjectToStringReturnsValidJsonString() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("property1", JsonString("value1"))
        jsonObject.addProperty("property2", JsonNumber(42))

        val jsonString = jsonObject.toString()
        assertEquals("{\"property1\": \"value1\", \"property2\": 42}", jsonString)
    }

    @Test
    fun testJsonArrayAddElementGetElementsReturnsAddedElement() {
        val jsonArray = JsonArray()
        val element = JsonBoolean(true)

        jsonArray.addElement(element)

        val elements = jsonArray.getElements()
        assertEquals(1, elements.size)
        assertTrue(elements.contains(element))
    }

    @Test
    fun testJsonArrayToStringReturnsValidJsonString() {
        val jsonArray = JsonArray(
            JsonString("value1"),
            JsonNumber(42),
            JsonBoolean(true)
        )

        val jsonString = jsonArray.toString()
        assertEquals("[\"value1\", 42, true]", jsonString)
    }

    @Test
    fun testJsonStringToStringReturnsValidJsonString() {
        val jsonString = JsonString("Hello, World!")

        val stringValue = jsonString.toString()
        assertEquals("\"Hello, World!\"", stringValue)
    }

    @Test
    fun testJsonNumberToStringReturnsValidJsonString() {
        val jsonNumber = JsonNumber(42)

        val numberValue = jsonNumber.toString()
        assertEquals("42", numberValue)
    }

    @Test
    fun testJsonBooleanToStringReturnsValidJsonString() {
        val jsonBoolean = JsonBoolean(true)

        val booleanValue = jsonBoolean.toString()
        assertEquals("true", booleanValue)
    }

    @Test
    fun testJsonValueVisitor() {
        val visitor = JsonVisitorImpl()
        val jsonObject = JsonObject()

        val jsonValue1 = JsonString("value1")
        val jsonValue2 = JsonString("value2")
        jsonObject.addProperty("property1", jsonValue1)
        jsonObject.addProperty("property2", jsonValue2)

        visitor.visit(jsonObject)

        val searchResults = visitor.getSearchResults()
        assertEquals(2, searchResults.size)
        assertTrue(searchResults.contains(jsonValue1))
        assertTrue(searchResults.contains(jsonValue2))
    }

    @Test
    fun testJsonObjectVisitor() {
        val visitor = JsonVisitorImpl()
        val jsonValue = JsonString("value")

        visitor.visit(jsonValue)

        val searchResults = visitor.getSearchResults()
        assertEquals(1, searchResults.size)
        assertTrue(searchResults.contains(jsonValue))
    }

    @Test
    fun testJsonArrayVisitor() {
        val visitor = JsonVisitorImpl()
        val jsonArray = JsonArray()

        val jsonValue1 = JsonString("value1")
        val jsonValue2 = JsonString("value2")
        jsonArray.addElement(jsonValue1)
        jsonArray.addElement(jsonValue2)

        visitor.visit(jsonArray)

        val searchResults = visitor.getSearchResults()
        assertEquals(2, searchResults.size)
        assertTrue(searchResults.contains(jsonValue1))
        assertTrue(searchResults.contains(jsonValue2))
    }
}