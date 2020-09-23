package affsub.puzzle.view

import affsub.puzzle.PuzzlePiece
import affsub.puzzle.R
import affsub.puzzle.TouchListener
import affsub.puzzle.vm.MainViewModel
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import java.util.*
import kotlin.math.roundToInt


class PuzzleFragment : Fragment() {
    lateinit var pieces: ArrayList<PuzzlePiece>
    private lateinit var layout: RelativeLayout
    private lateinit var imageView: ImageView
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_puzzle, container, false)
        layout = view.findViewById(R.id.layout)
        imageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        imageView.setImageResource(viewModel.getImageFromList())
        imageView.post {
            pieces = splitImage()
            pieces.shuffle()
            val touchListener = TouchListener(this)
            for (piece in pieces) {
                val layoutParams = LinearLayout.LayoutParams(piece.pieceWidth, piece.pieceHeight)
                piece.layoutParams = layoutParams
                piece.setOnTouchListener(touchListener)
                if (piece.parent != null) {
                    (piece.parent as ViewGroup).removeView(piece)
                }
                layout.addView(piece)
            }
        }
    }

    private fun splitImage(): ArrayList<PuzzlePiece> {
        val piecesNumber = 12
        val rows = 4
        val cols = 3
        val pieces: ArrayList<PuzzlePiece> = ArrayList(piecesNumber)

        // Get the scaled bitmap of the source image
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val dimensions = getBitmapPositionInsideImageView(imageView)
        val scaledBitmapLeft = dimensions!![0]
        val scaledBitmapTop = dimensions[1]
        val scaledBitmapWidth = dimensions[2]
        val scaledBitmapHeight = dimensions[3]
        val croppedImageWidth: Int = scaledBitmapWidth - 2 * kotlin.math.abs(scaledBitmapLeft)
        val croppedImageHeight: Int = scaledBitmapHeight - 2 * kotlin.math.abs(scaledBitmapTop)
        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true)
        val croppedBitmap = Bitmap.createBitmap(
            scaledBitmap,
            kotlin.math.abs(scaledBitmapLeft),
            kotlin.math.abs(scaledBitmapTop),
            croppedImageWidth,
            croppedImageHeight
        )

        // Calculate the with and height of the pieces
        val pieceWidth = croppedImageWidth / cols
        val pieceHeight = croppedImageHeight / rows

        // Create each bitmap piece and add it to the resulting array
        var y = 0
        for (row in 0 until rows) {
            var x = 0
            for (col in 0 until cols) {
                val pieceBitmap =
                    Bitmap.createBitmap(croppedBitmap, x, y, pieceWidth, pieceHeight)
                val piece = PuzzlePiece(activity)
                piece.setImageBitmap(pieceBitmap)
                piece.x = x + imageView.left
                piece.y = y + imageView.top
                piece.pieceWidth = pieceWidth
                piece.pieceHeight = pieceHeight
                pieces.add(piece)
                pieces.add(piece)
                x += pieceWidth
            }
            y += pieceHeight

        }
        return pieces
    }

    private fun getBitmapPositionInsideImageView(imageView: ImageView?): IntArray? {
        val ret = IntArray(4)
        if (imageView == null || imageView.drawable == null) return ret

        // Get image dimensions
        // Get image matrix values and place them in an array
        val f = FloatArray(9)
        imageView.imageMatrix.getValues(f)

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        val d = imageView.drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        // Calculate the actual dimensions
        val actW = (origW * scaleX).roundToInt()
        val actH = (origH * scaleY).roundToInt()
        ret[2] = actW
        ret[3] = actH

        // Get image position
        // We assume that the image is centered into ImageView
        val imgViewW = imageView.width
        val imgViewH = imageView.height
        val top = (imgViewH - actH) / 2
        val left = (imgViewW - actW) / 2
        ret[0] = left
        ret[1] = top
        return ret
    }

    fun checkGameOver() {
        if (isGameOver()) {
            Toast.makeText(activity,"Congratulations!!! You are WIN!!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun isGameOver(): Boolean {
        for (piece in pieces) {
            if (piece.canMove) {
                return false
            }
        }
        return true
    }

}