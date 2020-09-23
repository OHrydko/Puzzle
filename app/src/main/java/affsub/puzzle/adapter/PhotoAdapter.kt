package affsub.puzzle.adapter

import affsub.puzzle.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*


class PhotoAdapter(private val photo: ArrayList<Int>, private val listener: Listener) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    interface Listener {
        fun click(position: Int)
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViews: ImageView = itemView.imageView
        val layout: LinearLayout = itemView.layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photo.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.imageViews.setImageResource(photo[position])
        holder.layout.setOnClickListener { listener.click(position) }
    }
}