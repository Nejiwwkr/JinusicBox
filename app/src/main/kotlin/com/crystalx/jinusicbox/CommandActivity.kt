package com.crystalx.jinusicbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.crystalx.jinusicbox.Token.Companion.checkToken
import java.util.*

class CommandActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command)

        findViewById<ImageView>(R.id.command_back).setOnClickListener{
            this.finish()
        }

        val inputView = findViewById<EditText>(R.id.command_input)
        val outputView = findViewById<TextView>(R.id.command_output)
        val startButton = findViewById<Button>(R.id.command_start)

        startButton.setOnClickListener{
            outputView.text = runCommand(inputView.text.toString())
        }
    }
}

class Token(val value: String, val type: TokenType) {
    companion object {
        @JvmStatic
        fun checkToken(input: String): Token {
            return when (input) {
                "/" -> Token("/", TokenType.Initial)
                "-" -> Token("-", TokenType.Initial)
                "set" -> Token("set", TokenType.Command)
                "button" -> Token("button", TokenType.Clause)
                "disable" -> Token("disable", TokenType.Object)
                "enable" -> Token("enable", TokenType.Object)
                else -> throw IllegalTokenException
            }
        }
    }

    override fun equals(other: Any?): Boolean = other is Token && other.value == this.value && other.type == this.type

    enum class TokenType {
        Initial, Command, Clause, Object
    }

    object IllegalTokenException: Exception()
}

fun runCommand(input: String): String {
    val inputStream = input.split(" ").map{it.lowercase(Locale.ROOT)}
    val tokenStream = inputStream.map{
        try {
            checkToken(it)
        }catch (e: Token.IllegalTokenException){
            return "不合法的令牌: \"$it\""
        }
    }

    if (tokenStream.any{it.type == Token.TokenType.Initial}) {
        if (tokenStream.any{it == Token("/", Token.TokenType.Initial)}) {

        }else if (tokenStream.any{it == Token("/", Token.TokenType.Initial)}) {

        }
    }else {
        return "缺少起始符"
    }

    return "成功实现"
}

fun processTokens(tokens: Token){

}