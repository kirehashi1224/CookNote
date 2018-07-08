package jp.ac.titech.itpro.sdl.cooknote.model

data class Process(val process: MutableList<String> = mutableListOf()){
    fun toString(delimiter: String): String {
        var retStr = StringBuilder()

        for ((index, value) in process.withIndex()){
            if(index == 0){
                retStr.append(value)
            }else{
                retStr.append(delimiter).append(value)
            }
        }
        return retStr.toString()
    }
}