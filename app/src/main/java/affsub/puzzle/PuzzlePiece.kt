package affsub.puzzle

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView

class PuzzlePiece(context: Context?) : AppCompatImageView(context) {
    var x = 0
    var y = 0
    var pieceWidth = 0
    var pieceHeight = 0
    var canMove = true
}