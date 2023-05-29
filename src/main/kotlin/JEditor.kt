import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.event.ActionListener
import java.util.*
import javax.swing.*
import javax.swing.border.EmptyBorder


class JsonEditor : JFrame() {
    private val contentPane: JPanel
    private val fieldsPanel: JPanel
    private val addButton: JButton
    private val removeButton: JButton
    private val jsonPreviewTextArea: JTextArea
    private val jsonStack: Stack<JsonObject> = Stack()
    private val fieldList: MutableList<JsonField> = mutableListOf()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(450, 300)
        title = "JSON Editor"

        contentPane = JPanel()
        contentPane.border = EmptyBorder(5, 5, 5, 5)
        contentPane.layout = BorderLayout(0, 0)
        setContentPane(contentPane)

        fieldsPanel = JPanel()
        fieldsPanel.layout = BoxLayout(fieldsPanel, BoxLayout.Y_AXIS)
        contentPane.add(fieldsPanel, BorderLayout.CENTER)

        addButton = JButton("Add Field")
        addButton.addActionListener {
            addField()
        }
        contentPane.add(addButton, BorderLayout.NORTH)

        removeButton = JButton("Remove Field")
        removeButton.addActionListener {
            removeField()
        }
        contentPane.add(removeButton, BorderLayout.SOUTH)

        jsonPreviewTextArea = JTextArea(5, 15)
        jsonPreviewTextArea.isEditable = false
        contentPane.add(JScrollPane(jsonPreviewTextArea), BorderLayout.EAST)

        // Adiciona um objeto JSON inicial vazio
        addField()

        updateJsonPreview()
    }

    private fun addField() {
        val keyField = JTextField(8)
        val valueField = JTextField(15)
        val removeFieldButton = JButton("Remove")
        val fieldPanel = JPanel(FlowLayout(FlowLayout.LEFT))

        fieldPanel.add(JLabel("Key: "))
        fieldPanel.add(keyField)
        fieldPanel.add(JLabel("Value: "))
        fieldPanel.add(valueField)
        fieldPanel.add(removeFieldButton)

        fieldsPanel.add(fieldPanel)
        fieldsPanel.revalidate()
        fieldsPanel.repaint()

        val jsonField = JsonField(keyField, valueField)
        fieldList.add(jsonField)

        val actionListener = ActionListener {
            updateJsonPreview()
        }

        keyField.addActionListener(actionListener)
        valueField.addActionListener(actionListener)

        removeFieldButton.addActionListener {
            fieldsPanel.remove(fieldPanel)
            fieldsPanel.revalidate()
            fieldsPanel.repaint()
            fieldList.remove(jsonField)
            updateJsonPreview()
        }
    }

    private fun removeField() {
        if (fieldList.isNotEmpty()) {
            fieldsPanel.remove(fieldsPanel.componentCount - 1)
            fieldsPanel.revalidate()
            fieldsPanel.repaint()
            fieldList.removeAt(fieldList.size - 1)
            updateJsonPreview()
        }
    }

    private fun updateJsonPreview() {
        val jsonObject = JsonObject()
        for (field in fieldList) {
            val key = field.keyField.text
            val value = parseJsonValue(field.valueField.text)
            jsonObject.addProperty(key, value)
        }

        jsonStack.clear()
        jsonStack.push(jsonObject)
        jsonPreviewTextArea.text = jsonObject.toString()
    }

    private fun parseJsonValue(value: String): JsonValue {
        return if (value.startsWith("[") && value.endsWith("]")) {
            val elements = value.substring(1, value.length - 1)
                .split(",")
                .map { parseJsonValue(it.trim()) }
            JsonArray(elements.toMutableList())
        } else if (value.startsWith("{") && value.endsWith("}")) {
            val keyValuePairs = value.substring(1, value.length - 1)
                .split(",")
                .associate {
                    val (key, value) = it.split(":")
                    Pair(key.trim(), parseJsonValue(value.trim()))
                }
            JsonObject(keyValuePairs)
        } else if (value == "true" || value == "false") {
            JsonBoolean(value.toBoolean())
        } else if (value.toIntOrNull() != null) {
            JsonNumber(value.toInt())
        } else if (value.toDoubleOrNull() != null) {
            JsonNumber(value.toDouble())
        } else {
            JsonString(value)
        }
    }

    data class JsonField(val keyField: JTextField, val valueField: JTextField)
}
