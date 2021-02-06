package com.example.weighttracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val fileName: String = "weightfile.txt"
    lateinit var contents: List<String>

    //TODO: Make actualweightText, weighText -> avg weight

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Get contents of the file as List<String>
        try {
            contents = this.openFileInput(fileName).bufferedReader().readLines()
        } catch (e: Exception) {
            contents = listOf()
        }

        if (contents.isNotEmpty()) {
            val weightText: TextView = findViewById(R.id.avg_weight_text)
            weightText.text = calcSevenDayAvgToString(contents)
        }

        val addButton: Button = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            addWeight()
        }

    }

    private fun addWeight() {
        val addWeightText: EditText = findViewById(R.id.add_weight_text)
        val weightText: TextView = findViewById(R.id.avg_weight_text)
        val currentWeightText: TextView = findViewById(R.id.current_weight_text)
        val newContents = mutableListOf<String>()
        newContents.addAll(contents)

        val addWeight = addWeightText.text
        if (addWeight.isNotBlank()) {
            currentWeightText.text = addWeight
            newContents.add(addWeight.toString())
            if (contents.isNotEmpty()) {
                weightText.text = calcSevenDayAvgToString(newContents)
            } else {
                weightText.text = addWeight
            }
            update(newContents)
        }
    }

    private fun update(newContents: MutableList<String>) {
        this.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(newContents.toSaveableString().toByteArray())
        }
        contents = newContents
    }

    private fun List<String>.toSaveableString(): String {
        var result = ""
        for (value in this) {
            result += value + "\n"
        }
        return result
    }

    private fun calcSevenDayAvgToString(contents: List<String>): String {
        var avgWeight = 0.0
        var counter = 0
        for (value in contents.reversed()) {
            if (counter >= 7) {
                break
            }
            avgWeight += value.toDouble()
            counter++
        }
        avgWeight /= if (contents.size < 7) contents.size else 7
        return String.format("%.1f", avgWeight)
    }
}