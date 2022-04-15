package android.andrespin.healthapp.ui

import android.andrespin.healthapp.MainIntent
import android.andrespin.healthapp.MainState
import android.andrespin.healthapp.R
import android.andrespin.healthapp.databinding.MainFragmentBinding
import android.andrespin.healthapp.model.NoteData
import android.andrespin.healthapp.ui.adapter.DatesAdapter
import android.andrespin.healthapp.viewmodel.MainViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    private val adapter: DatesAdapter by lazy { DatesAdapter(requireContext(), this, viewModel) }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        initToolBar()
        observeViewModel()
        observeCurrentBackStackEntry()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun observeCurrentBackStackEntry() {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<NoteData>("key")
            ?.observe(
                viewLifecycleOwner
            ) {
                lifecycleScope.launch {
                    viewModel.intent.send(MainIntent.SaveAndDisplay(it))
                }
            }
    }

    private fun initAdapter() {
        binding.rvDate.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDate.adapter = adapter
    }

    private fun initToolBar() {
        binding.mainToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuAdd -> {
                    findNavController().navigate(R.id.action_notes_to_dialogAddNote)
                    true
                }
                else -> false
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                    }
                    is MainState.Loading -> {
                        renderLoading()
                    }
                    is MainState.Data -> {
                        renderData(it)
                    }
                    is MainState.Error -> {
                        renderError(it)
                    }
                }
            }
        }
    }

    private fun renderError(it: MainState.Error) {

    }

    private fun renderData(it: MainState.Data) {
        adapter.setData(it.data)
    }

    private fun renderLoading() {

    }

}