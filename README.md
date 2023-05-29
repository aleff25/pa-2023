# JsonVisitor - Classe para visitar e manipular estruturas JSON

A classe `JsonVisitor` e suas classes relacionadas são uma implementação simplificada de uma estrutura para visitar e manipular objetos JSON. Elas fornecem uma maneira flexível de percorrer uma estrutura JSON e realizar ações específicas em cada elemento encontrado.

## Uso básico

### Criando objetos JSON

Para criar um objeto JSON, você pode usar a classe `JsonObject`:

```kotlin
val jsonObject = JsonObject()
```

A classe `JsonObject` possui métodos para adicionar propriedades ao objeto:

```kotlin
jsonObject.addProperty("nome", JsonString("João"))
jsonObject.addProperty("idade", JsonNumber(25))
```

Você também pode criar um array JSON usando a classe `JsonArray`:

```kotlin
val jsonArray = JsonArray()
```

Para adicionar elementos ao array, use o método `addElement`:

```kotlin
jsonArray.addElement(JsonString("apple"))
jsonArray.addElement(JsonString("banana"))
jsonArray.addElement(JsonString("orange"))
```

### Visitando elementos JSON

Para percorrer e visitar todos os elementos de um objeto ou array JSON, você pode criar uma implementação de `JsonVisitor`. Aqui está um exemplo usando a classe `JsonVisitorImpl` fornecida:

```kotlin
val visitor = JsonVisitorImpl()
jsonObject.accept(visitor)
```

Após a visita, você pode obter os resultados da pesquisa usando o método `getSearchResults`:

```kotlin
val searchResults = visitor.getSearchResults()
```

### Exemplos de uso

#### Exemplo 1: Procurando por valores específicos

Suponha que você tenha um objeto JSON complexo e queira encontrar todos os valores booleanos verdadeiros (true) dentro dele. Você pode usar `JsonVisitorImpl` para fazer isso:

```kotlin
val jsonObject = JsonObject()
jsonObject.addProperty("name", JsonString("John"))
jsonObject.addProperty("age", JsonNumber(25))
jsonObject.addProperty("isStudent", JsonBoolean(true))
jsonObject.addProperty("hasCar", JsonBoolean(false))

val visitor = JsonVisitorImpl()
jsonObject.accept(visitor)

val searchResults = visitor.getSearchResults().filterIsInstance<JsonBoolean>()
val trueBooleans = searchResults.filter { it.value }

println(trueBooleans)  // Output: [true]
```

#### Exemplo 2: Convertendo um objeto JSON em uma string

Suponha que você tenha um objeto JSON e deseje convertê-lo em uma representação de string. A classe `JsonValue` tem um método `toString()` que retorna uma string JSON válida:

```kotlin
val jsonObject = JsonObject()
jsonObject.addProperty("name", JsonString("John"))
jsonObject.addProperty("age", JsonNumber(25))
jsonObject.addProperty("isStudent", JsonBoolean(true))

val jsonString = jsonObject.toString()

println(jsonString)
// Output: {"name": "John", "age": 25, "isStudent": true}
```

Esses são apenas exemplos básicos de uso da classe `JsonVisitor`. Você pode estender e personalizar a classe para realizar diferentes operações em estruturas JSON de acordo com suas necessidades.
 
 # JsonEditor - Editor de JSON com interface gráfica

A classe `JsonEditor` é uma implementação de um editor de JSON com interface gráfica usando a biblioteca `javax.swing`. Ela fornece uma janela com funcionalidades para adicionar e remover campos de um objeto JSON e exibir uma visualização atualizada do JSON resultante.

## Uso básico

### Executando o editor de JSON

Para iniciar o editor de JSON, você precisa criar uma instância da classe `JsonEditor` e exibi-la na tela. Aqui está um exemplo de como fazer isso:

```kotlin
fun main() {
    val editor = JsonEditor()
    editor.isVisible = true
}
```

Ao executar o código acima, o editor de JSON será aberto em uma janela.

### Adicionando campos

O editor de JSON permite adicionar campos a um objeto JSON. Para adicionar um campo, clique no botão "Add Field" na parte superior da janela. Um novo campo será adicionado na interface, onde você pode inserir a chave e o valor do campo.

### Removendo campos

Se você deseja remover um campo adicionado anteriormente, clique no botão "Remove Field" na parte inferior da janela. O campo correspondente será removido da interface.

### Visualizando o JSON

À medida que você adiciona ou remove campos, o editor de JSON exibe uma visualização atualizada do JSON resultante na área de texto à direita da janela. O JSON será exibido em formato de string, com as chaves e valores corretamente formatados.

### Conversão de tipos

Ao inserir um valor em um campo, o editor de JSON tentará converter automaticamente o valor para o tipo adequado no objeto JSON. Ele suporta valores numéricos, booleanos e strings. Se você inserir um valor entre colchetes `[ ]`, o editor interpretará como um array JSON. Se você inserir um valor entre chaves `{ }`, o editor interpretará como um objeto JSON.

## Considerações finais

A classe `JsonVisitor` e suas classes relacionadas fornecem uma abordagem flexível e modular para visitar e manipular estruturas JSON. Elas podem ser úteis ao lidar com a análise e manipulação de dados JSON em aplicativos Kotlin. Sinta-se à vontade para adaptar e estender essas classes conforme necessário para atender aos requisitos
específicos do seu projeto.
O `JsonEditor` fornece uma interface simples e interativa para adicionar e remover campos de um objeto JSON e visualizar o JSON resultante. Ele pode ser útil para tarefas de edição e manipulação de JSON em aplicativos Kotlin. Sinta-se à vontade para personalizar e estender essa classe de acordo com as necessidades específicas do seu projeto.
