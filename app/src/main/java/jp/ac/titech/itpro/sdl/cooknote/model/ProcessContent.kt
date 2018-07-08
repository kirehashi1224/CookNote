package jp.ac.titech.itpro.sdl.cooknote.model

import jp.ac.titech.itpro.sdl.cooknote.R.id.process

data class ProcessContent(val text: String){
    /*fun toString(delimiter: String): String {
        var retStr = StringBuilder()


        for ((index, value) in process.withIndex()){
            if(index == 0){
                retStr.append(value)
            }else{
                retStr.append(delimiter).append(value)
            }
        }
        return retStr.toString()
    }*/
}