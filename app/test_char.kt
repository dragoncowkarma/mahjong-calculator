fun main() {
    val codePoint = 0x1F007
    val str = buildString {
        if (codePoint <= 0xFFFF) {
            append(codePoint.toChar())
        } else {
            val offset = codePoint - 0x10000
            val high = ((offset ushr 10) + 0xD800).toChar()
            val low = ((offset and 0x3FF) + 0xDC00).toChar()
            append(high)
            append(low)
        }
    }
    println(str)
}
