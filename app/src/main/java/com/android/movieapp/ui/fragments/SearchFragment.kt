package com.android.movieapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.movieapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchWord = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.catFragSearchEt.addTextChangedListener{
            val s = it?.trim()
            searchWord = if (s!!.isNotEmpty()) {
                s.toString()
            } else {
                ""
            }
        }

        binding.searchBtn.setOnClickListener {
            if(searchWord.isNotEmpty()){
                val action = SearchFragmentDirections.actionSearchFragmentToMovieListFragment(searchWord)
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireActivity(),"Enter movie name!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}


