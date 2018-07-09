package qualityautomacao.com.nfceexample.dto

data class TagDto(val id: ByteArray, val techList: Array<out String>?) {

    fun uuid(): String {
        return convertTagIdToString(id)
    }

    private fun convertTagIdToString(tagId: ByteArray): String {
        return tagId.map {
            (it.toInt() and 0xff).toString(16)
        }.joinToString("")
    }
}
