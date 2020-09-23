package affsub.puzzle.view

import affsub.puzzle.R
import affsub.puzzle.adapter.PhotoAdapter
import affsub.puzzle.vm.MainViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment(), PhotoAdapter.Listener {
    lateinit var photoAdapter: PhotoAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerView)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        photoAdapter = PhotoAdapter(viewModel.listPhoto, this)
        val layoutManagerRV = GridLayoutManager(activity, 2)
        recyclerView.apply {
            layoutManager = layoutManagerRV
            setHasFixedSize(true)
            adapter = photoAdapter
        }
    }

    override fun click(position: Int) {
        viewModel.position = position
        (requireActivity() as MainActivity).onPuzzleFragmentClick()
    }

}