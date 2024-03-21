package com.android.movieapp.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.movieapp.R
import com.android.movieapp.data.models.MovieDetailsResponse
import com.android.movieapp.databinding.FragmentMovieDetailsBinding
import com.android.movieapp.utils.NetworkResult
import com.android.movieapp.viewmodels.MovieViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val movieVM: MovieViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(args.imdbID.isNotEmpty()){
            observeGetMovieDetails()
            getMovieDetails(args.imdbID)
        }

    }

    private fun getMovieDetails(imdbID: String) {
movieVM.getMovieDetails(imdbID)
    }

    private fun observeGetMovieDetails() {
        movieVM.movieDetailsLD.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Error -> {
                    binding.progress.root.visibility = View.GONE
                    response.message?.let { message ->
                        Toast.makeText(
                            requireActivity(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        if(message.isNotEmpty()){
                            binding.toastTvDetails.text = message
                        }else{
                            binding.toastTvDetails.text= getString(R.string.something_went)
                        }

                    }
                }

                is NetworkResult.Loading -> {
                    binding.progress.root.visibility = View.VISIBLE
                }

                is NetworkResult.Success -> {
                    binding.progress.root.visibility = View.GONE
                    if(response.data!=null){
                        Log.d("RESPONSE", response.data.toString())
                        setDetails(response.data)
                    }


                }
            }
        }
    }

    private fun setDetails(data: MovieDetailsResponse) {
binding.movieDetailsTitle.text = data.Title
binding.movieDetailsPlotTitleTv.text = "StoryLine:"
binding.movieDetailsGenre.text = createStyledString("Genre: ",data.Genre)
binding.movieDetailsDirector.text =createStyledString("Director: ",data.Director)
binding.movieDetailsWriter.text = createStyledString("Writer: ",data.Writer)
binding.movieDetailsActors.text =createStyledString("Actors: ",data.Actors)
binding.movieDetailsLanguage.text = createStyledString("Language: ",data.Language)
binding.movieDetailsImdbRating.text = createStyledString("IMDBRating: ",data.imdbRating)
binding.movieDetailsImdbVotes.text = createStyledString("IMDBVotes: ",data.imdbVotes)
binding.movieDetailsPlotTv.text = data.Plot

        Glide.with(requireActivity()).load(data.Poster).into(binding.imageView)
        Glide.with(requireActivity()).load(data.Poster).into(binding.imageView2)
    }

    fun createStyledString(title: String, matter: String): SpannableStringBuilder {
        val fullText = "$title$matter"

        // Create a SpannableStringBuilder
        val spannableStringBuilder = SpannableStringBuilder(fullText)

        // Apply bold style to the first part
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableStringBuilder.setSpan(boldSpan, 0, title.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply different color to the first part
        val colorSpan = ForegroundColorSpan(resources.getColor(R.color.dark_peach))
        spannableStringBuilder.setSpan(colorSpan, 0, title.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannableStringBuilder
    }



}