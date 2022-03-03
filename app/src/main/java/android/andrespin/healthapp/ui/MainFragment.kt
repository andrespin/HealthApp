package android.andrespin.healthapp.ui

import android.andrespin.healthapp.EventState
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    private val adapter: DatesAdapter by lazy { DatesAdapter(requireContext()) }

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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initAdapter()
        initToolBar()
        observeViewModel()

        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<NoteData>("key")
            ?.observe(
                viewLifecycleOwner
            ) {
                println("data of note $it")
                lifecycleScope.launch {
                    viewModel.intent.send(MainIntent.SaveData(it))
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
                R.id.menuSample -> {
                    lifecycleScope.launch {
                        viewModel.intent.send(MainIntent.AddNote)
                    }
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

        lifecycleScope.launch {
            viewModel.eventState.collect {
                when (it) {
                    is EventState.Idle -> {
                    }
                    is EventState.OpenDialog -> {
                        findNavController().navigate(R.id.action_notes_to_dialogAddNote)
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