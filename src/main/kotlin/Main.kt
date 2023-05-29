import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    val jsonObject = JsonObject().apply {
        addProperty("nome", JsonString("Alice"))
        addProperty("idade", JsonNumber(25))
        addProperty("email", JsonString("alice@example.com"))
        addProperty("inscritos", JsonArray().apply {
            addElement(JsonObject().apply {
                addProperty("nome", JsonString("Bob"))
                addProperty("numero", JsonNumber(123))
            })
            addElement(JsonObject().apply {
                addProperty("nome", JsonString("Charlie"))
                addProperty("numero", JsonNumber(456))
            })
            addElement(JsonObject().apply {
                addProperty("nome", JsonString("David"))
                addProperty("numero", JsonNumber(789))
            })
        })
    }

    val visitor = JsonVisitorImpl()
    jsonObject.accept(visitor)
    val searchResults = visitor.getSearchResults()

    // Exibir os resultados da pesquisa
    for (result in searchResults) {
        println(result)
    }

    println("Json: ${jsonObject.getProperties()}")

    SwingUtilities.invokeLater {
        val frame = JsonEditor()
        frame.isVisible = true
    }

}