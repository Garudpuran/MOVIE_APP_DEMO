package com.android.movieapp.ui.fragments

import MovieListAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.movieapp.data.models.Search
import com.android.movieapp.databinding.FragmentMovieListBinding
import com.android.movieapp.utils.NetworkResult
import com.android.movieapp.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieListAdapter.MoviesClickListener {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MovieListAdapter
    private var mList = mutableListOf<Search>()
    private val movieVM: MovieViewModel by viewModels()
    private val args: MovieListFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeGetMovies()
        adapter = MovieListAdapter(this)
        if(movieVM.movieLiveData.value ==null){
            baseSearch(args.searchWord)
        }
        binding.movieRC.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= 10
                ) {
                   baseSearch(args.searchWord)
                }
            }
        })


    }

    private fun baseSearch(searchWord: String) {
        movieVM.getMovies( searchWord)
    }

    private fun observeGetMovies() {
        movieVM.movieLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> {
                    binding.progress.root.visibility = View.GONE
                    response.message?.let { message ->
                        Toast.makeText(
                            requireActivity(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.toastTv.text = message
                        binding.toastTv.visibility = View.VISIBLE
                        binding.offlineBtn.visibility = View.VISIBLE
                    }
                }

                is NetworkResult.Loading -> {
                    binding.toastTv.visibility = View.GONE
                    binding.offlineBtn.visibility = View.GONE
                    binding.progress.root.visibility = View.VISIBLE
                }

                is NetworkResult.Success -> {
                    binding.progress.root.visibility = View.GONE
                    binding.offlineBtn.visibility = View.GONE
                    binding.toastTv.visibility = View.GONE
                    if(response.data!=null){
                        Log.d("RESPONSE", response.data.size.toString())
                        setRcView(response.data)
                    }


                }
            }
        }
    }

    private fun setRcView(data: List<Search>) {
        val lm = binding.movieRC.layoutManager as LinearLayoutManager
        val currentPosition = lm.findFirstVisibleItemPosition()
        mList.addAll(data)
        adapter.setData(mList)
        binding.movieRC.adapter = adapter
        lm.scrollToPosition(currentPosition)
    }

    override fun onMoviesItemClicked(item: Search) {
        val action =
            MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(item.imdbID)
        findNavController().navigate(action)
    }

}