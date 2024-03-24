package com.example.demomarvel.data.utill
import java.math.BigInteger
import java.security.MessageDigest

class Md5HashGenerator {

    fun getCurrentTimestamp(): Long {
        return System.currentTimeMillis() / 1000
    }

    private fun calculateMD5(ts: Long, privateKey: String, publicKey: String): String {
        val concatenatedString = "$ts$privateKey$publicKey"
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(concatenatedString.toByteArray())
        val md5Hash = BigInteger(1, digest).toString(16)
        return md5Hash.padStart(32, '0')
    }

    fun generateHash(privateKey: String, publicKey: String): String {
        val timestamp = getCurrentTimestamp()
        return calculateMD5(timestamp, privateKey, publicKey)
    }
}