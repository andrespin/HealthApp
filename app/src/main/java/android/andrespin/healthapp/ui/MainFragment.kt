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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import okhttp3.Dispatcher

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
                R.id.menuSample -> {

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


//lifecycleScope.launch {
//    coroutineScope {
//        val j1 = launch(start = CoroutineStart.LAZY) {
//            for (i in 0 until 10) {
//                delay(100)
//                println(i)
//            }
//        }
//
//        val j2 = launch(start = CoroutineStart.LAZY) {
//            for (i in 10 until 20) {
//                delay(100)
//                println(i)
//            }
//        }
//
//        j1.start()
//        j1.join()
//        j2.start()
//    }
//}