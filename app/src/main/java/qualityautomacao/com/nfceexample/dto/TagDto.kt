package qualityautomacao.com.nfceexample.dto

data class TagDto(val id: ByteArray, val techList: Array<out String>?) {

    fun uuid(): String {
        return convertTagIdToString(id)
    }

    private fun convertTagIdToString(tagId: ByteArray): String {
        val hexArray = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        return tagId.map {
            val incoming = it.toInt() and 0xff
            val firstByte = incoming shr 4 and 0x0f
            val secondByte = incoming and 0x0f
            hexArray[firstByte] + hexArray[secondByte]
        }.joinToString("")
    }
}
