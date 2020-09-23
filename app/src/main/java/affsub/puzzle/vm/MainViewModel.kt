package affsub.puzzle.vm

import affsub.puzzle.R
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var listPhoto: ArrayList<Int>
    var position: Int = 0

    init {
        listPhoto = populate()
    }

    private fun populate(): ArrayList<Int> {
        val arrayList: ArrayList<Int> = ArrayList()
        arrayList.add(R.drawable.photo)
        arrayList.add(R.drawable.photo1)
        arrayList.add(R.drawable.photo3)
        arrayList.add(R.drawable.photo4)
        arrayList.add(R.drawable.photo5)
        arrayList.add(R.drawable.photo6)
        arrayList.add(R.drawable.photo7)
        arrayList.add(R.drawable.photo8)
        arrayList.add(R.drawable.photo9)
        arrayList.add(R.drawable.photo10)
        return arrayList
    }

    fun getImageFromList(): Int {
        return listPhoto[position]
    }
}