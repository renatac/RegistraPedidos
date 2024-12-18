package br.com.lucramaisagenciadigital.registrapedidos.database.utils

import com.google.gson.Gson

inline fun <reified T: Any> T.toJson(): String = Gson().toJson(this, T::class.java)
inline fun <reified T: Any> String.fromJson(): T = Gson().fromJson(this, T::class.java)