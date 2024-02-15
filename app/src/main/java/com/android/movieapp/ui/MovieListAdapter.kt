import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.movieapp.R
import com.android.movieapp.data.models.Search
import com.bumptech.glide.Glide

class MovieListAdapter(private val mListener: MoviesClickListener) :
    RecyclerView.Adapter<MovieListAdapter.ItemViewHolder>() {

    private val moviesSet = ArrayList<Search>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moviePoster: ImageView = view.findViewById(R.id.movie_poster)
        val titleTv: TextView = view.findViewById(R.id.movie_title)
        val movieYear: TextView = view.findViewById(R.id.movie_year)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return moviesSet.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = moviesSet[position]
        holder.titleTv.text = item.Title
        holder.movieYear.text = "Released: ${item.Year}"
        holder.titleTv.text = item.Title

        Glide.with(holder.itemView.context)
            .load(item.Poster)
            .into(holder.moviePoster)

        holder.itemView.setOnClickListener {
            mListener.onMoviesItemClicked(item)
        }
    }

    fun setData(newMovies: List<Search>) {
        val diffCallback = MoviesDiffCallback(moviesSet, newMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        moviesSet.clear()
        moviesSet.addAll(newMovies)

        diffResult.dispatchUpdatesTo(this)
    }

    interface MoviesClickListener {
        fun onMoviesItemClicked(item: Search)
    }

    private class MoviesDiffCallback(
        private val oldList: List<Search>,
        private val newList: List<Search>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].imdbID == newList[newItemPosition].imdbID
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
