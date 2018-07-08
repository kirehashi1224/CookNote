package jp.ac.titech.itpro.sdl.cooknote.model

data class Ingredients(val ingredients: MutableList<String> = mutableListOf()){
    fun toString(delimiter: String): String {
        var retStr = StringBuilder()

        for ((index, value) in ingredients.withIndex()){
            if(index == 0){
                retStr.append(value)
            }else{
                retStr.append(delimiter).append(value)
            }
        }
        return retStr.toString()
    }
}