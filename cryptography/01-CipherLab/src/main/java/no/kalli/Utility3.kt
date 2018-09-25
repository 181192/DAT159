package no.kalli

private val a = 5
private val b = 7
private val m = 26

fun affineEncrypt(input: ByteArray): ByteArray {

    for (i in input.indices)
        input[i] = encryptThis(input[i].toInt()).toByte()

    return input
}

fun affineDecrypt(input: ByteArray): ByteArray {

    for (i in input.indices)
        input[i] = decryptThis(input[i].toInt()).toByte()

    return input
}

private fun encryptThis(i: Int): Int {
    return 'A'.toInt() + (a * (i + 'A'.toInt()) + b) % m
}

private fun decryptThis(i: Int): Int {
    val x = modInverse(a, m) * (i - 'A'.toInt() - b) % m
    return if (x < 0) (x + m) % m + 'A'.toInt() else x + 'A'.toInt()
}

private fun modInverse(a: Int, m: Int): Int {
    var a = a
    a = a % m
    for (x in 1 until m)
        if (a * x % m == 1)
            return x
    return 1
}

fun gcd(a: Int, b: Int): Int {
    // Everything divides 0
    if (a == 0 || b == 0)
        return 0

    // base case
    if (a == b)
        return a

    // a is greater
    return if (a > b) gcd(a - b, b) else gcd(a, b - a)
}


fun main(args: Array<String>) {
    val test = "ILOVEPIZZA".toByteArray()
    val encrypted = affineEncrypt(test)
    val decrypted = affineDecrypt(encrypted)

    println(String(encrypted))
    println(String(decrypted))
}